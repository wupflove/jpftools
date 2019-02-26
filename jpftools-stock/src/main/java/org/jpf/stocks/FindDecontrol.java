package org.jpf.stocks;


import java.io.BufferedReader;

import org.jpf.stocks.util.StockUtil;
import org.jpf.utils.conf.AiConfigUtil;
import org.jpf.utils.excelutils.AiExcelJxlUtil;

/**
 *
 * <p>
 * Title:
 * </p>
 * <p>
 * Description: 查找大小非
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
public class FindDecontrol extends StockBaseClass {
  public FindDecontrol() {

  }

  private String g_BeginStr = AiConfigUtil.GetConfigString("BEGINSTR_KEY");
  private String g_EndStr = AiConfigUtil.GetConfigString("ENDSTR_KEY");
  private String g_MonthStr = AiConfigUtil.GetConfigString("MONTH_KEY");
  private boolean g_FindBegin = false;

  @Override
  public boolean FindStr(String inStr) throws Exception {
    boolean b_Find = false;
    inStr = inStr.trim();
    if (inStr.startsWith(g_BeginStr)) {
      g_FindBegin = true;
    }
    if (inStr.startsWith(g_EndStr)) {
      g_FindBegin = false;
    }
    if (g_FindBegin) {
      if (inStr.indexOf(g_MonthStr) > 0) {
        b_Find = true;
      }
    }
    return b_Find;
  }



  /**
   * 写入到execl
   */
  @Override
  public void getContentFromExcel(BufferedReader in, jxl.write.WritableSheet ws) throws Exception {
    String Line = "";
    int iRow = 0;

    long m_StockCount = 0;
    String m_StockDate = "";
    String m_StockName = "";
    String m_StockCode = "";

    while ((Line = in.readLine()) != null) {
      // 分析时间
      int i = Line.indexOf(g_MonthStr);
      if (i > 0) {
        Line = Line.substring(i);
        i = Line.indexOf(" ");

        m_StockDate = Line.substring(0, i);
        m_StockCount += Long.parseLong(Line.substring(i).trim());
      } else {
        // 股票代码和名称
        System.out.println(StockUtil.GetStockCode(Line));
        if (m_StockCount > 0) {
          iRow++;
          AiExcelJxlUtil.addCell(ws, 0, iRow, m_StockName);
          AiExcelJxlUtil.addCell(ws, 1, iRow, m_StockCode);
          AiExcelJxlUtil.addCell(ws, 2, iRow, m_StockDate);
          AiExcelJxlUtil.addCell(ws, 3, iRow, String.valueOf(m_StockCount));
          m_StockCount = 0;

        }

        m_StockName = StockUtil.GetStockName(Line);
        m_StockCode = StockUtil.GetStockCode(Line);

      }



    }
  }

  @Override
  public String getSheetName() {
    return "大小非解禁";
  }

  @Override
  public String getColNames() {
    return "解禁时间;解禁股票数";
  }

}
