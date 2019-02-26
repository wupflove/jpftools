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
 * Description:查找评级
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
public class FindReview extends StockBaseClass {
  public FindReview() {
    DoWork();
  }

  private String g_BeginStr = AiConfigUtil.GetConfigString("KEYSTR_REVIEW_BEGIN");
  private String g_EndStr = AiConfigUtil.GetConfigString("KEYSTR_REVIEW_END");
  private String EXECL_REVIEW_COLS = AiConfigUtil.GetConfigString("EXECL_REVIEW_COLS");

  private int i = 0;

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
      i = 1;
    }
    if (i > 0) {
      i++;
    }
    // if (4==i)
    if (i == 4 && inStr.startsWith(g_EndStr)) {
      return true;
    } else {
      return false;
    }

  }

  /* 以下方法是重载 */
  @Override
  public String getSheetName() {
    return "评级情况";
  }

  @Override
  public String getColNames() {
    return EXECL_REVIEW_COLS;
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
        String[] a = Line.trim().split("[ ]+");
        if (a.length == 8) {
          m_Cols = ";" + a[0].trim() + ";" + a[1].trim() + ";" + a[2].trim() + ";" + a[3].trim()
              + ";" + a[4].trim() + ";" + a[5].trim() + ";" + a[6].trim() + ";" + a[7].trim() + ";";
        } else {
          m_Cols = "";
          System.out.println(m_StockCode + ":错误");
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

}
