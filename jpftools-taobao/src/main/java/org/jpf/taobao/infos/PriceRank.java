package org.jpf.taobao.infos;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jpf.taobao.httputils.TaobaoHttpSearch;

import com.alibaba.fastjson.JSON;


/**
 * 
 */
public class PriceRank {
  private static final Logger logger = LogManager.getLogger();

  // "price":{"rank":[{"percent":48,"start":"0.0","end":"19.00"},{"percent":42,"start":"19.01","end":"59.00"},{"percent":8,"start":"59.01","end":"146.00"},{"percent":2,"start":"146.01","end":""}]}
  /**
   * 
   */
  public PriceRank() {
    // TODO Auto-generated constructor stub
    try {
      String strUrl =
          "https://s.taobao.com/search?q=���ɺ�+����&js=1&stats_click=search_radio_all%3A1&initiative_id=staobaoz_20170329&ie=utf8&filter=reserve_price%5B0%2C0%5D";
      String strMsg = TaobaoHttpSearch.getWebSourceCode(strUrl);
      int iStart = strMsg.indexOf("\"price\"");
      if (iStart > 0) {
        strMsg = strMsg.substring(iStart, strMsg.length());
      } else {
        return;
      }
      iStart = strMsg.indexOf("[{");
      if (iStart > 0) {
        strMsg = strMsg.substring(iStart, strMsg.length());
      } else {
        return;
      }
      int iEnd = strMsg.indexOf("}]}");
      if (iEnd > 0) {
        strMsg = strMsg.substring(0, iEnd + 2);
      }
      List<PriceRankInfo> PriceRankInfoList = JSON.parseArray(strMsg, PriceRankInfo.class);
      for (PriceRankInfo bar : PriceRankInfoList) {
        System.out.println(bar.getStart() + "-" + bar.getEnd() + " " + bar.getPercent() + "%");
      }
      logger.info(strMsg);
    } catch (Exception ex) {
      // TODO: handle exception
      ex.printStackTrace();
    }

  }

  /**
   * @category @author ��ƽ��
   * @param args update 2017��3��29��
   */

  public static void main(String[] args) {
    // TODO Auto-generated method stub
    PriceRank cPriceRank = new PriceRank();
  }

}
