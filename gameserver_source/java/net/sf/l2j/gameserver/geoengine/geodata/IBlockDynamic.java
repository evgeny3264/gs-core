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
package net.sf.l2j.gameserver.geoengine.geodata;

/**
 * @author Hasha
 */
public interface IBlockDynamic
{
	/**
	 * Adds {@link IGeoObject} to the {@link ABlock}. The block will update geodata according the object.
	 * @param object : {@link IGeoObject} to be added.
	 */
	public void addGeoObject(IGeoObject object);
	
	/**
	 * Removes {@link IGeoObject} from the {@link ABlock}. The block will update geodata according the object.
	 * @param object : {@link IGeoObject} to be removed.
	 */
	public void removeGeoObject(IGeoObject object);
}
