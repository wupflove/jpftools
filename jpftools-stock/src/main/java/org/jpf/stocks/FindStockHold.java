package org.jpf.stocks;

import java.io.BufferedReader;

import org.jpf.stocks.util.StockUtil;
import org.jpf.utils.conf.JpfConfigUtil;
import org.jpf.utils.excelutils.JpfExcelJxlUtil;


/**
 *
 * <p>
 * Title:
 * </p>
 * <p>
 * Description: 持股情况
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
public class FindStockHold extends StockBaseClass {
  private String g_BeginStr;
  private String strConfigName = "";

  public FindStockHold() {

    try {
      g_BeginStr = JpfConfigUtil.getStrFromConfig(strConfigName, "KEYSTR_HOLD_BEGIN");
    } catch (Exception e) {
      // TODO: handle exception
      e.printStackTrace();
    }
  }

  /**
   *
   * @param inStr String
   * @throws Exception
   * @return boolean
   */
  @Override
  public boolean FindStr(String inStr) throws Exception {
    inStr = inStr.trim();
    if (inStr.startsWith(g_BeginStr)) {
      bFindResult = false;
      return true;
    }
    return false;

  }

  @Override
  public String getSheetName() {
    return "持股情况";
  }

  @Override
  public String getColNames() {
    return "前  期;近  期;幅度%";
  }

  @Override
  public void getContentFromExcel(BufferedReader in, jxl.write.WritableSheet ws) throws Exception {
    String Line = "";
    int iRow = 0;

    String m_Cols = "";
    String m_StockName = "";
    String m_StockCode = "";

    while ((Line = in.readLine()) != null) {
      // 分析时间
      iRow++;
      int i = iRow % 2;
      if (i == 0) {
        String[] a = Line.trim().split("│");
        if (a.length == 4) {
          m_Cols = ";" + a[1].trim() + ";" + a[2].trim() + ";" + a[3].trim() + ";";
        } else {
          m_Cols = "";
          System.out.println(m_StockCode + ":错误");
        }
        JpfExcelJxlUtil.addRow(ws, iRow / 2, m_StockName + ";" + m_StockCode + m_Cols);

      } else {
        // 股票代码和名称
        m_StockName = StockUtil.GetStockName(Line);
        m_StockCode = StockUtil.GetStockCode(Line);
        System.out.println(m_StockCode);

      }
    }

  }

}
