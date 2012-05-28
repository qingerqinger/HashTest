package net.armyao.projs.util;

public final class WorkerThread
extends Thread
{
private Command command;
private boolean suspended;
private volatile boolean terminated;

private ThreadPool threadPool;
//--------------------------------------------------------------------
public WorkerThread( ThreadPool in_threadPool )
{
threadPool	= in_threadPool;
terminated	= false;
suspended	= true;
start();
}
//--------------------------------------------------------------------

public final void run()
{
while( !terminated )
	{
	synchronized( this )
		{
		while( suspended )
			{
			try
				{
				wait();
				}
			catch( Exception e )
				{
				e.printStackTrace();
				//break;
				}
			}
		}
	
	if( terminated )
		{
		break;
		}

	try
		{
		command.execute();
		}
	catch( Throwable e )
		{
		 // execute() throws no Exception
		System.err.println( "########## Caught a Throwable #########" );
		e.printStackTrace();
		command.breakCommand();	
		}

	command = null;
	suspended = true;
	threadPool.setThreadWait( this );
	}
}
//--------------------------------------------------------------------
public final void setCommand( Command in_Command )
{
command = in_Command;
}
//--------------------------------------------------------------------
public final void terminate()
{
terminated = true;
}
//--------------------------------------------------------------------
public final synchronized void resumeThread()
{
suspended = false;
notify();
}
//--------------------------------------------------------------------
public final void breakThread()
{
if( command != null )
	{
	command.breakCommand();
	}
}
//--------------------------------------------------------------------
}
