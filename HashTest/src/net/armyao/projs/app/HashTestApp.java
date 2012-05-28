package net.armyao.projs.app;

import net.armyao.projs.agrothim.Poisson;
import net.armyao.projs.config.SystemConfig;
import net.armyao.projs.util.ThreadPool;

/**
 * @author Armyao
 * @version create time：2012-5-28 下午09:56:43
 * 
 */
public class HashTestApp
{
	private static HashTestApp hashTestAppInstance;
	private static Poisson poissonInstance;
	private ThreadPool threadPool;

	public void sysInit()
	{
		System.out.println("system init...");
		System.out.println("Thread init...");
		threadPool=new ThreadPool(SystemConfig.THREAD_COUNT);
		System.out.println("Poisson Seed init...");
		poissonInstance=new Poisson(SystemConfig.Lamda);
	}

	public void sysStart()
	{
		for (int i = 0; i <300; i++)
		{
			threadPool.addCommand(new ClientChooseSession(String.valueOf(i)));
			System.out.println(poissonInstance.nextPoisson());
		}
	}
	
	public static void appRun(String args)
	{
		hashTestAppInstance = new HashTestApp();
		hashTestAppInstance.sysInit();
		hashTestAppInstance.sysStart();
	}
}
