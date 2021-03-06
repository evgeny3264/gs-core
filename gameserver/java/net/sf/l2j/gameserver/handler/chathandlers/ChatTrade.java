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
package net.sf.l2j.gameserver.handler.chathandlers;

import net.sf.l2j.Config;
import net.sf.l2j.Config.ChatMode;
import net.sf.l2j.gameserver.datatables.MapRegionTable;
import net.sf.l2j.gameserver.handler.IChatHandler;
import net.sf.l2j.gameserver.model.BlockList;
import net.sf.l2j.gameserver.model.World;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.network.serverpackets.CreatureSay;
import net.sf.l2j.gameserver.util.FloodProtectors;
import net.sf.l2j.gameserver.util.FloodProtectors.Action;

public class ChatTrade implements IChatHandler
{
	private static final int[] COMMAND_IDS =
	{
		8
	};
	
	@Override
	public void handleChat(int type, L2PcInstance activeChar, String target, String text)
	{
		if (!FloodProtectors.performAction(activeChar.getClient(), Action.TRADE_CHAT))
			return;
		
		if(activeChar.getLevel()< Config.MIN_LEVEL_TRADE)
		{
			activeChar.sendMessage("Реклама в чате запрещена, чат доступен для игроков с "+Config.MIN_LEVEL_TRADE+"-го уровня.");
			return;
		}
		final CreatureSay cs = new CreatureSay(activeChar.getObjectId(), type, activeChar.getName(), text);
		
		if (Config.DEFAULT_TRADE_CHAT == ChatMode.REGION) {
			final int region = MapRegionTable.getMapRegion(activeChar.getX(), activeChar.getY());			
			for (L2PcInstance player : World.getInstance().getPlayers())
			{
				if (!BlockList.isBlocked(player, activeChar) && region == MapRegionTable.getMapRegion(player.getX(), player.getY()))
					player.sendPacket(cs);
			}			
		} else if (Config.DEFAULT_TRADE_CHAT == ChatMode.GLOBAL || (Config.DEFAULT_TRADE_CHAT == ChatMode.GM && activeChar.isGM())) {
			for (L2PcInstance player : World.getInstance().getPlayers()) {
				if (!(BlockList.isBlocked(player, activeChar))) {
					player.sendPacket(cs);					
				}
			}
		}		
	}
	
	@Override
	public int[] getChatTypeList()
	{
		return COMMAND_IDS;
	}
}