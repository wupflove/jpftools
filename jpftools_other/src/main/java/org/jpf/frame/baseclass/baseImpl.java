/**
 * 
 */
package org.jpf.frame.baseclass;

import java.sql.Connection;
import java.sql.SQLException;
import org.apache.logging.log4j.Logger;


/**
 * @author Administrator
 *
 */
public abstract class baseImpl {
	  public abstract Logger GetLogger();
	  public baseImpl()
	  {
	  }

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
