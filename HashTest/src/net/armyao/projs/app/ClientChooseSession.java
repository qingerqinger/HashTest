package net.armyao.projs.app;

import java.util.Date;

import net.armyao.projs.util.Command;

/** 
 * @author Armyao 
 * @version create time：2012-5-28 下午10:32:59 
 * 
 */
public class ClientChooseSession implements Command
{
	
	private String randomString;

	public ClientChooseSession()
	{
		
	}


	public ClientChooseSession(String randomString)
	{
		this.randomString = randomString;
	}


	public void execute()
	{
		System.out.println(new Date()+randomString);
	}


	public void breakCommand()
	{
	}

}
