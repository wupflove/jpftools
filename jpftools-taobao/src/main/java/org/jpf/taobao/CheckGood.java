/**
 * @author 吴平福 E-mail:wupf@asiainfo.com
 * @version 创建时间：2017年4月30日 下午6:11:30 类说明
 */

package org.jpf.taobao;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Vector;

import org.apache.http.HttpResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jpf.taobao.httputils.HttpRequestProxy;
import org.jpf.taobao.httputils.TaobaoHttpSearch;
import org.jpf.utils.ios.JpfFileUtil;

import com.alibaba.fastjson.JSON;
import com.opencsv.CSVReader;



/**
 * 
 */
public class CheckGood {
  private static final Logger logger = LogManager.getLogger();

  /**
   * 
   */
  public CheckGood() {

    try {
      Vector<String> vGoods = new Vector<String>();
      String strInputPath = "d:\\zgb";
      Vector<String> vFiles = new Vector<String>();
      JpfFileUtil.getFiles(strInputPath, vFiles, ".csv");
      int iCount = 0;
      for (int i = 0; i < vFiles.size(); i++) {
        String strCvsFileName = vFiles.get(i);
        String strId = getIdFromCsv(strCvsFileName);
        if (strId != null && strId.trim().length() > 0) {
          String strUrl = "https://item.taobao.com/item.htm?id=" + strId;
          logger.debug(strCvsFileName);
          logger.debug(strUrl);
          // 获取商品基本属性
          // strUrl = "https://detail.tmall.com/item.htm?id=546510653877";
          // https://item.taobao.com/item.htm?spm=a1z0d.6639537.1997196601.658.3ZBE07&id=538400365793
          // 3,33
          if (checkGoodExist(strUrl)) {
            vGoods.add(strCvsFileName + ":此宝贝已下架");
          }

          // checkPrice(strUrl);
          // sb.append(cell.getStringCellValue()).append(strKey).append("\n");
          iCount++;
          Thread.sleep(5000);
        }
      }
      logger.debug("商品数量:" + iCount);

      for (int i = 0; i < vGoods.size(); i++) {
        System.out.print(vGoods.get(i));
      }
    } catch (Exception ex) {
      // TODO: handle exception
      ex.printStackTrace();
    }
  }

  /**
   * 
   * @category 检查商品是否存在
   * @author 吴平福
   * @param strUrl
   * @return update 2017年5月17日
   */
  private boolean checkGoodExist(String strUrl) {
    // TODO Auto-generated constructor stub
    try {
      String strResult = HttpRequestProxy.getHttpUrlResponse(strUrl);
      // String strResult = TaobaoHttpSearch.getWebSourceCode(strUrl);
      // System.out.println(strResult);
      if (strResult.indexOf("查询商品不存在") > 0) {
        System.out.println("此宝贝已下架");

        return true;
      }
      if (strResult.indexOf("可能已下架或者被转移") > 0) {
        System.out.println("此宝贝已下架");
        return true;
      }

      if (strResult.indexOf("您查看的商品找不到了") > 0) {
        System.out.println("此宝贝已下架");
        return true;
      }
      if (strResult.indexOf("此宝贝已下架") > 0) {
        System.out.println("此宝贝已下架");
        return true;
      }
      if (strResult.indexOf("此商品已下架") > 0) {
        System.out.println("此宝贝已下架");
        return true;
      }
    } catch (Exception ex) {
      // TODO: handle exception
      ex.printStackTrace();
    }
    logger.debug("此宝贝正常");
    return false;
  }


  /**
   * @category @author 吴平福
   * @param args update 2017年4月30日
   */

  public static void main(String[] args) {
    // TODO Auto-generated method stub
    CheckGood cCheckGood = new CheckGood();
  }

  /**
   * 
   * @category 获取商品基本属性
   * @author 吴平福
   * @param strUrl
   * @return update 2017年5月4日
   */
  private String getGoodProps(String strUrl) {
    final String strKeyStart = "<ul id=\"J_AttrUL\">";
    final String strKeyEnd = "</ul>";
    try {
      String strResult = TaobaoHttpSearch.getWebSourceCode(strUrl);
      strResult = getSubStringByKey(strResult, strKeyStart, strKeyEnd);
      System.out.println(strResult);
    } catch (Exception ex) {
      // TODO: handle exception
      ex.printStackTrace();
    }

    return "";
  }


