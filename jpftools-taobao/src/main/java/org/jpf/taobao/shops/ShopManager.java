/**
 * @author 吴平福 E-mail:wupf@asiainfo.com
 * @version 创建时间：2017年9月24日 上午1:58:05 类说明
 */

package org.jpf.taobao.shops;

import java.net.URLEncoder;
import java.util.Vector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jpf.taobao.httputils.TaobaoHttpSearch;
import org.jpf.taobao.titles.TitlesUtil;
import org.jpf.utils.logUtil.TextAreaLogAppender;

import org.jpf.utils.AiDateTimeUtil;

/**
 * 
 */
public class ShopManager  extends Thread {
    // https://shop316791781.taobao.com/shop/view_shop.htm?shop_id=316791781
    private static final Logger logger = LogManager.getLogger();

    /**
     * 
     */
    public ShopManager() {
        // TODO Auto-generated constructor stub
        run();
    }


    String[][] strUrls = {
            {"https://loveyysj.taobao.com/search.htm?search=y&orderType=hotsell_desc","女人饰品商城"}
            ,{"https://shop579866310.taobao.com/","刘胜五金百货店"}};
    String strKey = "";
    Vector<ShopInfo> vShopInfos = new Vector<>();

    public void run() {


        try {


            for (int i = 0; i < strUrls.length; i++) {


                String strUrl = strUrls[i][0];
                strKey = strUrls[i][1];
                String typesUrl = strUrl;
                TextAreaLogAppender.log(typesUrl);
                TextAreaLogAppender.log(strKey);
                ShopInfo cShopInfo = new ShopInfo();
                FindKeyFromShopHtml(typesUrl, cShopInfo,
                        "<ul class=\"ks-switchable-content carousel-content\">", "</ul>");

                TextAreaLogAppender.log("销量值:" + cShopInfo.vGoodInfos.size());

            }
            exportTitle();
        } catch (Exception ex) {
            // TODO: handle exception
            ex.printStackTrace();
        }
    }

    /**
     * 
     * @category 取低价钱
     * @author 吴平福
     * @param strPrice
     * @return update 2017年9月24日
     */
    private String getNewPriceLow(String strPrice) {
        return Double.toString(Double.parseDouble(strPrice) * 0.9);
    }

    /**
     * 
     * @category 取高价钱
     * @author 吴平福
     * @param strPrice
     * @return update 2017年9月24日
     */
    private String getNewPriceHigh(String strPrice) {
        return Double.toString(Double.parseDouble(strPrice) * 1.1);
    }

    /**
     * 
     * @category 根据商品抓类似标题
     * @author 吴平福 update 2017年9月24日
     */
    private void getTitlesByGood() {
        for (int i = 0; i < vShopInfos.size(); i++) {
            for (int j = 0; j < vShopInfos.get(i).vGoodInfos.size(); j++) {
                GoodInfo cGoodInfo = vShopInfos.get(i).vGoodInfos.get(j);
                try {
                    logger.info(cGoodInfo.goodUrl);
                    logger.info(cGoodInfo.goodName);

                    // https://shop316791781.taobao.com/shop/view_shop.htm?shop_id=316791781
                    // https://shop316791781.taobao.com/search.htm?orderType=hotsell_desc&viewType=grid&keyword=%D6%D0%B9%FA%B7%E7&lowPrice=1&highPrice=1.2
                    String strUrl = vShopInfos.get(i).shopUrl
                            //+ "/search.htm?orderType=hotsell_desc&viewType=grid&keyword="
                            + "i/asynSearch.htm?_ksTS=1506296577909_120&callback=jsonp121&mid=w-16761742284-0&wid=16761742284&path=/search.htm&search=y&orderType=hotsell_desc&viewType=grid&keyword="
                            + URLEncoder.encode(cGoodInfo.goodName.substring(0,3), "GB2312") + "&lowPrice="
                            + getNewPriceLow(cGoodInfo.goodPrice) + "&highPrice="
                            + getNewPriceHigh(cGoodInfo.goodPrice);
                    logger.info(strUrl);
                    
                    //https://shop316791781.taobao.com/i/asynSearch.htm?_ksTS=1506296577909_120&callback=jsonp121&mid=w-16761742284-0&wid=16761742284&path=/search.htm&search=y&orderType=hotsell_desc&viewType=grid&keyword=%D6%D0%B9%FA%B7%E7&lowPrice=1&highPrice=1.2
                    strUrl = vShopInfos.get(i).shopUrl
                            + "i/asynSearch.htm?_ksTS=1506296577909_120&callback=jsonp121&mid=w-16761742284-0&wid=16761742284&path=/search.htm&search=y&orderType=hotsell_desc&viewType=grid&keyword="
                            + URLEncoder.encode(cGoodInfo.goodName.substring(cGoodInfo.goodName.length()-3,cGoodInfo.goodName.length()), "GB2312") + "&lowPrice="
                            + getNewPriceLow(cGoodInfo.goodPrice) + "&highPrice="
                            + getNewPriceHigh(cGoodInfo.goodPrice);
                    logger.info(strUrl);
                } catch (Exception ex) {
                    // TODO: handle exception
                    ex.printStackTrace();
                }
            }
        }
    }

