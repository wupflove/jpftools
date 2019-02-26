/** 
 * @author 吴平福 
 * E-mail:wupf@asiainfo.com 
 * @version 创建时间：2015年2月8日 下午11:07:00 
 * 类说明 
 */

package org.jpf.frame.threads;

import java.io.*;

public class ThreadPool
{
  public static void main(String[] args)
  {
    try
    {
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      String s;
      ThreadPoolManager manager = new ThreadPoolManager(10);
      while ( (s = br.readLine()) != null)
      {
        manager.process(s);
      }
    } catch (IOException e)
    {
      e.printStackTrace();
    }
  }
}
