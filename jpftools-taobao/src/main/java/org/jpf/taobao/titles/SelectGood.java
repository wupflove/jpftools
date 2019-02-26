/**
 * @author 吴平福 E-mail:wupf@asiainfo.com
 * @version 创建时间：2017年7月3日 下午7:24:11 类说明
 */

package org.jpf.taobao.titles;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jpf.utils.logUtil.TextAreaLogAppender;

import org.jpf.utils.AiDateTimeUtil;

/**
 * 
 */
public class SelectGood extends Thread {
    private static final Logger logger = LogManager.getLogger();

    String strKey;

    public SelectGood(String strKey) {
        this.strKey = strKey;
    }

    String[] typesUrls = {
            // 综合
            "https://s.taobao.com/search?q=wupf&s_from=newHeader&ssid=s5-e&search_type=item&uniq=imgo&sourceId=tb.item",
            "https://s.taobao.com/search?q=wupf&s_from=newHeader&ssid=s5-e&search_type=item&uniq=imgo&sourceId=tb.item&bcoffset=4&ntoffset=4&p4ppushleft=1%2C48&s=44",
            "https://s.taobao.com/search?q=wupf&s_from=newHeader&ssid=s5-e&search_type=item&uniq=imgo&sourceId=tb.item&bcoffset=4&ntoffset=4&p4ppushleft=1%2C48&s=88",

            // 人气
            "https://s.taobao.com/search?q=wupf&s_from=newHeader&ssid=s5-e&search_type=item&sourceId=tb.item&bcoffset=4&ntoffset=4&p4ppushleft=1%2C48&uniq=imgo&sort=renqi-desc",
            "https://s.taobao.com/search?q=wupf&s_from=newHeader&ssid=s5-e&search_type=item&sourceId=tb.item&bcoffset=4&ntoffset=4&p4ppushleft=1%2C48&uniq=imgo&sort=renqi-desc&s=44",
            "https://s.taobao.com/search?q=wupf&s_from=newHeader&ssid=s5-e&search_type=item&sourceId=tb.item&bcoffset=4&ntoffset=4&p4ppushleft=1%2C48&uniq=imgo&sort=renqi-desc&s=88",

            // 销量
            "https://s.taobao.com/search?q=wupf&s_from=newHeader&ssid=s5-e&search_type=item&sourceId=tb.item&bcoffset=4&ntoffset=4&p4ppushleft=1%2C48&sort=sale-desc",
            "https://s.taobao.com/search?q=wupf&s_from=newHeader&ssid=s5-e&search_type=item&sourceId=tb.item&bcoffset=0&ntoffset=4&p4ppushleft=%2C44&sort=sale-desc&s=44",
            "https://s.taobao.com/search?q=wupf&s_from=newHeader&ssid=s5-e&search_type=item&sourceId=tb.item&bcoffset=0&ntoffset=4&p4ppushleft=%2C44&sort=sale-desc&s=88",

            // 信用
            "https://s.taobao.com/search?q=wupf&s_from=newHeader&ssid=s5-e&search_type=item&sourceId=tb.item&bcoffset=0&ntoffset=4&p4ppushleft=%2C44&uniq=imgo&sort=credit-desc",
            "https://s.taobao.com/search?q=wupf&s_from=newHeader&ssid=s5-e&search_type=item&sourceId=tb.item&bcoffset=0&ntoffset=4&p4ppushleft=%2C44&uniq=imgo&sort=credit-desc&s=44",
            "https://s.taobao.com/search?q=wupf&s_from=newHeader&ssid=s5-e&search_type=item&sourceId=tb.item&bcoffset=0&ntoffset=4&p4ppushleft=%2C44&uniq=imgo&sort=credit-desc&s=88",

            // 价格
            "https://s.taobao.com/search?q=wupf&s_from=newHeader&ssid=s5-e&search_type=item&sourceId=tb.item&bcoffset=0&ntoffset=4&p4ppushleft=%2C44&uniq=imgo&sort=price-asc",
            "https://s.taobao.com/search?q=wupf&s_from=newHeader&ssid=s5-e&search_type=item&sourceId=tb.item&bcoffset=0&ntoffset=4&p4ppushleft=%2C44&uniq=imgo&sort=price-asc&s=44",
            "https://s.taobao.com/search?q=wupf&s_from=newHeader&ssid=s5-e&search_type=item&sourceId=tb.item&bcoffset=0&ntoffset=4&p4ppushleft=%2C44&uniq=imgo&sort=price-asc&s=88",

            "https://s.taobao.com/search?q=wupf&s_from=newHeader&ssid=s5-e&search_type=item&sourceId=tb.item&bcoffset=0&ntoffset=4&p4ppushleft=%2C44&sort=price-desc&uniq=imgo",
            "https://s.taobao.com/search?q=wupf&s_from=newHeader&ssid=s5-e&search_type=item&sourceId=tb.item&bcoffset=0&ntoffset=4&p4ppushleft=%2C44&sort=price-desc&uniq=imgo&s=44",
            "https://s.taobao.com/search?q=wupf&s_from=newHeader&ssid=s5-e&search_type=item&sourceId=tb.item&bcoffset=0&ntoffset=4&p4ppushleft=%2C44&sort=price-desc&uniq=imgo&s=88"};

    public void run() {
        long start = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder();
        try {
            sb.append("网址").append("\t").append("标题").append("\t").append("价格").append("\t")
                    .append("销售数量").append("\t");
            sb.append("\n");
            strKey=strKey.replaceAll(" ","+");
            TextAreaLogAppender.log("关键字"+strKey );
            for (int i = 0; i < typesUrls.length; i++) {
                
                String typesUrl = typesUrls[i].replaceAll("wupf", strKey);
                int iCount = TitlesUtil.doSearchTaobao(typesUrl, sb);
                TextAreaLogAppender.log("第"+i + "页，标题数量" + iCount);
            }
            TitlesUtil.writeToCsv(strKey + AiDateTimeUtil.getCurrDate() + ".csv", sb);
            TextAreaLogAppender.log("输出文件:" + strKey + AiDateTimeUtil.getCurrDate());
        } catch (Exception ex) {
            // TODO: handle exception
            ex.printStackTrace();
        }
        logger.info("ExcuteTime " + (System.currentTimeMillis() - start) + "ms");
        TextAreaLogAppender.log("一共耗时 " + (System.currentTimeMillis() - start) + "毫秒");
    }



    /**
     * @category @author 吴平福
     * @param args update 2017年6月27日
     */

    public static void main(String[] args) {
        // TODO Auto-generated method stub

        SelectGood cGoodsSort = new SelectGood("中年女包单肩");


    }

}
