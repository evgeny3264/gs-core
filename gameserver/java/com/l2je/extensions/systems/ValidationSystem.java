package com.l2je.extensions.systems;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author HandOfGod
 * @date 27 мая 2017 г. 22:35:01
 */
public class ValidationSystem
{
	private static String ID = "server.lin2.pro";
	private static String URL_STR = "https://l2newday.com/clients.txt";
	
	public static boolean run()
	{
		URL url = null;
		try
		{
			url = new URL(URL_STR);
		}
		catch (MalformedURLException e)
		{			
			e.printStackTrace();
			return false;
		}
		BufferedReader in;
		try
		{
			in = new BufferedReader(new InputStreamReader(url.openStream()));
		}
		catch (IOException e1)
		{			
			e1.printStackTrace();
			return false;
		}
		
		String line;
		try
		{
			while ((line = in.readLine()) != null) {
				if(line.equals(ID)) {
					return true;
				}
			}
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try
		{
			in.close();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
