/** 
* @author 吴平福 
* E-mail:wupf@asiainfo.com 
* @version 创建时间：2015年7月23日 下午1:39:59 
* 类说明 
*/

package org.jpf.frame.dbproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * �������ڼ�¼Connectionһ�����־
 * @author keyboardsun@163.com
 */
public class ConnectionProxy extends BaseProxy implements InvocationHandler
{
	private static final Logger logger = LogManager.getLogger();

  private Connection connection;
  private ConnectionProxy(Connection conn)
  {
    super();
    this.connection = conn;
    logger.debug("ConnectionId=" + id);
  }

  /*��˵�е�invoke������Connection .class����õķ�����Ҫ���������䣬����å�Ķ���
   ����������Ҫʵ��InvocationHandler�ӿڣ���������invoke������
   */
  public Object invoke(Object prox, Method method, Object[] params) throws Throwable
  {
    try
    {
      if ("prepareStatement".equalsIgnoreCase(method.getName()))
      {
        logger.debug("ConnectionId=" + id + " Preparing Statement: " + params[0].toString());
        PreparedStatement stmt = (PreparedStatement)method.invoke(connection, params); //������������ĵ��õ��õ��õ��ļ�
        stmt = PreparedStatementProxy.newInstance(stmt, (String)params[0]);
        return stmt; //�����ǳ���PreparedStatement.class���ļ�������ġ�
      } else if ("prepareCall".equalsIgnoreCase(method.getName()))
      {
        logger.debug("ConnectionId=" + id + " prepareCall Statement: " + params[0].toString());
        PreparedStatement stmt = (PreparedStatement)method.invoke(connection, params);
        stmt = PreparedStatementProxy.newInstance(stmt, (String)params[0]);
        return stmt;
      } else if ("createStatement".equalsIgnoreCase(method.getName()))
      {
        logger.debug("ConnectionId=" + id + " createStatement: " + params[0].toString());
        Statement stmt = (Statement)method.invoke(connection, params);
        stmt = StatementProxy.newInstance(stmt);
        return stmt;
      } else
      {
        return method.invoke(connection, params);
      }
    } catch (Throwable t)
    {
      throw t;
    }
  }

  /**
   * ��������Ѿ��ConnectionҲ����proxy�Ĺ����ļ��б�
   */
  public static Connection newInstance(Connection conn)
  {
    InvocationHandler handler = new ConnectionProxy(conn);
    ClassLoader cl = Connection.class.getClassLoader();
    return (Connection)Proxy.newProxyInstance(cl, new Class[]
                                              {Connection.class /*�����������Connection.class�������ˣ���������Ϣ��Ҫ�ȱ�ConnectionProxy �����ˣ�һ�����ҾͲ���ע��*/}, handler);
  }
}
