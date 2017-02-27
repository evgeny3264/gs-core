package com.l2je.protection;

import net.sf.l2j.Config;
import net.sf.l2j.commons.config.ExProperties;

public final class ProtectionConfig
{
	private static final String FILE = "./config/protection.properties";
	
	public static boolean CRYPT;
	public static boolean BLOWFISH_KEY;
	public static boolean HWID;
	public static boolean PUNISHER;
	public static String ON_HACK_ATTEMP;
	
	public static void init()
	{
		try
		{
			final ExProperties protection = Config.initProperties(FILE);			
			CRYPT = Boolean.parseBoolean(protection.getProperty("CRYPT", "true"));
			BLOWFISH_KEY = Boolean.parseBoolean(protection.getProperty("BLOWFISH_KEY", "true"));
			HWID = Boolean.parseBoolean(protection.getProperty("HWID", "true"));
			PUNISHER = Boolean.parseBoolean(protection.getProperty("PUNISHER", "true"));
			ON_HACK_ATTEMP = protection.getProperty("ON_HACK_ATTEMP", "kick");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}