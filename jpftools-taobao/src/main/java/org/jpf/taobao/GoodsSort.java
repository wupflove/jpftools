/**
 * @author 吴平福 E-mail:421722623@qq.com
 * @version 创建时间：2017年6月27日 下午4:50:15 类说明
 */

package org.jpf.taobao;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.Vector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.jpf.taobao.httputils.TaobaoHttpSearch;
import org.jpf.utils.JpfDateTimeUtil;



/**
 * 
 */
public class GoodsSort {
  private static final Logger logger = LogManager.getLogger();
  final String strXlsFileName = "goodsort.xlsx";

  public GoodsSort(String strUrl) {
    typesInfo ctypesInfo = new typesInfo();
    ctypesInfo.typesUrl =
        "https://s.taobao.com/search?q=女用棒震动&js=1&stats_click=search_radio_all%3A1&initiative_id=staobaoz_20170703&ie=utf8";
    ctypesInfo.typesName = "成人";
    doSort(ctypesInfo);
  }

  public void doSort2(typesInfo ctypesInfo) {
    try {

      logger.debug(ctypesInfo.typesName);
      logger.debug(ctypesInfo.typesUrl);

      String strHtml = TaobaoHttpSearch.getWebSourceCode(ctypesInfo.typesUrl);
      String[] strGoods = strHtml.split("\"p4pTags\"");
      logger.debug(strGoods.length);
      int iCount = 0;
      StringBuilder sb = new StringBuilder();
      sb.append("网址").append("\t").append("标题").append("\t").append("价格").append("\t")
          .append("销售数量").append("\t");
      sb.append("\n");
      for (int i = 1; i < strGoods.length; i++) {
        // logger.debug(strGoods[i]);
        getKeyValue("nid", strGoods[i], sb);
        getKeyValue("raw_title", strGoods[i], sb);
        getKeyValue("view_price", strGoods[i], sb);
        getKeyValue("view_sales", strGoods[i], sb);

        sb.append("\n");
        // logger.debug(sb.toString());
        iCount++;
      }
      logger.info(iCount);


      writeToCsv(ctypesInfo.typesName + JpfDateTimeUtil.getCurrDate() + ".csv", sb);
    } catch (Exception ex) {
      // TODO: handle exception
      ex.printStackTrace();
    }
  }

  /**
   * 
   * @category 淘宝热卖
   * @author 吴平福
   * @param ctypesInfo update 2017年7月2日
   */
  private void doReTaobao(typesInfo ctypesInfo) throws Exception {
    String strHtml = TaobaoHttpSearch.getWebSourceCode(ctypesInfo.typesUrl);
    System.out.println(strHtml);
  }

  public void doSort(typesInfo ctypesInfo) {
    try {

      logger.debug(ctypesInfo.typesName);
      logger.debug(ctypesInfo.typesUrl);

      if (ctypesInfo.typesUrl.startsWith("https://re.taobao.com/")) {
        doReTaobao(ctypesInfo);
      }
      if (ctypesInfo.typesUrl.startsWith("https://s.taobao.com/")) {
        doSearchTaobao(ctypesInfo);
      }
    } catch (Exception ex) {
      // TODO: handle exception
      logger.error(ex);
    }
  }

  class priceDistributedInfo {
    String percent;
    String start;
    String end;
  }

  private void getPercent(String strHtml, StringBuilder sb) {
    strHtml = getKeyValue(strHtml, "\"rank\":[", "]");
    strHtml = strHtml.replaceAll("percent", "比例");
    strHtml = strHtml.replaceAll("start", "最低价");
    strHtml = strHtml.replaceAll("end", "最高价");
    logger.debug(strHtml);
    sb.append(strHtml).append("\n");
  }

  private String getKeyValue(String strInput, String strKeyBegin, String strKeyEnd) {
    // "nid": "551131421002",
    int iPosBegin = strInput.indexOf(strKeyBegin);
    int iPosEnd = strInput.indexOf(strKeyEnd, iPosBegin);
    String strResult = "";
    if (iPosBegin > 0 && iPosEnd > 0) {
      strResult = strInput.substring(iPosBegin + strKeyBegin.length(), iPosEnd);

      logger.debug(strResult);
    }
    return strResult;
  }

