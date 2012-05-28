package net.armyao.projs.app;

/**
 * @author Armyao
 * @version create time：2012-5-28 下午09:58:35
 * 
 */
public class HashTestRun
{
	public static void main(String[] args) throws Exception
	{
		Class.forName("net.armyao.projs.app.HashTestApp").getMethod("appRun", new Class[] { String.class }).invoke(null, new Object[] { "" });
	}
}
