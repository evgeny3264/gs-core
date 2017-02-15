package com.l2je.extensions;

import com.l2je.extensions.acp.AcpManager;
import com.l2je.extensions.events.EventManager;

import net.sf.l2j.commons.lang.StringUtil;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.network.serverpackets.NpcHtmlMessage;

/**
 * @author evgeny64 Official Website: http://l2je.com
 * @date 14 февр. 2017 г. 10:57:13
 */
public class MenuManager
{
	private static class SingletonHolder
	{
		protected static final MenuManager _instance = new MenuManager();
	}
	
	public static final MenuManager getInstance()
	{
		return SingletonHolder._instance;
	}
	
	private static final String HTML_FILE_PATH = "data/html/commands/menu/Menu.htm";
	
	public void showChatWindow(L2PcInstance activeChar)
	{
		NpcHtmlMessage html = new NpcHtmlMessage(0);
		html.setFile(HTML_FILE_PATH);
		String premiumInfo = "<td><font color=\"ff0000\">Не активирован</font></td><td><button value=\"Активировать\" action=\"bypass -h menu_premium\" width=75 height=20 back=\"L2UI_CH3.Btn1_normalDisable\" fore=\"L2UI_CH3.Btn1_normalDisable\"></td>";
		if (activeChar.isPremium())
		{
			StringBuffer sb = new StringBuffer();
			StringUtil.append(sb, "<td><font color=\"LEVEL\">до ", PremiumManager.getPremiumEndDate(activeChar), "</font></td>");
			premiumInfo = sb.toString();
		}
		html.basicReplace("%premium%", premiumInfo);
		html.basicReplace("%exp%", stateToString(!activeChar.getStopExp()));
		html.basicReplace("%acp%", stateToString(activeChar.isAcpOn()));
		html.basicReplace("%ip%", activeChar.getClient().getConnection().getInetAddress().getHostAddress());
		html.basicReplace("%ipon%", stateToString(activeChar.isIpBlock()));
		String eventInfo = "<td><font color=\"ff0000\">Нет доступных ивентов</font></td>";
		if (EventManager.getInstance().getCurrentEvent() != null)
		{
			StringBuffer sb = new StringBuffer();
			StringUtil.append(sb, "<td><font color=\"LEVEL\">", EventManager.getInstance().getCurrentEvent().getName(), "</font></td><td><button value=\"Информация\" action=\"bypass -h event_info\" width=75 height=20 back=\"L2UI_CH3.Btn1_normalDisable\" fore=\"L2UI_CH3.Btn1_normalDisable\"></td>");
			eventInfo = sb.toString();
		}
		html.basicReplace("%event%", eventInfo);
		activeChar.sendPacket(html);
	}
	
	private static String stateToString(boolean state)
	{
		return state ? "<font color=\"LEVEL\">Вкл</font>" : "<font color=\"ff0000\">Выкл</font>";
	}
	
	public void onBypassFeedback(L2PcInstance activeChar, String command)
	{
		if (command.equals("expon") && activeChar.getStopExp())
		{
			activeChar.setStopExp(false);
			activeChar.sendMessage("Опыт включен.");
		}
		else if (command.equals("expoff") && !activeChar.getStopExp())
		{
			activeChar.setStopExp(true);
			activeChar.sendMessage("Опыт выключен.");
		}
		else if (command.startsWith("acp"))
		{
			command = command.substring(3);
			AcpManager.onBypassFeedback(activeChar, command);
		}
		else if (command.equals("ipon"))
		{
			try
			{
				activeChar.setIpBlock(activeChar.getClient().getConnection().getInetAddress().getHostAddress());
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else if (command.equals("ipoff"))
		{
			activeChar.setIpBlock("0");
		}
		else if (command.equals("premium"))
		{
			PremiumManager.showChatWindow(activeChar);
			return;
		}
		showChatWindow(activeChar);
	}
}