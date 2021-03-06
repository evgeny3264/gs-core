/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package net.sf.l2j.gameserver.model.actor.instance;

import java.util.StringTokenizer;

import net.sf.l2j.commons.lang.Language;
import net.sf.l2j.commons.lang.StringUtil;

import net.sf.l2j.Config;
import net.sf.l2j.gameserver.ai.CtrlIntention;
import net.sf.l2j.gameserver.datatables.SkillTable.FrequentSkill;
import net.sf.l2j.gameserver.instancemanager.CastleManager;
import net.sf.l2j.gameserver.instancemanager.CoupleManager;
import net.sf.l2j.gameserver.model.World;
import net.sf.l2j.gameserver.model.actor.L2Npc;
import net.sf.l2j.gameserver.model.actor.template.NpcTemplate;
import net.sf.l2j.gameserver.model.item.instance.ItemInstance;
import net.sf.l2j.gameserver.model.itemcontainer.Inventory;
import net.sf.l2j.gameserver.network.serverpackets.ActionFailed;
import net.sf.l2j.gameserver.network.serverpackets.ConfirmDlg;
import net.sf.l2j.gameserver.network.serverpackets.MagicSkillUse;
import net.sf.l2j.gameserver.network.serverpackets.MoveToPawn;
import net.sf.l2j.gameserver.network.serverpackets.NpcHtmlMessage;
import net.sf.l2j.gameserver.util.Broadcast;

public class L2WeddingManagerInstance extends L2NpcInstance
{
	public L2WeddingManagerInstance(int objectId, NpcTemplate template)
	{
		super(objectId, template);
	}
	
	@Override
	public void onAction(L2PcInstance player)
	{
		// Set the target of the L2PcInstance player
		if (player.getTarget() != this)
			player.setTarget(this);
		else
		{
			// Calculate the distance between the L2PcInstance and the L2Npc
			if (!canInteract(player))
				player.getAI().setIntention(CtrlIntention.INTERACT, this);
			else
			{
				// Rotate the player to face the instance
				player.sendPacket(new MoveToPawn(player, this, L2Npc.INTERACTION_DISTANCE));
				
				// Send ActionFailed to the player in order to avoid he stucks
				player.sendPacket(ActionFailed.STATIC_PACKET);
				
				// Shouldn't be able to see wedding content if the mod isn't activated on configs
				if (!Config.ALLOW_WEDDING)
				{
					String path = (player.getLang() == Language.RU) ? "data/html-ru/mods/wedding/disabled.htm" : "data/html/mods/wedding/disabled.htm";
					sendHtmlMessage(player, path);
				}
				else
				{
					// Married people got access to another menu
					if (player.getCoupleId() > 0)
					{
						String path = (player.getLang() == Language.RU) ? "data/html-ru/mods/wedding/start2.htm" : "data/html/mods/wedding/start2.htm";
						sendHtmlMessage(player, path);
					}
					// "Under marriage acceptance" people go to this one
					else if (player.isUnderMarryRequest())
					{
						String path = (player.getLang() == Language.RU) ? "data/html-ru/mods/wedding/waitforpartner.htm" : "data/html/mods/wedding/waitforpartner.htm";
						sendHtmlMessage(player, path);
					}
					// And normal players go here :)
					else
					{
						String path = (player.getLang() == Language.RU) ? "data/html-ru/mods/wedding/start.htm" : "data/html/mods/wedding/start.htm";
						sendHtmlMessage(player, path);
					}
				}
			}
		}
	}
	
	@Override
	public void onBypassFeedback(L2PcInstance player, String command)
	{
		if (command.startsWith("AskWedding"))
		{
			StringTokenizer st = new StringTokenizer(command);
			st.nextToken();
			
			if (st.hasMoreTokens())
			{
				final L2PcInstance partner = World.getInstance().getPlayer(st.nextToken());
				if (partner == null)
				{
					String path = (player.getLang() == Language.RU) ? "data/html-ru/mods/wedding/notfound.htm" : "data/html/mods/wedding/notfound.htm";
					sendHtmlMessage(player, path);
					return;
				}
				
				// check conditions
				if (!weddingConditions(player, partner))
					return;
				
				// block the wedding manager until an answer is given.
				player.setUnderMarryRequest(true);
				partner.setUnderMarryRequest(true);
				
				// memorize the requesterId for future use, and send a popup to the target
				partner.setRequesterId(player.getObjectId());
				partner.sendPacket(new ConfirmDlg(1983).addString(player.getName() + " asked you to marry. Do you want to start a new relationship ?"));
			}
			else
			{
				String path = (player.getLang() == Language.RU) ? "data/html-ru/mods/wedding/notfound.htm" : "data/html/mods/wedding/notfound.htm";
				sendHtmlMessage(player, path);
			}
		}
		else if (command.startsWith("Divorce"))
			CoupleManager.getInstance().deleteCouple(player.getCoupleId());
		else if (command.startsWith("GoToLove"))
		{
			// Find the partner using the couple id.
			final int partnerId = CoupleManager.getInstance().getPartnerId(player.getCoupleId(), player.getObjectId());
			if (partnerId == 0)
			{
				player.sendMessage("Your partner can't be found.");
				return;
			}
			
			final L2PcInstance partner = World.getInstance().getPlayer(partnerId);
			if (partner == null)
			{
				player.sendMessage("Your partner is not online.");
				return;
			}
			
			// Simple checks to avoid exploits
			if (partner.isInJail() || partner.isInOlympiadMode() || partner.isInDuel() || partner.isFestivalParticipant() || (partner.isInParty() && partner.getParty().isInDimensionalRift()) || partner.inObserverMode())
			{
				player.sendMessage("Due to the current partner's status, the teleportation failed.");
				return;
			}
			
			if (partner.getClan() != null && CastleManager.getInstance().getCastleByOwner(partner.getClan()) != null && CastleManager.getInstance().getCastleByOwner(partner.getClan()).getSiege().isInProgress())
			{
				player.sendMessage("As your partner is in siege, you can't go to him/her.");
				return;
			}
			
			// If all checks are successfully passed, teleport the player to the partner
			player.teleToLocation(partner.getX(), partner.getY(), partner.getZ(), 20);
		}
	}
	
