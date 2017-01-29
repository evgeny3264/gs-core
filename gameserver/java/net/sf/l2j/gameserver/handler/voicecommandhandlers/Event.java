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
package net.sf.l2j.gameserver.handler.voicecommandhandlers;

import com.l2je.custom.events.EventManager;

import net.sf.l2j.gameserver.handler.IVoicedCommandHandler;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;

public class Event implements IVoicedCommandHandler
{
	private final String[] _voicedCommands =
	{
		"join",
		"leave",
		"info",		
	};
	@Override
	public String[] getVoicedCommandList()
	{
		return _voicedCommands;
	}
	/* (non-Javadoc)
	 * @see net.sf.l2j.gameserver.handlers.IVoicedCommandHandler#useVoicedCommand(java.lang.String, net.sf.l2j.gameserver.model.actor.instance.L2PcInstance, java.lang.String)
	 */
	@Override
	public boolean useVoicedCommand(String command, L2PcInstance activeChar, String params)
	{
		// TODO Auto-generated method stub
		EventManager.getInstance().userByPass(activeChar,command);
		return true;
	}
	
}