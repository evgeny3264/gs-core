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
package net.sf.l2j.gameserver.network.clientpackets;

import net.sf.l2j.gameserver.instancemanager.SevenSignsFestival;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.model.zone.ZoneId;
import net.sf.l2j.gameserver.network.SystemMessageId;
import net.sf.l2j.gameserver.network.serverpackets.ActionFailed;
import net.sf.l2j.gameserver.network.serverpackets.SystemMessage;
import net.sf.l2j.gameserver.taskmanager.AttackStanceTaskManager;

import custom.events.EventManager;

public final class Logout extends L2GameClientPacket
{
	@Override
	protected void readImpl()
	{
	}
	
	@Override
	protected void runImpl()
	{
		final L2PcInstance player = getClient().getActiveChar();
		if (player == null)
			return;
		
		if (player.getActiveEnchantItem() != null || player.isLocked())
		{
			player.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		if (EventManager.getInstance().getCurrentEvent() != null)
		{
			if (!EventManager.getInstance().getCurrentEvent().canLogOut(player))
			{
				player.sendPacket(ActionFailed.STATIC_PACKET);
				return;
			}
			EventManager.getInstance().getCurrentEvent().onLogOut(player);
		}
		if (player.isInsideZone(ZoneId.NO_RESTART))
		{
			player.sendPacket(SystemMessageId.NO_LOGOUT_HERE);
			player.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		
		if (AttackStanceTaskManager.getInstance().isInAttackStance(player))
		{
			player.sendPacket(SystemMessageId.CANT_LOGOUT_WHILE_FIGHTING);
			player.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		
		if (player.isFestivalParticipant())
		{
			if (SevenSignsFestival.getInstance().isFestivalInitialized())
			{
				player.sendPacket(SystemMessageId.NO_LOGOUT_HERE);
				player.sendPacket(ActionFailed.STATIC_PACKET);
				return;
			}
			
			if (player.isInParty())
				player.getParty().broadcastToPartyMembers(SystemMessage.sendString(player.getName() + " has been removed from the upcoming festival."));
		}
		
		player.removeFromBossZone();
		player.logout();
	}
}