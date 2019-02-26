/**
 * @author 吴平福 E-mail:wupf@asiainfo.com
 * @version 创建时间：2017年10月8日 下午4:27:46 类说明
 */

package org.jpf.taobao;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Vector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import org.jpf.utils.ios.AiFileUtil;

/**
 * 
 */
public class FindTitle {
    private static final Logger logger = LogManager.getLogger();
    
    private StringBuilder sb=new StringBuilder();
    /**
     * 
     */
    public FindTitle() {
        // TODO Auto-generated constructor stub
        try {
            String strInputPath = "E:\\zgb\\原始文件";
            Vector<String> vFile = new Vector<String>();
            AiFileUtil.getFiles(strInputPath, vFile, ".xls");
            AiFileUtil.getFiles(strInputPath, vFile, ".xlsx");
            for (int i = 0; i < vFile.size(); i++) {
                getTitles( vFile.get(i));
            }
            AiFileUtil.saveFile("D:\\zgb\\标题汇总.csv", sb);
            logger.info("game over");
        } catch (Exception ex) {
            // TODO: handle exception
            ex.printStackTrace();
        }
    }

    /**
     * 
     * @category 从EXCEL中获取标题
     * @author 吴平福
     * @param vTitles
     * @param strXlsFileName update 2017年6月17日
     */
    private void getTitles( String strXlsFileName) {
        InputStream inp = null;
        Workbook wb = null;
        try {
            if (!AiFileUtil.FileExist(strXlsFileName))
            {
                return ;
            }
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
                    sb.append(strXlsFileName).append("\t").append(cell.getStringCellValue().trim()).append("\n");

                } else {
                    break;
                }
                row = sheet.getRow(++iCount);
            }
            //logger.info(strXlsFileName+" 标题数量 =" + iCount);
        } catch (Exception ex) {
            logger.error(strXlsFileName);
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
     * @param args update 2017年10月8日
     */

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        FindTitle cFindTitle = new FindTitle();
    }

}
