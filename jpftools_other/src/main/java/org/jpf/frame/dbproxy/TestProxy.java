/** 
 * @author 吴平福 
 * E-mail:wupf@asiainfo.com 
 * @version 创建时间：2015年2月8日 下午11:07:00 
 * 类说明 
 */

package org.jpf.frame.dbproxy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class TestProxy
{
  private static Connection getConnection() throws Throwable
  {
    Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
    Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@10.10.10.203:1521:ora01", "js", "js");
	//这里，因为这段代码，数据库的操作类全部被出卖给了Proxy
    return ConnectionProxy.newInstance(conn); 
  }

  public static void main(String args[])
  {
    try
    {
      Connection connection = getConnection();
      PreparedStatement statement = connection.prepareStatement("select * from sys_log where rownum<? AND oper_code=?");
      statement.setInt(1, 100);
      statement.setInt(2, 0);
      ResultSet rs = statement.executeQuery();
      while (rs.next())
      {
        System.out.println(rs.getString("OPER_MSG"));
        System.out.println(rs.getString("OPER_TABLE"));
      }
    } catch (Throwable e)
    {
      e.printStackTrace();
    }
  }
}
