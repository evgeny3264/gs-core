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

import com.l2je.protection.ProtectionConfig;
import com.l2je.protection.Punisher;

/**
 * Developers: Redist Team<br>
 * <br>
 * <br>
 * Author: Redist<br>
 * Date: 29 февр. 2016 г.<br>
 * Time: 22:04:45<br>
 * <br>
 */
public class IllegalAction extends L2GameClientPacket
{
	private byte[] b ;


	@Override
	protected void readImpl()
	{		
		b= new byte[_buf.remaining()];
		readB(b);
	}


	@Override
	protected void runImpl()
	{		
		if(ProtectionConfig.PUNISHER)
		{
			Punisher.getInstance().illegalAction(getClient(), b[0]);
		}
	}

}