/** 
* @author 吴平福 
* E-mail:wupf@asiainfo.com 
* @version 创建时间：2017年9月23日 下午8:39:00 
* 类说明 
*/ 

package org.jpf.taobao.titles;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jpf.taobao.httputils.TaobaoHttpSearch;

/**
 * 
 */
public class TitlesUtil {
    private static final Logger logger = LogManager.getLogger();
    /**
     * 
     */
    private TitlesUtil() {
        // TODO Auto-generated constructor stub
    }
    /**
     * 
     * @category 
     * @author 吴平福 
     * @param strCvsName
     * @param sb
     * @throws Exception
     * update 2017年9月24日
     */
    public static  void writeToCsv(String strCvsName, StringBuilder sb) throws Exception {
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
    /**
     * 
     * @category 
     * @author 吴平福 
     * @param strKey
     * @param strInput
     * @param sb
     * @return
     * update 2017年9月24日
     */
    public static String getKeyValue(String strKey, String strInput, StringBuilder sb) {
        // "nid": "551131421002",
        int iPosBegin = strInput.indexOf("\"" + strKey + "\"");
        int iPosEnd = strInput.indexOf("\",", iPosBegin);
        if (iPosBegin > 0 && iPosEnd > 0) {
            String strResult = strInput.substring(iPosBegin + strKey.length() + 4, iPosEnd);

            logger.debug(strResult);
            if (strKey.equalsIgnoreCase("nid")) {
                sb.append("https://item.taobao.com/item.htm?id=");
            }
            if (sb.indexOf(strKey)<0)
            {
                sb.append(strResult).append("\t");
            }
            return strInput.substring(iPosEnd, strInput.length());
        }
        return "";
    }
    /**
     * 
     * @category 
     * @author 吴平福 
     * @param typesUrl
     * @param sb
     * @return
     * update 2017年9月24日
     */
    public static int doSearchTaobao(String typesUrl,StringBuilder sb) {
        int iCount = 0;
        try {
            String strHtml = TaobaoHttpSearch.getWebSourceCode(typesUrl);
            int iPosBegin = strHtml.indexOf("g_page_config = ");
            int iPosEnd = strHtml.indexOf("g_srp_loadCss();");
            strHtml = strHtml.substring(iPosBegin, iPosEnd);
            logger.debug(strHtml);
            

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


        } catch (Exception ex) {
            // TODO: handle exception
            ex.printStackTrace();
        }
        return iCount;
    }
}