    /**
     * 
     * @category @author 吴平福 update 2017年9月24日
     */
    private void exportTitle() {
        for (int i = 0; i < vShopInfos.size(); i++) {
            for (int j = 0; j < vShopInfos.get(i).vGoodInfos.size(); j++) {
                try {
                    StringBuilder sb = new StringBuilder();
                    sb.append("店铺").append("\t").append("网址").append("\t").append("标题").append("\t")
                            .append("价格").append("\t").append("销售数量").append("\t").append("\n");
                    TitlesUtil.writeToCsv("店铺" + AiDateTimeUtil.getCurrDate() + ".csv", sb);
                    TextAreaLogAppender.log("输出文件:" + "店铺" + AiDateTimeUtil.getCurrDate());
                } catch (Exception ex) {
                    // TODO: handle exception
                    ex.printStackTrace();
                }
            }
        }
    }

    /**
     * @category @author 吴平福
     * @param args update 2017年9月24日
     */

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        ShopManager cShopManager = new ShopManager();

        /*
         * 1. 抓店铺销售量前几名商品 2. 检查商品是否已经复制过，如果是则删除 3. 根据每个商品查询出同类商品 4. 保存标题
         */
        /*
         * try { String value="礼品盒"; System.out.println( URLEncoder.encode(new
         * String(value.getBytes("GB2312")) , "UTF-8")); String mytext =
         * java.net.URLEncoder.encode("礼品盒", "GB2312"); System.out.println(mytext);
         * //%C0%F1%C6%B7%BA%D0 } catch (Exception e) {
         * 
         * }
         */

    }

    // 淘宝URL中对中文的编码
    final String TaobaoUrlCode = "GB2312";

    public void FindKeyFromShopHtml(String typesUrl, ShopInfo cShopInfo, String strBegin,
            String strEnd) {

        try {
            String strHtml = TaobaoHttpSearch.getWebSourceCode(typesUrl);
            //logger.info(strHtml);
            int iPosBegin = strHtml.indexOf(strBegin);
            int iPosEnd = strHtml.indexOf(strEnd, iPosBegin);
            strHtml = strHtml.substring(iPosBegin, iPosEnd);
            logger.info(strHtml);


            while (strHtml.length() > 0) {
                GoodInfo cGoodInfo = new GoodInfo();
                StringBuilder sb = new StringBuilder();
                strHtml = getKeyValueHtml("<a target=\"_blank\" href=\"", "\"><img", strHtml, sb);
                cGoodInfo.goodUrl = sb.toString();

                strHtml = getKeyValueHtml("<span class=\"name\">", "</span>", strHtml, sb);
                cGoodInfo.goodName = sb.toString();

                strHtml = getKeyValueHtml("<span class=\"price\">￥", "</span>", strHtml, sb);
                cGoodInfo.goodPrice = sb.toString();

                strHtml = getKeyValueHtml("<span class=\"sale-num\">售出:", "</span>", strHtml, sb);
                cGoodInfo.saledCount = sb.toString();
                // logger.debug(sb.toString());
                cShopInfo.vGoodInfos.add(cGoodInfo);
            }
            logger.info(cShopInfo.vGoodInfos.size());


        } catch (Exception ex) {
            // TODO: handle exception
            ex.printStackTrace();
        }

    }

    public static String getKeyValueHtml(String strBegin, String strEnd, String strInput,
            StringBuilder sb) {
        // "nid": "551131421002",
        sb.setLength(0);
        int iPosBegin = strInput.indexOf(strBegin);
        int iPosEnd = strInput.indexOf(strEnd, iPosBegin);
        if (iPosBegin > 0 && iPosEnd > 0) {
            String strResult = strInput.substring(iPosBegin + strBegin.length(), iPosEnd);

            //logger.debug(strResult);
            if (strResult.trim().startsWith("//")) {
                strResult = "http:" + strResult;
            }
            sb.append(strResult).append("\t");
            return strInput.substring(iPosEnd, strInput.length());
        }
        return "";
    }
}
