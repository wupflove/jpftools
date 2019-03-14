package org.jpf.stocks;

import java.io.BufferedReader;

import org.jpf.stocks.util.StockUtil;
import org.jpf.utils.conf.JpfConfigUtil;
import org.jpf.utils.excelutils.JpfExcelJxlUtil;

/**
 *
 * <p>
 * Title: 查找社保持股情况
 * </p>
 * <p>
 * Description:
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
public class FindFund extends StockBaseClass {
  private String strConfigName = "";

  public FindFund() {
    try {
      g_BeginStr = JpfConfigUtil.getStrFromConfig(strConfigName, "KEYSTR_FUND_BEGIN");
      g_EndStr = JpfConfigUtil.getStrFromConfig(strConfigName, "KEYSTR_FUND_END");
      g_KeyStr = JpfConfigUtil.getStrFromConfig(strConfigName, "KEYSTR_FUND_KEY");
    } catch (Exception e) {
      // TODO: handle exception
      e.printStackTrace();
    }
  }

  private String g_BeginStr;
  private String g_EndStr;
  private String g_KeyStr;

  private boolean g_FindBegin = false;

  /**
   *
   * @param inStr String
   * @throws Exception
   * @return boolean
   */
  @Override
  public boolean FindStr(String inStr) throws Exception {
    boolean b_Find = false;
    inStr = inStr.trim();
    // System.out.println(inStr);
    if (inStr.indexOf(g_BeginStr) > 0) {
      g_FindBegin = true;
    }
    if (inStr.endsWith(g_EndStr)) {
      g_FindBegin = false;
      bFindResult = false;
    }
    if (g_FindBegin) {
      if (inStr.startsWith(g_KeyStr)) {
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

    String m_ColName1 = "";
    String m_ColName2 = "";
    String m_ColName3 = "";

    String m_StockName = "";
    String m_StockCode = "";

    while ((Line = in.readLine()) != null) {
      // 分析时间
      if (Line.startsWith(g_KeyStr)) {
        String[] m_str = Line.split("│");
        m_ColName1 = m_str[1].trim();
        m_ColName2 = m_str[2].trim();
        m_ColName3 = m_str[3].trim();
      } else {
        // 股票代码和名称
        System.out.println(StockUtil.GetStockCode(Line));
        if (!"".equalsIgnoreCase(m_ColName1)) {
          if (!m_ColName1.equalsIgnoreCase("--") || !m_ColName2.equalsIgnoreCase("--")
              || !m_ColName2.equalsIgnoreCase("--")) {
            iRow++;
            JpfExcelJxlUtil.addCell(ws, 0, iRow, m_StockName);
            JpfExcelJxlUtil.addCell(ws, 1, iRow, m_StockCode);
            JpfExcelJxlUtil.addCell(ws, 2, iRow, m_ColName1);
            JpfExcelJxlUtil.addCell(ws, 3, iRow, m_ColName2);
            JpfExcelJxlUtil.addCell(ws, 4, iRow, m_ColName3);
          }
        }
        m_ColName1 = "";
        m_StockName = StockUtil.GetStockName(Line);
        m_StockCode = StockUtil.GetStockCode(Line);

      }
    }
    if (!"".equalsIgnoreCase(m_ColName1)) {
      if (!m_ColName1.equalsIgnoreCase("--") || !m_ColName2.equalsIgnoreCase("--")
          || !m_ColName2.equalsIgnoreCase("--")) {
        iRow++;
        JpfExcelJxlUtil.addCell(ws, 0, iRow, m_StockName);
        JpfExcelJxlUtil.addCell(ws, 1, iRow, m_StockCode);
        JpfExcelJxlUtil.addCell(ws, 2, iRow, m_ColName1);
        JpfExcelJxlUtil.addCell(ws, 3, iRow, m_ColName2);
        JpfExcelJxlUtil.addCell(ws, 4, iRow, m_ColName3);
      }
    }

  }

  @Override
  public String getSheetName() {
    return "社保持股";
  }

  @Override
  public String getColNames() {
    return "前  期;近  期;幅度";
  }

}
