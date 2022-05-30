package org.jpf.thread.threadpool;

import java.util.*;

/**
 *
 * <p>Title: </p>
 * <p>Description: 线程池</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * @author wupflove2003@hotmail.com
 * @version 1.0
 */
class ThreadPoolManager
{

  private int maxThread;
  public Vector vector;
  public void setMaxThread(int threadCount)
  {
    maxThread = threadCount;
  }

  public ThreadPoolManager(int threadCount)
  {
    setMaxThread(threadCount);
    System.out.println("Starting thread pool...");
    vector = new Vector();
    for (int i = 1; i <= threadCount; i++)
    {
      SimpleThread thread = new SimpleThread(i);
      vector.addElement(thread);
      thread.start();
    }
  }

  public void process(String argument)
  {
    int i;
    for (i = 0; i < vector.size(); i++)
    {
      SimpleThread currentThread = (SimpleThread) vector.elementAt(i);
      if (!currentThread.isRunning())
      {
        System.out.println("Thread " + (i + 1) + " is processing:" + argument);
        currentThread.setArgument(argument);
        currentThread.setRunning(true);
        return;
      }
    }
    if (i == vector.size())
    {
      System.out.println("pool is full, try in another time.");
    }
  }
} //end of class ThreadPoolManager
