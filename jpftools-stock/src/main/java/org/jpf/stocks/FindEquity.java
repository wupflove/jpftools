package org.jpf.stocks;

import java.io.BufferedReader;

import org.jpf.stocks.util.StockUtil;
import org.jpf.utils.conf.AiConfigUtil;
import org.jpf.utils.excelutils.AiExcelJxlUtil;

/**
 *
 * <p>
 * Title: 查找净资产
 * </p>
 *
 * <p>
 * Description:
 * </p>
 *
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 *
 * <p>
 * Company:
 * </p>
 *
 * @author 吴平福
 * @version 1.0
 */
public class FindEquity extends StockBaseClass {
  public FindEquity() {
    try {
      g_BeginStr = AiConfigUtil.GetConfigString("KEYSTR_EQUITY_BEGIN");
      g_EndStr = AiConfigUtil.GetConfigString("KEYSTR_EQUITY_END");
    } catch (Exception e) {
      // TODO: handle exception
      e.printStackTrace();
    }

  }

  private String g_BeginStr;
  private String g_EndStr;


  @Override
  public boolean FindStr(String inStr) throws Exception {
    boolean b_Find = false;
    inStr = inStr.trim();
    if (inStr.startsWith(g_BeginStr)) {
      b_Find = true;
    }
    if (inStr.startsWith(g_EndStr)) {
      bFindResult = false;
    }
    return b_Find;
  }

  @Override
  public void getContentFromExcel(BufferedReader in, jxl.write.WritableSheet ws) throws Exception {


    String Line;
    int iRow = 0;

    String m_Cols = "";
    String m_StockName = "";
    String m_StockCode = "";

    while ((Line = in.readLine()) != null) {
      iRow++;
      if (Line.startsWith(g_BeginStr)) {

        String m_strs[] = Line.split("│");
        if (m_strs.length > 1) {

          m_Cols = ";" + m_strs[1].trim();
        }
        AiExcelJxlUtil.addRow(ws, iRow / 2, m_StockName + ";" + m_StockCode + m_Cols);
      } else {
        // 股票代码和名称
        m_StockName = StockUtil.GetStockName(Line);
        m_StockCode = StockUtil.GetStockCode(Line);
        System.out.println(m_StockCode);

      }

    }

  }

  @Override
  public String getSheetName() {
    return "每股净资产";
  }

  @Override
  public String getColNames() {
    return "每股净资产";
  }
}
