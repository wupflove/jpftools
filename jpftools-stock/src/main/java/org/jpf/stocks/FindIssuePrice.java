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
 * Description:查找发行价
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
public class FindIssuePrice extends StockBaseClass {


  // 关键字:发行价格
  private String g_KeyFindStr = AiConfigUtil.GetConfigString("KEYSTR_ISSUEPRICE");

  public FindIssuePrice() {}

  @Override
  public boolean FindStr(String inStr) throws Exception {
    if (inStr.indexOf(g_KeyFindStr) >= 0) {
      bFindResult = false;
      return true;
    } else {
      return false;
    }
  }


  /**
   * 写入到execl
   */

  @Override
  public void getContentFromExcel(BufferedReader in, jxl.write.WritableSheet ws) throws Exception {


    String Line;
    int iRow = 0;

    String m_Cols = "";
    String m_StockName = "";
    String m_StockCode = "";

    while ((Line = in.readLine()) != null) {
      iRow++;
      int i = iRow % 2;
      if (i == 0) {

        String m_strs[] = Line.split("│");
        if (m_strs.length != 5) {
          System.out.println(Line + ":错误");
        } else {
          m_Cols = ";" + m_strs[4].trim();
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
    return "发行价格";
  }

  @Override
  public String getColNames() {
    return "发行价格";
  }

}
