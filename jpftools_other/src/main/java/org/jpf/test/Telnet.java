package org.jpf.test;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;

//import org.apache.commons.net.telnet.TelnetClient;

public class Telnet
{
  //static final char LF = 10;

  static final char LF = 10;
  static final char CR = 13;
  static final char DC1 = 17;

  private String host = "10.10.10.182";
  private int port = 23;
  private Socket socket = null;

  private InputStream is = null;
  private OutputStream os = null;
  private DataInputStream sin = null;
  private PrintStream sout = null;

  private StringBuffer sb = new StringBuffer();

  public Telnet()
  {

  }

  public Telnet(String host, int port)
  {

    this.host = host;
    this.port = port;
  }

  public int hand(InputStream is, OutputStream os) throws IOException
  {
    while (true)
    {
      int ch = is.read();
      // System.out.println("Svr[ch]:"   +   ch);
      if (ch == 82)
      {
        System.out.println(ch);
      }
      if (ch < 0 || ch != 255)
      {
        return ch;
      }
      int cmd = is.read();
      int opt = is.read();
      //System.out.println("Svr[cmd&opt]:"   +   cmd+" : "+opt);
      switch (opt)
      {
        case 1: //   echo协商选项,本程序未处理
          break;
        case 3: //   supress   go-ahead(抑制向前选项)
          break;
        case 24: //   terminal   type(终端类型选项)
          if (cmd == 253)
          {
            os.write(255);
            os.write(251);
            os.write(24);

            os.write(255);
            os.write(250);
            os.write(24);
            os.write(0);
            os.write("dumb".getBytes());
            os.write(255);
            os.write(240);
          } else if (cmd == 250) //子选项开始
          {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int svr = is.read();
            while (svr != 240)
            {
              baos.write(svr);
              //System.out.println("Svr[while]:"   +   svr);
              svr = is.read();
            }
            //System.out.println("Svr:"   +   baos.toString());
          }
          os.flush();
          break;
        default:
          if (cmd == 253)
          {
            os.write(255);
            os.write(252);
            os.write(opt);
            os.flush();
            //System.out.println("Svr[default]:"   +   opt);
          }
      }
    }
  }

  public void pause(int cnt)
  {
    for (int i = 0; i < cnt * 1000000; i++)
    {

    }
  }

  public void open(String userName, String password) throws IOException
  {
    socket = new Socket(this.host, this.port);
    // System.out.println("client   ok");
    //获得对应socket的输入/输出流
    is = socket.getInputStream();
    os = socket.getOutputStream();
    hand(is, os);
    //输入用户名和密码
    send(userName);
    this.pause(1);
    send(password);

    System.out.println("open :------------1-------------- ");
    System.out.println(receive());
    System.out.println("open :------------1-end ------------- ");
    //执行命令
    String cmdStr = "ls -al";
    send(cmdStr); //将读取得字符串传给server
    System.out.println("open :------------2-------------- ");
    System.out.println(receive());
    System.out.println("open :------------2-end ------------- ");

    //执行命令
    cmdStr = "ps -ef";
    send(cmdStr); //将读取得字符串传给server
    System.out.println("open :------------3-------------- ");
    System.out.println(receive());
    System.out.println("open :------------3-end ------------- ");

    //退出telnet
    logOut();
  }

  /**
   * Send data to the remote host.
   * @param buf array of bytes to send
   * @see #receive
   */
  public void logOut() throws IOException
  {
    //linux
//   String cmdStr="logout"+CR+LF;
//        send(cmdStr.getBytes());
    //windows
    String cmdStr = "exit";
    send(cmdStr);
  }

  public void send(String cmd) throws IOException
  {
    String cmdStr = cmd + LF + CR;

    os.write(cmdStr.getBytes());
    os.flush();
  }

  public void send(byte b) throws IOException
  {
    System.out.println("Connected   to" + socket.getInetAddress() + ":" + socket.getPort());
    os.write(b);
    os.flush();
  }

  public String receive() throws IOException
  {
    String result = "";
    String temp = null;
    this.pause(1);
    for (int i = 0; i < 2; i++)
    {
      temp = receiveData();
      String des = new String(temp.getBytes(), "UTF-8");
      result += des;
    }
    return result;
  }

  public String receiveData() throws IOException
  {
    StringBuffer sb = new StringBuffer();
    BufferedReader br = new BufferedReader(new InputStreamReader(is));
    char[] buf = new char[1024];
    int read = br.read(buf);

    String temp = null;
    temp = new String(buf, 0, read);
    //System.out.println("receiveData[while] : "+ temp+" :read: "+read);
    sb.append(temp);
    return sb.toString();
  }

  public void close() throws IOException
  {

    //关闭连接
    if (sin != null)
    {
      sin.close(); //关闭数据输入流
    }
    if (sout != null)
    {
      sout.close(); //关闭数据输出流
    }
    if (is != null)
    {
      is.close(); //关闭数据输入流
    }
    if (os != null)
    {
      os.close(); //关闭数据输出流
    }
    if (socket != null)
    {
      socket.close(); //关闭socket
    }
  }

  /**
   * @param args
   */
  public static void main(String[] args)
  {
    // TODO Auto-generated method stub
//  TelnetClient tc = new TelnetClient();
    Telnet telnet = new Telnet();
    try
    {
      telnet.open("wupf", "wupf");
      //String str = telnet.receiveData();
      //System.out.println("main : "+str);
      telnet.close();
    } catch (IOException ie)
    {
      ie.printStackTrace();
      try
      {
        telnet.close();
      } catch (IOException iec)
      {
        iec.printStackTrace();
      }
    }

  }

}