  public void doSearchTaobao(typesInfo ctypesInfo) {
    try {

      String strHtml = TaobaoHttpSearch.getWebSourceCode(ctypesInfo.typesUrl);
      int iPosBegin = strHtml.indexOf("g_page_config = ");
      int iPosEnd = strHtml.indexOf("g_srp_loadCss();");
      strHtml = strHtml.substring(iPosBegin, iPosEnd);
      // logger.debug(strHtml);
      int iCount = 0;
      StringBuilder sb = new StringBuilder();
      getPercent(strHtml, sb);
      sb.append("网址").append("\t").append("标题").append("\t").append("价格").append("\t")
          .append("销售数量").append("\t");
      sb.append("\n");
      while (strHtml.length() > 0) {
        strHtml = getKeyValue("nid", strHtml, sb);
        strHtml = getKeyValue("raw_title", strHtml, sb);
        strHtml = getKeyValue("view_price", strHtml, sb);
        strHtml = getKeyValue("view_sales", strHtml, sb);

        sb.append("\n");
        // logger.debug(sb.toString());
        iCount++;
      }
      logger.info(iCount);
      writeToCsv(ctypesInfo.typesName + JpfDateTimeUtil.getCurrDate() + ".csv", sb);

    } catch (Exception ex) {
      // TODO: handle exception
      ex.printStackTrace();
    }
  }

  /**
   * 
   */
  public GoodsSort() {
    // TODO Auto-generated constructor stub
    try {
      Vector<typesInfo> vector = new Vector<typesInfo>();
      getTitlesFromXsl(vector, strXlsFileName);
      logger.debug(vector.size());
      for (typesInfo ctypesInfo : vector) {
        doSort(ctypesInfo);
      }
    } catch (Exception ex) {
      // TODO: handle exception
      ex.printStackTrace();
    }
  }

  private void writeToCsv(String strCvsName, StringBuilder sb) throws Exception {
    OutputStreamWriter writer = null;
    try {
      strCvsName = strCvsName.replaceAll("/", "-");
      strCvsName = strCvsName.replaceAll(">", "-");
      logger.info(strCvsName);
      writer = new OutputStreamWriter(new FileOutputStream(strCvsName), "x-UTF-16LE-BOM");
      writer.write(sb.toString());
      writer.flush();
      sb.delete(0, sb.length());
    } catch (Exception ex) {
      // TODO: handle exception

      logger.error(ex);
      throw ex;
    } finally {
      try {
        if (null != writer) {
          writer.close();
        }
      } catch (Exception ex2) {
        // TODO: handle exception
      }
    }

  }

  private String getKeyValue(String strKey, String strInput, StringBuilder sb) {
    // "nid": "551131421002",
    int iPosBegin = strInput.indexOf("\"" + strKey + "\"");
    int iPosEnd = strInput.indexOf("\",", iPosBegin);
    if (iPosBegin > 0 && iPosEnd > 0) {
      String strResult = strInput.substring(iPosBegin + strKey.length() + 4, iPosEnd);

      logger.debug(strResult);
      if (strKey.equalsIgnoreCase("nid")) {
        sb.append("https://item.taobao.com/item.htm?id=");
      }
      sb.append(strResult).append("\t");
      return strInput.substring(iPosEnd, strInput.length());
    }
    return "";
  }

  class typesInfo {
    String typesName;
    String typesUrl;
  }

  String goodsPrice;
  String goodsUrl;
  String goodsName;
  String goodsSaleCount;

  /**
   * 
   * @category 从EXCEL中获取标题
   * @author 吴平福
   * @param vTitles
   * @param strXlsFileName update 2017年6月17日
   */
  private void getTitlesFromXsl(Vector<typesInfo> vTitles, String strXlsFileName) {
    InputStream inp = null;
    Workbook wb = null;
    try {
      inp = new FileInputStream(strXlsFileName);
      wb = WorkbookFactory.create(inp);
      Sheet sheet = wb.getSheetAt(0);
      // System.out.println(sheet.getSheetName());
      int iCount = 0;
      Row row = sheet.getRow(iCount);

      while (row != null) {

        Cell cell = row.getCell(0);
        if (null != cell && cell.getStringCellValue() != null
            && cell.getStringCellValue().trim().length() > 0) {

          typesInfo ctypesInfo = new typesInfo();
          ctypesInfo.typesName = cell.getStringCellValue().trim();
          cell = row.getCell(1);
          ctypesInfo.typesUrl = cell.getStringCellValue().trim();
          vTitles.add(ctypesInfo);
          // sb.append(cell.getStringCellValue()).append(strKey).append("\n");
          // System.out.println( cell.getStringCellValue());

        }
        row = sheet.getRow(++iCount);
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    } finally {
      try {
        if (null != wb) {
          wb.close();
        }
      } catch (Exception ex2) {
        // TODO: handle exception
      }
      try {
        if (null != inp) {
          inp.close();
        }
      } catch (Exception ex2) {
        // TODO: handle exception
      }
    }
  }

  /**
   * @category @author 吴平福
   * @param args update 2017年6月27日
   */

  public static void main(String[] args) {
    // TODO Auto-generated method stub
    long start = System.currentTimeMillis();
    GoodsSort cGoodsSort = new GoodsSort("1");

    logger.info("ExcuteTime " + (System.currentTimeMillis() - start) + "ms");
  }

}