  /**
   * 
   * @category @author 吴平福
   * @param strInput
   * @param strKey
   * @return update 2017年5月4日
   */
  private String[] getKeyValue(final String strInput, String strKey) {
    ArrayList<String> kvlist = new ArrayList<String>();
    String[] array = new String[kvlist.size()];
    return kvlist.toArray(array);
  }

  /**
   * 
   * @category @author 吴平福
   * @param strInput
   * @param strKeyStart
   * @param strKeyEnd
   * @return update 2017年5月4日
   */
  private String getSubStringByKey(final String strInput, final String strKeyStart,
      final String strKeyEnd) {
    String strResult = "";
    // System.out.println(strInput);
    int iPos = strInput.indexOf(strKeyStart);
    if (iPos > 0) {
      strResult = strInput.substring(iPos + strKeyStart.length(), strInput.length()).trim();
      // System.out.println(strResult);
      iPos = strResult.indexOf(strKeyEnd);
      if (iPos > 0) {
        strResult = strResult.substring(0, iPos);

      }
    }
    // System.out.println(strResult);
    return strResult;
  }

  /**
   * 
   * @category 从网页获取价格信息
   * @author 吴平福
   * @param strURL
   * @return update 2017年6月4日
   */
  private String getPriceResponse(String strURL) {
    String content = "";
    try {


      String id = getStrByPrePost(strURL, "id=", "&");// 根据url获取商品的id
      if (id != null) {
        // 组装出获取商品信息的url
        String detailUrl =
            "https://mdskip.taobao.com/core/initItemDetail.htm?isForbidBuyItem=false&cartEnable=true&isAreaSell=false&addressLevel=2&tmallBuySupport=true&cachedTimestamp=1492939104236&isRegionLevel=false&isSecKill=false&service3C=false&sellerPreview=false&itemId="
                + id
                + "&isApparel=true&isUseInventoryCenter=false&isPurchaseMallPage=false&queryMemberRight=true&showShopProm=false&household=false&offlineShop=false&tryBeforeBuy=false&callback=setMdskip&timestamp=1492955646416&isg=Ajw8TliN-7cTiokPSBIgawsvjNTvMuBf&isg2=ApubrmCSR94oSrSjTTxuqQZCKv9FP69yBBj9mY3ZcBqxbLtOFUA_wrmucEG-&ref=https%3A%2F%2Fcart.taobao.com%2Fcart.htm%3Fspm%3Da1z09.2.1997525049.1.TB8p7E%26from%3Dmini%26pm_id%3D1501036000a02c5c3739";
        // System.out.println(detailUrl);
        // https://detailskip.taobao.com/service/getData/1/p1/item/detail/sib.htm?itemId=38568986311&sellerId=334919803&modules=dynStock,qrcode,viewer,price,duty,xmpPromotion,delivery,upp,activity,fqg,zjys,amountRestriction,couponActivity,soldQuantity,contract,tradeContract&callback=onSibRequestSuccess
        String detailUrl2 =
            "https://detailskip.taobao.com/service/getData/1/p1/item/detail/sib.htm?itemId=" + id
                + "&sellerId=334919803&modules=dynStock,qrcode,viewer,price,duty,xmpPromotion,delivery,upp,activity,fqg,zjys,amountRestriction,couponActivity,soldQuantity,contract,tradeContract&callback=onSibRequestSuccess";
        while (true) {
          content = TaobaoHttpSearch.getWebSourceCode(detailUrl2, strURL);
          /*
           * HttpClient httpclient = new DefaultHttpClient(); HttpGet get = new HttpGet(detailUrl);
           * get.addHeader("Referer", strURL);// Referer必须要设置 HttpResponse response =
           * httpclient.execute(get); content = getContent(response);
           */
          System.out.println("content:" + content);
          if (content.startsWith("window.location.href='https://sec.taobao.com/query.htm?")) {
            Thread.sleep(5000);
          } else {
            break;
          }

        }
      }
    } catch (Exception ex) {
      // TODO: handle exception
      ex.printStackTrace();
    }
    return content;
  }