	/**
	 * Are both partners wearing formal wear ? If Formal Wear check is disabled, returns True in any case.<BR>
	 * @param p1 L2PcInstance
	 * @param p2 L2PcInstance
	 * @return boolean
	 */
	private static boolean wearsFormalWear(L2PcInstance p1, L2PcInstance p2)
	{
		ItemInstance fw1 = p1.getInventory().getPaperdollItem(Inventory.PAPERDOLL_CHEST);
		if (fw1 == null || fw1.getItemId() != 6408)
			return false;
		
		ItemInstance fw2 = p2.getInventory().getPaperdollItem(Inventory.PAPERDOLL_CHEST);
		if (fw2 == null || fw2.getItemId() != 6408)
			return false;
		
		return true;
	}
	
	private boolean weddingConditions(L2PcInstance requester, L2PcInstance partner)
	{
		// Check if player target himself
		if (partner.getObjectId() == requester.getObjectId())
		{
			String path = (requester.getLang() == Language.RU) ? "data/html-ru/mods/wedding/error_wrongtarget.htm" : "data/html/mods/wedding/error_wrongtarget.htm";
			sendHtmlMessage(requester, path);
			return false;
		}
		
		// Sex check
		if (!Config.WEDDING_SAMESEX && partner.getAppearance().getSex() == requester.getAppearance().getSex())
		{
			String path = (requester.getLang() == Language.RU) ? "data/html-ru/mods/wedding/error_sex.htm" : "data/html/mods/wedding/error_sex.htm";
			sendHtmlMessage(requester, path);
			return false;
		}
		
		// Check if player has the target on friendlist
		if (!requester.getFriendList().contains(partner.getObjectId()))
		{
			String path = (requester.getLang() == Language.RU) ? "data/html-ru/mods/wedding/error_friendlist.htm" : "data/html/mods/wedding/error_friendlist.htm";
			sendHtmlMessage(requester, path);
			return false;
		}
		
		// Target mustn't be already married
		if (partner.getCoupleId() > 0)
		{
			String path = (requester.getLang() == Language.RU) ? "data/html-ru/mods/wedding/error_alreadymarried.htm" : "data/html/mods/wedding/error_alreadymarried.htm";
			sendHtmlMessage(requester, path);
			return false;
		}
		
		// Check for Formal Wear
		if (Config.WEDDING_FORMALWEAR && !wearsFormalWear(requester, partner))
		{
			String path = (requester.getLang() == Language.RU) ? "data/html-ru/mods/wedding/error_noformal.htm" : "data/html/mods/wedding/error_noformal.htm";
			sendHtmlMessage(requester, path);
			return false;
		}
		
		// Check and reduce wedding price
		if (requester.getAdena() < Config.WEDDING_PRICE || partner.getAdena() < Config.WEDDING_PRICE)
		{
			String path = (requester.getLang() == Language.RU) ? "data/html-ru/mods/wedding/error_adena.htm" : "data/html/mods/wedding/error_adena.htm";
			sendHtmlMessage(requester, path);
			return false;
		}
		
		return true;
	}
	
	public static void justMarried(L2PcInstance requester, L2PcInstance partner)
	{
		// Unlock the wedding manager for both users, and set them as married
		requester.setUnderMarryRequest(false);
		partner.setUnderMarryRequest(false);
		
		// reduce adenas amount according to configs
		requester.reduceAdena("Wedding", Config.WEDDING_PRICE, requester.getCurrentFolkNPC(), true);
		partner.reduceAdena("Wedding", Config.WEDDING_PRICE, requester.getCurrentFolkNPC(), true);
		
		// Messages to the couple
		requester.sendMessage("Congratulations, you are now married with " + partner.getName() + " !");
		partner.sendMessage("Congratulations, you are now married with " + requester.getName() + " !");
		
		// Wedding march
		requester.broadcastPacket(new MagicSkillUse(requester, requester, 2230, 1, 1, 0));
		partner.broadcastPacket(new MagicSkillUse(partner, partner, 2230, 1, 1, 0));
		
		// Fireworks
		requester.doCast(FrequentSkill.LARGE_FIREWORK.getSkill());
		partner.doCast(FrequentSkill.LARGE_FIREWORK.getSkill());
		
		Broadcast.announceToOnlinePlayers("Congratulations to " + requester.getName() + " and " + partner.getName() + "! They have been married.");
	}
	
	private void sendHtmlMessage(L2PcInstance player, String file)
	{
		final NpcHtmlMessage html = new NpcHtmlMessage(getObjectId());
		html.setFile(file);
		html.replace("%objectId%", getObjectId());
		html.replace("%adenasCost%", StringUtil.formatNumber(Config.WEDDING_PRICE));
		html.replace("%needOrNot%", Config.WEDDING_FORMALWEAR ? "will" : "won't");
		player.sendPacket(html);
	}
}