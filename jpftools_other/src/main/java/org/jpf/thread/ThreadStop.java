package org.jpf.thread;

import java.net.*;
import java.io.*;

/**
 *
 * <p>Title: 演示如何终止线程</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2008</p>
 * @author 421722623@qq.com
 * @version 1.0
 */
public class ThreadStop extends Thread
{
  volatile boolean stop = false;
  volatile ServerSocket socket;
  public static void main(String args[]) throws Exception
  {
	  /*
    //Example5 thread = new Example5();
    System.out.println("Starting thread...");
    thread.start();
    Thread.sleep(3000);
    System.out.println("Asking thread to stop...");
    thread.stop = true;
    thread.socket.close();
    Thread.sleep(3000);
    System.out.println("Stopping application...");
    //System.exit( 0 );
     * 
     */
  }

  public void run()
  {
    try
    {
      socket = new ServerSocket(7856);
    } catch (IOException e)
    {
      System.out.println("Could not create the socket...");
      return;
    }
    while (!stop)
    {
      System.out.println("Waiting for connection...");
      try
      {
        Socket sock = socket.accept();
      } catch (IOException e)
      {
        System.out.println("accept() failed or interrupted...");
      }
    }
    System.out.println("Thread exiting under request...");
  }
}
