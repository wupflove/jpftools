/** 
 * @author 吴平福 
 * E-mail:421722623@qq.com 
 * @version 创建时间：2015年2月8日 下午11:07:00 
 * 类说明 
 */

package org.jpf.frame.baseclass;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



/**
 * 
 */
public abstract class baseApp
{
	private static final Logger logger = LogManager.getLogger();
	  public abstract Logger GetLogger();	
	  /**
	   * �ر����ݿ�
	   * @param conn Connection
	   */
	  public void DoClear(Connection conn)
	  {
	    try {
	      if (conn != null) {
	    	  conn.close();
	      }
	    }
	    catch (Exception ex) {
	    }
	  }

	  public void DoError(Connection conn, Logger cLogger, Exception ex)
	  {
	    cLogger.error(ex);
	    try {
	      conn.rollback();
	    }
	    catch (SQLException sqlEx) {}

	  }

}