  private void checkPrice(String strURL) {
    try {
      String content = getPriceResponse(strURL);

      // content = getStrByPrePost(content, "setMdskip(", ")");
      // System.out.println("content:" + content);
      // 返回的内容是json格式，可以通过gson等来解析，为了方便，此处直接截取。
      // System.out.println("商品信息:"+content);
      String priceInfo = getStrByPrePost(content, "priceInfo", "queryProm");
      // logger.info(priceInfo);
      // RespJsonData cRespJsonData = JSON.parseObject(strResult, RespJsonData.class);
      priceInfo = "{\"priceInfo" + priceInfo.substring(0, priceInfo.length() - 2) + "}";
      logger.info(priceInfo);
      priceInfo cpriceInfo = JSON.parseObject(priceInfo, priceInfo.class);
      logger.info(cpriceInfo.getPriceInfo().size());
      for (String key : cpriceInfo.getPriceInfo().keySet()) {
        sku sku_value = cpriceInfo.getPriceInfo().get(key);
        if (sku_value.getPromotionList()[0] != null) {
          logger.info("skuid:" + key + ";" + sku_value.getPromotionList()[0].getExtraPromPrice()
              + ";" + sku_value.getPromotionList()[0].getPrice());
        } else {
          logger.info("skuid:" + key + ";" + sku_value.getPrice());
        }
        // System.out.println("手机商品价格:" +
        // value.getPromotionList()[0].getExtraPromPrice());
        // System.out.println("商品价格:" + value.getPromotionList()[0].getPrice());
      }

    } catch (Exception ex) {
      // TODO: handle exception
      ex.printStackTrace();
    }
    /*
     * String promotionList = getStrByPrePost(priceInfo, "promotionList", null); String
     * promotionPrice = getStrByPrePost(promotionList, "\"price\":", ","); String
     * extraPromPrice=getStrByPrePost(promotionList, "\"extraPromPrice\":", ",");
     * System.out.println("商品价格:"+promotionPrice); System.out.println("手机商品价格:"+extraPromPrice);
     */
  }

  /**
   * 根据前后的文本截取指定的内容。
   * 
   * @param str
   * @param pre
   * @param post
   * @return
   */
  public static String getStrByPrePost(String str, String pre, String post) {
    if (str != null) {
      if (pre != null) {
        int s = str.indexOf(pre);
        if (s > -1) {
          str = str.substring(s + pre.length(), str.length());
        } else {
          return null;
        }

      }
      if (post != null) {
        int e = str.indexOf(post);
        if (e > -1) {
          str = str.substring(0, e);

        }

      }
    }

    return str;
  }

  /**
   * 获取返回内容的string
   * 
   * @param response
   * @return
   * @throws IllegalStateException
   * @throws IOException
   */
  private static String getContent(HttpResponse response)
      throws IllegalStateException, IOException {
    java.io.InputStream is = response.getEntity().getContent();
    BufferedReader br = new BufferedReader(new InputStreamReader(is, "GBK"));// response.getEntity().getContentEncoding().getName()
    String line = "";
    String temp = null;
    while ((temp = br.readLine()) != null) {
      line += temp;
    }
    return line;

  }

  /**
   * 
   * @category 从CSV中获取商品ID
   * @author 吴平福
   * @param strCvsName
   * @return update 2017年6月1日
   */
  private String getIdFromCsv(String strCvsName) {
    String strId = null;
    try {
      // String strCvsName = "D:\\zgb\\1拖3新店1套\\1_收纳盒_201.csv";
      BufferedReader bufferedReader = new BufferedReader(
          new InputStreamReader(new FileInputStream(strCvsName), Charset.forName("Unicode")));

      // File file = new File(strCvsName);
      // FileReader fReader = new FileReader(file);
      CSVReader csvReader = new CSVReader(bufferedReader, '\t', '\'', 3);
      String[] nextLine;
      if ((nextLine = csvReader.readNext()) != null) {
        // nextLine[] is an array of values from the line
        /*
         * System.out.println(nextLine.length); for(int i=0;i<nextLine.length;i++) {
         * System.out.println(i+":"+nextLine[i]); }
         */
        // System.out.println(nextLine[36]);
        strId = nextLine[36];
      }
      /*
       * List<String[]> list = csvReader.readAll(); for(String[] ss : list){ for(String s : ss)
       * if(null != s && !s.equals("")) System.out.println(s + " , "); //System.out.println(); }
       */
      csvReader.close();

    } catch (Exception ex) {
      // TODO: handle exception
      ex.printStackTrace();
    }
    return strId;
  }
}
