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
package net.sf.l2j.gameserver.scripting;

import java.util.concurrent.ScheduledFuture;
import java.util.logging.Logger;

import net.sf.l2j.commons.concurrent.ThreadPool;

import net.sf.l2j.gameserver.model.actor.L2Npc;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;

public class QuestTimer
{
	protected static final Logger _log = Logger.getLogger(QuestTimer.class.getName());
	private boolean				_isActive	= true;
	protected final Quest _quest;
	protected final String _name;
	protected final L2Npc _npc;
	protected final L2PcInstance _player;
	protected final boolean _isRepeating;
	
	protected ScheduledFuture<?> _schedular;
	
	public QuestTimer(Quest quest, String name, L2Npc npc, L2PcInstance player, long time, boolean repeating)
	{
		_quest = quest;
		_name = name;
		_npc = npc;
		_player = player;
		_isRepeating = repeating;
		
		if (repeating)
			_schedular = ThreadPool.scheduleAtFixedRate(new ScheduleTimerTask(), time, time);
		else
			_schedular = ThreadPool.schedule(new ScheduleTimerTask(), time);
	}
	public final boolean getIsActive()
	{
		return _isActive;
	}

	@Override
	public final String toString()
	{
		return _name;
	}
	
	protected final class ScheduleTimerTask implements Runnable
	{		
		@Override
		public void run()
		{			
			if (_schedular == null){
				_log.info(_quest.getName()+ "_schedular == null "+_name);				
			}
			if (!getIsActive()){
				_log.info(_quest.getName()+ "!getIsActive() "+_name);
				return;
			}
			try
			{				
				if (!_isRepeating)
					cancel();
				
				_quest.notifyEvent(_name, _npc, _player);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public final void cancel()
	{
		_isActive = false;
		if (_schedular != null)
		{
			_schedular.cancel(false);
		}
		
		_quest.removeQuestTimer(this);
	}
	
	/**
	 * public method to compare if this timer matches with the key attributes passed.
	 * @param quest : Quest instance to which the timer is attached
	 * @param name : Name of the timer
	 * @param npc : Npc instance attached to the desired timer (null if no npc attached)
	 * @param player : Player instance attached to the desired timer (null if no player attached)
	 * @return boolean
	 */
	public final boolean equals(Quest quest, String name, L2Npc npc, L2PcInstance player)
	{
		if (quest == null || quest != _quest)
			return false;
		
		if (name == null || !name.equals(_name))
			return false;
		
		return ((npc == _npc) && (player == _player));
	}
}