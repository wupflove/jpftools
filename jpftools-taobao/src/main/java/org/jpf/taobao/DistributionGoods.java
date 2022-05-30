/**
 * @author 吴平福 E-mail:421722623@qq.com
 * @version 创建时间：2017年4月25日 下午11:23:03 类说明
 */

package org.jpf.taobao;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Random;
import java.util.Vector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.jpf.taobao.httputils.TaobaoHttpSearch;
import org.jpf.taobao.infos.CommodityInfo;

/**
 * 
 */
public class DistributionGoods {
    private static final Logger logger = LogManager.getLogger();
    private Vector<CommodityInfo> vCommodityInfos = new Vector<CommodityInfo>();

    /**
     * 
     */
    public DistributionGoods() {

        try {
            String strXlsFileName = "G:\\掌柜\\川10\\川10.xlsx";
            initCommodity(strXlsFileName);
            setGoodTitles();
            printCommoditys();
            dispatch();
        } catch (Exception ex) {
            // TODO: handle exception
        }
    }

    private void printCommoditys() {
        for (int i = 0; i < vCommodityInfos.size(); i++) {
            System.out.println(vCommodityInfos.get(i).toString());
        }
    }

    private void initCommodity(String strXlsFileName) {
        // TODO Auto-generated constructor stub

        try {
            InputStream inp = new FileInputStream(strXlsFileName);
            // InputStream inp = new FileInputStream("workbook.xlsx");

            Workbook wb = WorkbookFactory.create(inp);
            Sheet sheet = wb.getSheetAt(0);
            int iRowNum = 2;
            Row row = sheet.getRow(iRowNum);
            int iCount = 0;
            while (row != null) {

                Cell cell = row.getCell(1);
                String strTypeName = cell.getStringCellValue();
                String strTypeUrl = "";
                String strKeyWord = "";
                if (strTypeName != null && strTypeName.trim().length() > 0) {
                    cell = row.getCell(2);
                    if (cell != null) {
                        strTypeUrl = cell.getStringCellValue();
                    }
                    cell = row.getCell(3);
                    if (cell != null) {
                        strKeyWord = cell.getStringCellValue();

                    }
                    if (strTypeUrl != null && strTypeUrl.trim().length() > 0) {
                        System.out.println(
                                ++iCount + ":" + strTypeName + ":" + strTypeUrl + ":" + strKeyWord);
                        CommodityInfo cCommodityInfo = new CommodityInfo();
                        cCommodityInfo.setTitle(strTypeName);
                        cCommodityInfo.setUrl(strTypeUrl);
                        cCommodityInfo.setKeyword(strKeyWord);
                        vCommodityInfos.add(cCommodityInfo);
                    }

                }
                row = sheet.getRow(++iRowNum);
            }
        } catch (Exception ex) {
            // TODO: handle exception
            ex.printStackTrace();
        }
    }

    /**
     * @category @author 吴平福
     * @param args update 2017年4月25日
     */

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        DistributionGoods cDistributionGoods = new DistributionGoods();
    }

    /**
     * 
     * @category @author 吴平福 update 2017年4月28日
     */
    private void setGoodTitles() {
        for (int i = 0; i < vCommodityInfos.size(); i++) {
            setGoodTitle(vCommodityInfos.get(i));
            try {
                Random random=new Random(10);
                long lSleep=random.nextLong()*10;
                logger.info("sleep:"+lSleep);
                lSleep=3000;
                Thread.sleep(lSleep);
            } catch (Exception ex) {
            }
        }
    }

    /**
     * 
     * @category @author 吴平福
     * @param cCommodityInfo update 2017年4月28日
     */
    private void setGoodTitle(CommodityInfo cCommodityInfo) {
        try {
            String strHtml = TaobaoHttpSearch.getWebSourceCode(cCommodityInfo.getUrl());

            // System.out.println(strHtml);
            int iPos = strHtml.indexOf("<meta name=\"keywords\"");
            if (iPos > 0) {
                strHtml = strHtml.substring(iPos + 21);
                iPos = strHtml.indexOf("=\"");
                strHtml = strHtml.substring(iPos + 2);
                iPos = strHtml.indexOf("\"/>");
                strHtml = strHtml.substring(0, iPos);
                cCommodityInfo.setName(strHtml);
            } else {
                throw new Exception(cCommodityInfo.getTitle() + ":" + cCommodityInfo.getUrl()
                        + " not found title");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * 
     * @category 从甩手导出文件读，和XLS中的商品匹配，输出CSV文件
     * @author 吴平福 
     * update 2017年4月29日
     */
    private void dispatch()
    {
        String strXlsFileName="d:\\9\\淘宝助理5201704281936.xlsx";
        
        try {
            InputStream inp = new FileInputStream(strXlsFileName);
            // InputStream inp = new FileInputStream("workbook.xlsx");

            Workbook wb = WorkbookFactory.create(inp);
            Sheet sheet = wb.getSheetAt(0);
            int iRowNum = 3;
            Row row = sheet.getRow(iRowNum);
            int iCount = 0;
            while (row != null) {
                Cell cell = row.getCell(0);
                String strName = cell.getStringCellValue();
                if (strName != null && strName.trim().length() > 0) {
                    for(int i=0;i<vCommodityInfos.size();i++)
                    {
                        if (vCommodityInfos.get(i).getName().trim().equalsIgnoreCase(strName))
                        {
                            logger.info("a:"+strName);
                            iCount++;
                        }
                    }
                    
                }
                row = sheet.getRow(++iRowNum);
            }
            logger.info("处理数量:"+iCount);
        } catch (Exception ex) {
            // TODO: handle exception
            ex.printStackTrace();
        }        
    }
}
