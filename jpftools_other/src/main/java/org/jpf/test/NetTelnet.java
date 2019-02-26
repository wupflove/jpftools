package org.jpf.test;

import java.io.InputStream;
import java.io.PrintStream;

import org.apache.commons.net.telnet.TelnetClient;
import java.io.InputStreamReader;
import java.io.BufferedReader;

/**
 * 利用apache net 开源包，使用telnet方式获取AIX主机信息
 *
 * @author zhaoyl
 * @date 20008.7.21
 * @version 1.2
 */
public class NetTelnet {

    // Telnet对象
    private TelnetClient telnet = new TelnetClient();

    private InputStream in;

    private PrintStream out;
    private BufferedReader br;
    // 提示符。具体请telnet到AIX主机查看
    private final String prompt = "%";
    // telnet端口
    private String port;
    // 用户
    private String user;
    // 密码
    private String password;
    // IP地址
    private String ip;

    public NetTelnet() {

        try {
            // AIX主机IP
            this.ip = "10.10.10.182";
            this.password = "wupf";
            this.user = "wupf";
            this.port = "23";
            telnet.connect(ip, Integer.parseInt(port));
            in = telnet.getInputStream();
            out = new PrintStream(telnet.getOutputStream());
            //br = new BufferedReader(new InputStreamReader(in));
            // 登录
            readUntil("login: ");
            write(this.user);

            readUntil(this.user+"'s Password: ");
            write(this.password);
            readUntil(prompt + " ");
            if (telnet.isConnected())
            {
            write("ls");
            readUntil(this.user+prompt+ " ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void pause(int cnt)
    {
     for(int i=0; i< cnt * 1000000; i++)
     {

     }
 }
    /**
     * 读取分析结果
     *
     * @param pattern
     * @return
     */
    public String readUntil(String pattern) {
      System.out.println(pattern);
      try {
            char lastChar = pattern.charAt(pattern.length() - 1);
            StringBuffer sb = new StringBuffer();

            char ch = (char) in.read();
            while (true) {

                sb.append(ch);

                if (ch == lastChar) {
                    if (sb.toString().endsWith(prompt)) {
                      System.out.println(sb.toString());
                      return sb.toString();
                    }
                }
                ch = (char) in.read();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    static long iCount=0;
    public void readUntil2(String pattern) {

      //System.out.println(pattern);
      StringBuffer sb = new StringBuffer();
      try
      {
        int ch;
        while ( (ch=in.read())>=0)
        {
          sb.append((char)ch);
        }
        System.out.println(sb.toString());
      }catch(Exception ex)
      {
        ex.printStackTrace();
      }
    }
    /**
     * 写
     *
     * @param value
     */
    public void write(String value) {
        try {
            out.println(value);
            out.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 向目标发送命令字符串
     *
     * @param command
     * @return
     */
    public String sendCommand(String command) {
        try {
            write(command);
            return readUntil(prompt + " ");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 关闭连接
     *
     */
    public void disconnect() {
        try {
            telnet.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            NetTelnet telnet = new NetTelnet();
            // 通过aix的命令“查找主机名称”获取数据
            // 命令是 "hostname"
            // 不熟悉命令的参考<<AIX网络管理手册>>
            //String result = telnet.sendCommand("ls");

            //System.out.println(result);
            // 最后一定要关闭
            telnet.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
