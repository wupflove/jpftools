/** 
 * @author 吴平福 
 * E-mail:wupf@asiainfo.com 
 * @version 创建时间：2015年2月8日 下午11:07:00 
 * 类说明 
 */

package org.jpf.frame.dbproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public class PreparedStatementProxy extends BaseProxy implements InvocationHandler
{
	private static final Logger logger = LogManager.getLogger();
  private PreparedStatement statement;
  private String sql;
  private PreparedStatementProxy(PreparedStatement stmt, String sql)
  {
    this.statement = stmt;
    this.sql = sql;
  }

  public Object invoke(Object proxy, Method method, Object[] params) throws Throwable
  {
    try
    {
      if (execSqlMethods.contains(method.getName()))
      {
        logger.debug("PreparedStatementId=" + id + " Executing Statement: " + sql);
        logger.debug("PreparedStatementId=" + id + " Parameters: " + getValueString());
        //logger.debug("PreparedStatementId=" + id + " Types: " + getTypeString());
        if ("executeQuery".equals(method.getName()))
        {
          ResultSet rs = (ResultSet)method.invoke(statement, params);
          if (rs != null)
          {
            return ResultSetProxy.newInstance(rs);
          } else
          {
            return null;
          }
        } else
        {
          return method.invoke(statement, params);
        }
      } else if (setMethods.contains(method.getName()))
      {
        if ("setNull".equals(method.getName()))
        {
          setColumn(params[0], null);
        } else
        {

          setColumn(params[0], params[1]);
        }
        return method.invoke(statement, params);
      } else if ("getResultSet".equals(method.getName()))
      {
        ResultSet rs = (ResultSet)method.invoke(statement, params);
        if (rs != null)
        {
          return ResultSetProxy.newInstance(rs);
        } else
        {
          return null;
        }
      } else if ("equals".equals(method.getName()))
      {
        Object ps = params[0];
        if (ps instanceof Proxy)
        {
          return new Boolean(proxy == ps);
        }
        return new Boolean(false);
      } else if ("hashCode".equals(method.getName()))
      {
        return new Integer(proxy.hashCode());
      } else
      {
        return method.invoke(statement, params);
      }
    } catch (Throwable t)
    {
      throw t;
    }
  }


  public static PreparedStatement newInstance(PreparedStatement stmt, String sql)
  {
    InvocationHandler handler = new PreparedStatementProxy(stmt, sql);
    ClassLoader cl = PreparedStatement.class.getClassLoader();
    return (PreparedStatement)Proxy.newProxyInstance(cl, new Class[]
        {PreparedStatement.class, CallableStatement.class}, handler);
  }
}
