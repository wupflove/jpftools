package org.jpf.stocks.util;

/**
 *
 * <p>
 * Title:
 * </p>
 * <p>
 * Description: 方法类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author wupingfu
 * @version 1.0
 */

public class StockUtil {
  public StockUtil() {}

  static final String number = "0123456789";

  public static String GetStockCode(String in_Str) {
    String m_Str = "";
    for (int i = 0; i < in_Str.length(); i++) {
      if (number.indexOf(in_Str.charAt(i)) != -1) {
        m_Str += in_Str.charAt(i);
      }
    }
    if (m_Str.length() != 6) {
      System.out.println("错误的代码:" + in_Str);
    }
    return m_Str;
  }

  public static String GetStockName(String in_Str) {
    String m_Str = "";
    for (int i = 0; i < in_Str.length(); i++) {
      if (number.indexOf(in_Str.charAt(i)) == -1) {
        m_Str += in_Str.charAt(i);
      }
    }

    return m_Str;

  }

}
