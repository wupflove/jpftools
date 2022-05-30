/**
 * @author 吴平福 E-mail:421722623@qq.com
 * @version 创建时间：2017年9月23日 下午5:30:34 类说明:抓取相同属性宝贝的标题
 */

package org.jpf.taobao.titles;

import org.jpf.utils.JpfDateTimeUtil;
import org.jpf.utils.logUtil.TextAreaLogAppender;

/**
 * 
 */
public class SameTypeTitle extends Thread {

  /**
   * 
   */
  public SameTypeTitle(String strUrl, String strKey) {
    // TODO Auto-generated constructor stub
    // https://s.taobao.com/search?type=samestyle&app=i2i&rec_type=0&uniqpid=-584991458&nid=558446808463&sort=sale-desc
    this.strKey = strKey;
    this.strUrl = strUrl;
    // this.strUrl="https://s.taobao.com/search?type=samestyle&app=i2i&rec_type=0&uniqpid=-584991458&nid=558446808463&sort=sale-desc";
    /*
     * https://s.taobao.com/search?type=samestyle&app=i2i&rec_type=0&uniqpid=-584991458&nid=
     * 558446808463&sort=default
     * https://s.taobao.com/search?type=samestyle&app=i2i&rec_type=0&uniqpid=-584991458&nid=
     * 558446808463&sort=credit-desc
     * https://s.taobao.com/search?type=samestyle&app=i2i&rec_type=0&uniqpid=-584991458&nid=
     * 558446808463&sort=price-asc
     */

  }

  String strKey;
  String strUrl;

  @Override
  public void run() {

    StringBuilder sb = new StringBuilder();
    try {
      sb.append("网址").append("\t").append("标题").append("\t").append("价格").append("\t")
          .append("销售数量").append("\t");
      sb.append("\n");

      int iPos = strUrl.lastIndexOf("=");
      strUrl = strUrl.substring(0, iPos) + "=sale-desc";
      // 销量由高到低
      TextAreaLogAppender.log("销量由高到低");
      String typesUrl = strUrl;
      TitlesUtil.doSearchTaobao(typesUrl, sb);

      // 默认值
      TextAreaLogAppender.log("默认值");
      typesUrl = strUrl.replaceAll("sale-desc", "default");
      TitlesUtil.doSearchTaobao(typesUrl, sb);

      // 信用由高到低
      TextAreaLogAppender.log("信用由高到低");
      typesUrl = strUrl.replaceAll("sale-desc", "credit-desc");
      TitlesUtil.doSearchTaobao(typesUrl, sb);

      // 价格由低到高
      TextAreaLogAppender.log("价格由低到高");
      typesUrl = strUrl.replaceAll("sale-desc", "price-asc");
      TitlesUtil.doSearchTaobao(typesUrl, sb);

      TitlesUtil.writeToCsv(strKey + JpfDateTimeUtil.getCurrDate() + ".csv", sb);
      TextAreaLogAppender.log("输出文件:" + strKey + JpfDateTimeUtil.getCurrDate());
    } catch (Exception ex) {
      // TODO: handle exception
      ex.printStackTrace();
    }



  }

  /**
   * @category @author 吴平福
   * @param args update 2017年9月23日
   */

  public static void main(String[] args) {
    // TODO Auto-generated method stub
    SameTypeTitle cSameTypeTitle = new SameTypeTitle("", "饰品");
  }

}
