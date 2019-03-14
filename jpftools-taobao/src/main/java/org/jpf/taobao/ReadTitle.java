/**
 * @author 吴平福 E-mail:wupf@asiainfo.com
 * @version 创建时间：2017年10月22日 下午5:21:30 类说明
 */

package org.jpf.taobao;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Vector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jpf.utils.excelutils.WriteExcel;
import org.jpf.utils.ios.JpfFileUtil;

import com.opencsv.CSVReader;


/**
 * 
 */
public class ReadTitle {
  private static final Logger logger = LogManager.getLogger();

  /**
   * 
   */
  public ReadTitle() {
    // TODO Auto-generated constructor stub
    try {
      Vector<String> vFiles = new Vector<String>();
      JpfFileUtil.getFiles("D:\\zgb\\原始文件\\川川\\新建文件夹", vFiles, ".csv");
      for (int i = 0; i < vFiles.size(); i++) {
        readTitleFromCSV(vFiles.get(i));
      }
    } catch (Exception ex) {
      // TODO: handle exception
      ex.printStackTrace();
    }
  }

  /**
   * @category @author 吴平福
   * @param args update 2017年10月22日
   */

  public static void main(String[] args) {
    // TODO Auto-generated method stub
    new ReadTitle();
  }

  /**
   * 
   * @category 从CVS读取标题
   * @author 吴平福
   * @param vTitles
   * @param strCvsFileName
   * @return update 2017年10月22日
   */
  public static boolean readTitleFromCSV(String strCvsFileName) {
    Vector<String> vTitles = new Vector<String>();
    logger.debug(strCvsFileName);
    CSVReader csvReader = null;
    BufferedReader bufferedReader = null;
    try {

      // 打开CSV文件
      bufferedReader = new BufferedReader(
          new InputStreamReader(new FileInputStream(strCvsFileName), Charset.forName("Unicode")));

      csvReader = new CSVReader(bufferedReader, '\t');
      String[] nextLine;
      int iRow = 0;
      Vector<String[]> vCSV = new Vector<String[]>();
      while ((nextLine = csvReader.readNext()) != null) {
        if (iRow <= 3) {
          vCSV.add(nextLine);
        }
        if (iRow >= 3) {
          // 宝贝价格
          logger.debug(nextLine.length);
          logger.info(nextLine[0]);
          vTitles.add(nextLine[0]);
        }

        iRow++;
      }
      logger.info(vTitles.size());
      JpfFileUtil.writeToCsv(strCvsFileName, vCSV);
      // TaoBaoUtils.writeToCsv(strCvsName, vStrings);
      String finalXlsxPath = strCvsFileName.replace("csv", "xls");
      logger.info(finalXlsxPath);
      WriteExcel.writeExcel(vTitles, finalXlsxPath);
      return true;
    } catch (Exception ex) {
      // TODO: handle exception
      ex.printStackTrace();
    } finally {
      try {
        if (null != csvReader) {
          csvReader.close();
        }
      } catch (Exception ex2) {
        // TODO: handle exception
      }
      try {
        if (null != bufferedReader) {
          bufferedReader.close();
        }
      } catch (Exception ex2) {
        // TODO: handle exception
      }
    }

    return false;
  }


}
