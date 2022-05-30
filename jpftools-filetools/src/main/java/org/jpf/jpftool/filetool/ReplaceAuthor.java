/**
 * copyrigth by wupf@ 2019年2月27日
 */
package org.jpf.jpftool.filetool;

import java.util.Vector;

/**
 * @author 421722623@qq.com
 *
 */
public class ReplaceAuthor {

  /**
   * 
   */
  public ReplaceAuthor() {
    // TODO Auto-generated constructor stub
  }

  /**
   * @category:
   * @Title: main
   * @author:421722623@qq.com
   * @date:2019年2月27日
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub

  }

  protected void doWork() {
    try {
      Vector<String> vKeys = new Vector<>();
      vKeys.add("Created by");
      vKeys.add("@author");
    } catch (Exception e) {
      // TODO: handle exception
      e.printStackTrace();
    }
  }
}
