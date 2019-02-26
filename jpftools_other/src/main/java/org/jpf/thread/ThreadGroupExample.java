package org.jpf.thread;

public class ThreadGroupExample
{
  public static class MyThreadGroup extends ThreadGroup
  {
    public MyThreadGroup(String s)
    {
      super(s);
    }

    public void uncaughtException(Thread thread, Throwable throwable)
    {
      //System.out.println("Thread " + thread.getName()+ " died, exception was: ");
      //throwable.printStackTrace();
      System.out.println(throwable.getMessage());
    }
  }

  public ThreadGroup workerThreads =  new MyThreadGroup("Worker Threads");
  public class WorkerThread extends Thread
  {
    public WorkerThread(String s)
    {
      super(workerThreads, s);
    }

    public void run()
    {
      try
      {
        Thread.sleep(2000);
        System.out.println("WorkerThread");
      throw new Exception("abc");
      }catch(Exception ex)
      {
        throw new RuntimeException(ex.getMessage());
      }

      //throw new RuntimeException();
    }
  }

  public static void main(String[] args)
  {
     ThreadGroupExample t=new ThreadGroupExample();
     
  }
  public ThreadGroupExample()
  {
    //ThreadGroup workerThreads =  new MyThreadGroup("Worker Threads");
    Thread t = new WorkerThread("Worker Thread");
    t.start();
  }

}
