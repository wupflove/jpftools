/** 
* @author 吴平福 
* E-mail:421722623@qq.com 
* @version 创建时间：2015年7月23日 下午1:39:59 
* 类说明 
*/

package org.jpf.frame.baseclass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * 
 */
public class baseDs
{
	private static final Logger logger = LogManager.getLogger();
	/**
	 * �ر����ݿ�
	 * 
	 * @param conn
	 *            Connection
	 */
	public static void DoClear(Connection conn)
	{
		try
		{
			if (conn != null)
			{
				conn.close();
			}
		} catch (Exception ex)
		{
		}
	}
	public static void DoClear( PreparedStatement stmt, ResultSet rs)
	{
		try
		{
			if (rs != null)
			{
				rs.close();
			}
		} catch (Exception ex)
		{
		}

		try
		{
			if (stmt != null)
			{
				stmt.close();
			}
		} catch (Exception ex)
		{
		}
	}
	public static void DoClear(Connection conn, PreparedStatement stmt, ResultSet rs)
	{
		DoClear(stmt,rs);
		DoClear(conn);
	}

	public static void DoError(Connection conn, Logger cLogger, Exception ex)
	{
		cLogger.error(ex);
		try
		{
			conn.rollback();
		} catch (SQLException sqlEx)
		{
		}

	}
}
