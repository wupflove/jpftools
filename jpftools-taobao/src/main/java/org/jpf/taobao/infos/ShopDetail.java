package org.jpf.taobao.infos;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.fastjson.JSON;

/**
 * 
 */
public class ShopDetail {
  private static final Logger logger = LogManager.getLogger();

  // "isTmall":true,"delivery":[479,1,375],"description":[486,0,0],"service":[480,1,308]
  /**
   * 
   */
  public ShopDetail(String strMsg) {
    // TODO Auto-generated constructor stub
    try {

      int iStart = strMsg.lastIndexOf("\"isTmall");
      if (iStart > 0) {
        strMsg = strMsg.substring(iStart, strMsg.length());
      } else {
        return;
      }
      int iSecondStart = strMsg.indexOf("service");
      if (iSecondStart > 0) {
        String strTmp = strMsg.substring(iSecondStart, strMsg.length());
        int iEnd = strTmp.indexOf("]");
        strMsg = strMsg.substring(0, iSecondStart + iEnd + 1);
      } else {
        return;
      }
      /*
       * List<PriceRankInfo> PriceRankInfoList = JSON.parseArray(strMsg, PriceRankInfo.class); for
       * (PriceRankInfo bar : PriceRankInfoList) {
       * System.out.println(bar.getStart()+"-"+bar.getEnd()+" "+bar.getPercent()+"%"); }
       */
      strMsg = "{" + strMsg + "}";
      logger.info(strMsg);
      ShopEvaluationInfo cShopEvaluationInfo = JSON.parseObject(strMsg, ShopEvaluationInfo.class);
      System.out.println(cShopEvaluationInfo.isTmall);
      System.out.println(cShopEvaluationInfo.getDelivery());
      System.out.println(cShopEvaluationInfo.getDescription());
      System.out.println(cShopEvaluationInfo.getService());
    } catch (Exception ex) {
      // TODO: handle exception
      ex.printStackTrace();
    }
  }

}
