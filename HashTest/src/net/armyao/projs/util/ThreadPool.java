package net.armyao.projs.util;

import java.util.*;


public final class ThreadPool
{

	private static int DEFAULT_THREAD_COUNT = 20;
	private LinkedList waitingThreadList;
	private LinkedList runningThreadList;
	private LinkedList commandList;
	
	private static Integer runningThreadListSize=0;

	private boolean stopped = false;

	private int maxThreadCount = 0;


	// ---------------------------------------------------------------
	public ThreadPool(int threadCount)
	{
		init(threadCount);
	}

	// ---------------------------------------------------------------
	public ThreadPool()
	{
		init(DEFAULT_THREAD_COUNT);
	}



	// ---------------------------------------------------------------
	private void init(int threadCount)
	{
		waitingThreadList = new LinkedList();
		runningThreadList = new LinkedList();
		commandList = new LinkedList();

		// create worker threads
		for (int i = 0; i < threadCount; ++i)
		{
			waitingThreadList.add(new WorkerThread(this));
		}
	}


	private synchronized void doIt()
	{
		if (commandList.size() > 0 && waitingThreadList.size() > 0)
		{
			Command command = (Command) commandList.getFirst();
			commandList.removeFirst();

			WorkerThread workerThread = (WorkerThread) waitingThreadList.getFirst();
			waitingThreadList.removeFirst();
			runningThreadList.addLast(workerThread);

			workerThread.setCommand(command);
			workerThread.resumeThread();
		}

		int count = runningThreadList.size();
		
		runningThreadListSize=count;
		
		System.out.println("nowThreadCount:"+count);
		if (count > maxThreadCount)
		{
			System.out.println("maxThreadCount::" + count);
			maxThreadCount = count;
		}
		
		
		
	}

	// ---------------------------------------------------------------
	public synchronized void addCommand(Command command)
	{
		commandList.addLast(command);
		doIt();
	}

	// --------------------------------------------------------------------------------
	public synchronized boolean forceCommand(Command command)
	{
		if (waitingThreadList.size() > 0)
		{
			commandList.addFirst(command);
			doIt();
			return true;
		}
		else
		{
			return false;
		}
	}

	public synchronized void setThreadWait(WorkerThread workerThread)
	{
		runningThreadList.remove(workerThread);

		if (stopped)
		{
			workerThread.terminate();
			workerThread.resumeThread();
		}
		else
		{
			waitingThreadList.addLast(workerThread);
			doIt();
		}
	}

	public synchronized void stop()
	{
		if (stopped)
		{
			return;
		}

		stopped = true;

		// stop waiting threads
		while (!waitingThreadList.isEmpty())
		{
			WorkerThread workerThread = (WorkerThread) waitingThreadList.getFirst();
			waitingThreadList.removeFirst();
			workerThread.terminate();
			workerThread.resumeThread();
		}


		while (!runningThreadList.isEmpty())
		{
			WorkerThread workerThread = (WorkerThread) runningThreadList.getFirst();
			runningThreadList.removeFirst();
			workerThread.breakThread();
		}
	}

	// ---------------------------------------------------------------
	public int getRunningThreadCount()
	{
		return runningThreadList.size();
	}

	// ---------------------------------------------------------------
	public int getWaitingThreadCount()
	{
		return waitingThreadList.size();
	}

	// ---------------------------------------------------------------
	public int getCommandCount()
	{
		return commandList.size();
	}
	// --------------------------------------------------------------------------------

	public static Integer getRunningThreadListSize()
	{
		return runningThreadListSize;
	}

}
