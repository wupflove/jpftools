/** 
 * @author 吴平福 
 * E-mail:wupf@asiainfo.com 
 * @version 创建时间：2015年2月8日 下午11:07:00 
 * 类说明 
 */

package org.jpf.frame.threads;

import java.util.*;


class ThreadPoolManager
{

  private int maxThread;
  public Vector<SimpleThread> vector;
  public void setMaxThread(int threadCount)
  {
    maxThread = threadCount;
    
  }

  public ThreadPoolManager(int threadCount)
  {
    setMaxThread(threadCount);
    System.out.println("Starting thread pool...");
    vector = new Vector<SimpleThread>();
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
