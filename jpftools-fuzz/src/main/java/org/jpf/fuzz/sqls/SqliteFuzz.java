/**
 * copyrigth by wupf@ 2019年2月27日
 */
package org.jpf.fuzz.sqls;

import java.util.Vector;

/**
 * @author wupf@asiainfo.com
 *
 */
public class SqliteFuzz {

  /**
   * 
   */
  public SqliteFuzz() {
    // TODO Auto-generated constructor stub
    try {
      Vector<String> v = new Vector<>();
      // crashing bugs
      v.add("SELECT n()AND+#00;");
      v.add("SELECT(SELECT strftime());");
      v.add("DETACH(SELECT group_concat(q));");
      v.add("CREATE TABLE t0(t); INSERT INTO t0 SELECT strftime();");
    } catch (Exception e) {
      // TODO: handle exception
      e.printStackTrace();
    }
  }

  /**
   * @category:
   * @Title: main
   * @author:wupf@asiainfo.com
   * @date:2019年2月27日
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub

  }


}
