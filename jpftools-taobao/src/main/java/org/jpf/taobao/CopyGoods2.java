/**
 * @author 吴平福 E-mail:wupf@asiainfo.com
 * @version 创建时间：2017年4月24日 下午8:23:23 类说明
 */

package org.jpf.taobao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
<<<<<<< .mine
import org.jpf.utils.JpfDateTimeUtil;
import org.jpf.utils.ios.JpfFileUtil;

=======
import org.jpf.utils.JpfDateTimeUtil;
<<<<<<< HEAD
import org.jpf.utils.ios.JpfFileUtil;
=======
import org.jpf.utils.cvsutil.JpfCvsUtil;
import org.jpf.utils.ios.AiFileUtil;
>>>>>>> .theirs
>>>>>>> 50ef2cf76322bd6b1791e6cc03059365cfbfd439
import org.jpf.utils.logUtil.TextAreaLogAppender;

/**
 * @category 根据excel文件内容，复制CSV文件
 */
public class CopyGoods2 extends Thread {
  private static final Logger logger = LogManager.getLogger();

  private int total_file_count = 0;
  private int total_good_count = 0;
  private String strConstPicPath = "";


  String strCvsFilePath = "";
  String strXlsFilePath = "";

  String OUTPUT_PATH = "";

  // 店子数量
  int ShopCount = 1;
  // 最大标题数量
  private final int MAX_TITLE_COUNT = 10;

  /**
   * @return the iShopCount
   */
  public int getShopCount() {
    return ShopCount;
  }

  /**
   * @param iShopCount the iShopCount to set
   */
  public void setShopCount(int iShopCount) {
    this.ShopCount = iShopCount;
  }

  @Override
  public void run() {

    try {
      long start = System.currentTimeMillis();
      doWork();
      doImprove();
      zipMultiFile();
      writelog();
      TextAreaLogAppender.log("ExcuteTime " + (System.currentTimeMillis() - start) + "ms");
    } catch (Exception ex) {
      // TODO: handle exception
      ex.printStackTrace();
    }

  }

  /**
   * 
   * @category 记录操作结果
   * @author 吴平福 update 2017年6月28日
   */
  public void writelog() {
    String strLogFileName = "zgb.csv";
    logger.info("处理文件数:" + total_file_count);
    TextAreaLogAppender.log("处理文件数:" + total_file_count);
    logger.info("处理数据总条数:" + total_good_count);
    TextAreaLogAppender.log("处理数据总条数:" + total_good_count);
<<<<<<< HEAD
=======
<<<<<<< .mine
>>>>>>> 50ef2cf76322bd6b1791e6cc03059365cfbfd439
    JpfFileUtil.appendCsv(strLogFileName, JpfDateTimeUtil.getCurrDate() + "\t" + strCvsFilePath
        + "\t" + total_file_count + "\t" + total_good_count + "\n");
=======
    JpfCvsUtil.appendCsv(strLogFileName, JpfDateTimeUtil.getCurrDate() + "\t" + strCvsFilePath
        + "\t" + total_file_count + "\t" + total_good_count + "\n");
>>>>>>> .theirs
  }

  /**
   * 
   */
  public CopyGoods2(String mstrCvsFilePath, String mstrXlsFilePath) {

    if (mstrCvsFilePath.endsWith(File.separator)) {
      this.strCvsFilePath = mstrCvsFilePath.substring(0, mstrCvsFilePath.length() - 1);
    } else {
      this.strCvsFilePath = mstrCvsFilePath;
    }

    this.strXlsFilePath = mstrXlsFilePath;
    OUTPUT_PATH = strCvsFilePath.replaceAll("原始文件", "处理后");
  }

  public void doWork() {
    try {
      // 清空目标目录
      JpfFileUtil.delDirWithFiles(OUTPUT_PATH);

      strConstPicPath = "D:\\" + getOutPutLastPath();
      Vector<String> vector = new Vector<String>();
      JpfFileUtil.getFiles(strCvsFilePath, vector, ".csv");
      for (int i = 0; i < vector.size(); i++) {
        String strCvsFileName = vector.get(i);
        // 保存商品名称，查找重复
        Vector<String> vTitles = new Vector<String>();
        logger.info(strCvsFileName);
        TextAreaLogAppender.log(strCvsFileName);
        String strXlsFileName = JpfFileUtil.getFileName(strCvsFileName);

        // 检查商品下是否存在media目录，删除
        TaoBaoUtils.removeMediaPath(strCvsFileName, "media");

        strXlsFilePath = JpfFileUtil.getFilePath(strCvsFileName);
        String strXlsPathFileName = strCvsFileName.replace(".csv", ".xlsx");
        if (JpfFileUtil.FileExist(strXlsPathFileName)) {
          getTitles(vTitles, strXlsPathFileName);
        } else if (JpfFileUtil.FileExist(strCvsFileName.replace(".csv", ".xls"))) {
          strXlsPathFileName = strCvsFileName.replace(".csv", ".xls");
          getTitles(vTitles, strXlsPathFileName);
        } else {
          // throw new Exception(strXlsFileName +" not exist");
          logger.error(strXlsPathFileName + " not exist");
          TextAreaLogAppender.log(strXlsPathFileName + " 没有找到");
          try {
            throw new Exception(strXlsPathFileName + " not exist");
          } catch (Exception ex) {
            // TODO: handle exception
            ex.printStackTrace();
          }
          continue;
        }
        // logger.info(strXlsFileName);


        doCopyTitles(strXlsPathFileName, strCvsFileName, vTitles);
        total_file_count++;

      }
      // readac("G:\\掌柜\\川10\\1_防尘罩_200.xlsx");

    } catch (Exception ex) {
      // TODO: handle exception
      ex.printStackTrace();
    }
  }


  /**
   * 
   * @category 增加标题检查是否有重复
   * @author 吴平福
   * @param vStrings
   * @param strTitle update 2017年6月8日
   */
  private void addUniqueTitle(Vector<String> vStrings, String strTitle) {
    boolean isAdd = true;
    strTitle = strTitle.trim().replaceAll("\"", "").replaceAll("\t", "");
    for (int i = 0; i < vStrings.size(); i++) {
      if (vStrings.get(i).indexOf(strTitle) >= 0) {
        isAdd = false;
        break;
      }
      if (strTitle.indexOf(vStrings.get(i)) >= 0) {
        isAdd = false;
        break;
      }
    }
    if (isAdd) {
      vStrings.add(strTitle.trim());
    }
  }

  /**
   * 
   * @category 从EXCEL中获取标题
   * @author 吴平福
   * @param vTitles
   * @param strXlsFileName update 2017年6月17日
   */
  private void getTitles(Vector<String> vTitles, String strXlsFileName) {
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
          addUniqueTitle(vTitles, cell.getStringCellValue());
          // sb.append(cell.getStringCellValue()).append(strKey).append("\n");
          // System.out.print(iCount + ":");
          // System.out.println( cell.getStringCellValue());
          // logger.debug(iCount);
        } else {
          break;
        }
        row = sheet.getRow(++iCount);
      }
      // 精华模式
      iCount = vTitles.size();

      if (RunParam.B_Essence) {
        Collections.sort(vTitles, new Comparator() {

          @Override
          public int compare(Object left, Object right) {
            String Strleft = (String) left;
            String Strright = (String) right;
            return Strright.length() - Strleft.length();
          }
        });

        while (getShopCount() * MAX_TITLE_COUNT < vTitles.size()) {
          vTitles.remove(vTitles.lastElement());
        }
        logger.info(vTitles.size() + "/" + iCount);
        TextAreaLogAppender.log(vTitles.size() + "/" + iCount);
        if (vTitles.size() < getShopCount() * MAX_TITLE_COUNT) {
          logger.error("标题数量不正常");
          TextAreaLogAppender.log("标题数量不正常");
          // throw new Exception("标题数量不正常");
        }



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
    logger.debug("标题数量=" + vTitles.size());
  }

  /**
   * 
   * @category @author 吴平福
   * @param strXlsFileName
   * @param strCvsName update 2017年5月18日
   */
  private void doCopyTitles(String strXlsFileName, String strCvsName, Vector<String> vTitles) {
    // TODO Auto-generated constructor stub
    // 1_防尘罩_200.xlsx
    FileReader fileReader = null;
    BufferedReader bufferedReader = null;
    try {

      bufferedReader = new BufferedReader(
          new InputStreamReader(new FileInputStream(strCvsName), Charset.forName("Unicode")));
      int iCount = 0;
      String strLine = "";
      String strKey = "";

      // CVS前面三行
      StringBuilder sbCvsFileTitle = new StringBuilder();
      while ((strLine = bufferedReader.readLine()) != null) {
        iCount++;
        // System.out.println(strLine);

        if (iCount == 4) {
          strKey = strLine.trim();
          break;
        } else {

          sbCvsFileTitle.append(strLine).append("\n");
        }
      }
      bufferedReader.close();

      int iPos = strKey.indexOf("\t", 2);
      if (iPos > 0) {
        strKey = strKey.substring(iPos + 1, strKey.length());
      }
      logger.debug(strKey);

      if (strKey == null || strKey.trim().length() == 0) {
        throw new Exception("获取导出的商品信息失败");
      }
      iPos = strKey.indexOf("<wapDesc>");
      int iPos2 = strKey.indexOf("</wapDesc>");
      if (iPos > 0 && iPos2 > 0) {
        strKey = strKey.substring(0, iPos) + strKey.substring(iPos2 + 10, strKey.length());
      }
      logger.debug(strKey);
      strKey = TaoBaoUtils.getPicsPath(strKey, strCvsName, strConstPicPath);

      distribution(sbCvsFileTitle, vTitles, strKey, strCvsName);
      logger.info("处理标题数据条数:" + vTitles.size());
      TextAreaLogAppender.log("处理标题数据条数:" + vTitles.size());
      total_good_count += vTitles.size();

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (bufferedReader != null) {
          bufferedReader.close();
        }
        if (fileReader != null) {
          fileReader.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }


  /**
   * 
   * @category 压缩文件
   * @author 吴平福 update 2017年6月23日
   */
  public void zipMultiFile() {

    logger.info("正在压缩...");
    TextAreaLogAppender.log("正在压缩...");
    String sourceDir = OUTPUT_PATH;
    File file = new File(sourceDir);
    if (file.isDirectory()) {
      File[] files1 = file.listFiles();
      for (File fileSec1 : files1) {
        File[] files2 = fileSec1.listFiles();
        for (File fileSec2 : files2) {
          logger.info(fileSec2.getAbsolutePath());
          String strZipFileName = fileSec2.getAbsolutePath();
          int iPos = strZipFileName.lastIndexOf("\\");
          strZipFileName = strZipFileName.substring(0, iPos) + "_"
              + strZipFileName.substring(iPos + 1, strZipFileName.length()) + ".zip";
          logger.info(strZipFileName);
          TextAreaLogAppender.log("输出文件:" + strZipFileName);
          TextAreaLogAppender.log("压缩文件:" + fileSec2.getAbsolutePath());
          // zipMultiFile(fileSec2.getAbsolutePath(), strZipFileName, true);
        }
      }
    }
    /*
     * String sourceFilePath = "D:\\zgb\\处理后\\23号\\1\\23号"; String zipFilePath =
     * "D:\\zgb\\处理后\\23号\\23_1.zip"; AiZipUtil.zipMultiFile(sourceFilePath, zipFilePath, true);
     */
  }

  public void doImprove() throws Exception {

    // 复制图片目录
    String strShopDir = OUTPUT_PATH + "\\";
    logger.debug(strShopDir);
    Vector<String> vector = new Vector<String>();
    JpfFileUtil.getFiles(strShopDir, vector, ".csv");
    for (int i = 0; i < vector.size(); i++) {
      String strCvsFileName = vector.get(i);
      logger.info("提升处理:" + strCvsFileName);
      TextAreaLogAppender.log("提升处理:" + strCvsFileName);
      new ImproveGoods().ReWriteCsv(strCvsFileName);
    }


  }



  /**
   * 
   * @category 组装CVS文件内容
   * @author 吴平福
   * @param sbCvsFileTitle
   * @param vTitles
   * @param strKey
   * @param strCvsName
   * @throws Exception update 2017年6月22日
   */
  private void distribution(StringBuilder sbCvsFileTitle, Vector<String> vTitles, String strKey,
      String strCvsName) throws Exception {

    int iGoodCount = vTitles.size() / ShopCount;
    for (int j = 1; j <= ShopCount; j++) {
      StringBuilder sb = new StringBuilder();
      sb.append(sbCvsFileTitle);
      int i = (j - 1) * iGoodCount;
      for (; i < j * iGoodCount && i < vTitles.size(); i++) {
        // logger.info(strKey);
        sb.append(vTitles.get(i)).append("\t").append(strKey).append("\n");

      }
      // logger.info(sb.toString());
      if (sb.length() > 0) {
        writeToCsv(strCvsName, sb, j);
      }
    }


  }

  /**
   * 
   * @category 写入到CVS文件
   * @author 吴平福
   * @param strCvsName
   * @param sb
   * @throws Exception update 2017年6月22日
   */
  private void writeToCsv(String strCvsName, StringBuilder sb, int iShopNumber) throws Exception {
    String strNewCvsName =
        MakeOutPutDir(strCvsName, iShopNumber) + JpfFileUtil.getFileName(strCvsName);
    // logger.info(strNewCvsName);
    JpfFileUtil.writeToCsv(strNewCvsName, sb);

  }

  /**
   * 
   * @category 显示描述图片的路径
   * @author 吴平福
   * @param strPicInfo update 2017年6月22日
   */
  /*
   * private String showPicPath(final String strPicInfo,String strCvsName) { String
   * strKey="file:///"; String strTmpPicInfo = strPicInfo; int i = strTmpPicInfo.indexOf(strKey); if
   * (i > 0) { strTmpPicInfo = strTmpPicInfo.substring(i+strKey.length(), strPicInfo.length()); i =
   * strTmpPicInfo.indexOf(">"); strTmpPicInfo = strTmpPicInfo.substring(0, i-1); i =
   * strTmpPicInfo.lastIndexOf("/"); //图片中位置 String strOldPicPath = strTmpPicInfo.substring(0, i);
   * int j = strTmpPicInfo.indexOf("\""); String strPicName = strTmpPicInfo.substring(i+1, j);
   * 
   * String strExistPath=AiFileUtil.FileExist(getCurrentGoodPath(strCvsName), strPicName); if
   * (strExistPath!=null) { String
   * strNewPicPath=strConstPicPath+strExistPath.substring(strCvsFilePath.length(),strExistPath.
   * length()); strNewPicPath=AiFileUtil.getFilePath(strNewPicPath); logger.debug("strNewPicPath:" +
   * strNewPicPath); logger.debug("strOldPicPath:" + strOldPicPath);
   * strNewPicPath=strNewPicPath.replaceAll("\\\\", "/"); logger.info("strNewPicPath:" +
   * strNewPicPath); logger.info("strOldPicPath:" + strOldPicPath);
   * //TextAreaLogAppender.log("strConstPicPath:" + strConstPicPath);
   * //TextAreaLogAppender.log("strTmpPicInfo:" + strTmpPicInfo);
   * //logger.info(strPicInfo.indexOf(strPicPath)); strTmpPicInfo =AiStringUtil.ReplaceAll
   * (strPicInfo,strOldPicPath, strNewPicPath);
   * 
   * logger.info(strTmpPicInfo); return strTmpPicInfo; } }
   * 
   * return strPicInfo; }
   */
  /**
   * 
   * @category 显示描述图片的路径
   * @author 吴平福
   * @param strPicInfo update 2017年6月22日
   */
  private String showPicPath2(final String strPicInfo) {
    String strTmpPicInfo = strPicInfo;
    int i = strTmpPicInfo.indexOf("file:///");
    if (i > 0) {
      strTmpPicInfo = strTmpPicInfo.substring(i, strPicInfo.length());
      i = strTmpPicInfo.indexOf("contentPic");
      strTmpPicInfo = strTmpPicInfo.substring(0, i);
      i = strTmpPicInfo.lastIndexOf("/");
      strTmpPicInfo = strTmpPicInfo.substring(0, i);
      i = strTmpPicInfo.lastIndexOf("/");
      strTmpPicInfo = strTmpPicInfo.substring(0, i);

      if (!strConstPicPath.equalsIgnoreCase(strTmpPicInfo)) {
        logger.debug("strConstPicPath:" + strConstPicPath);
        logger.debug("strTmpPicInfo:" + strTmpPicInfo);
        // TextAreaLogAppender.log("strConstPicPath:" + strConstPicPath);
        // TextAreaLogAppender.log("strTmpPicInfo:" + strTmpPicInfo);
        logger.debug(strPicInfo);
        strTmpPicInfo = strPicInfo.replaceAll(strTmpPicInfo, strConstPicPath);
        logger.debug(strTmpPicInfo);
        return strTmpPicInfo;
      }
    }

    return strPicInfo;
  }


  public String getOutPutLastPath() {

    int iPos = strCvsFilePath.lastIndexOf(File.separator);
    return strCvsFilePath.substring(iPos + 1, strCvsFilePath.length());

  }

  /**
   * 
   * @category 创建目录
   * @author 吴平福
   * @param strCvsStr update 2017年6月17日
   */
  public String MakeOutPutDir(String strCvsFileName, int iShopNumber) throws Exception {
    try {
      // 创建目标
      String sourceDir = JpfFileUtil.getFilePath(strCvsFileName);
      String sourceFileName = JpfFileUtil.getFileName(strCvsFileName);
      sourceFileName = sourceFileName.substring(0, sourceFileName.length() - 4);
      int iPos = sourceDir.lastIndexOf("\\");
      sourceDir = sourceDir.substring(iPos + 1, sourceDir.length());
      // 复制图片目录
      String strShopDir =
          OUTPUT_PATH + "\\" + iShopNumber + "\\" + sourceDir + "\\" + sourceFileName;
      JpfFileUtil.mkdir(strShopDir);
      String strSourceDir = strCvsFileName.substring(0, strCvsFileName.length() - 4);
      JpfFileUtil.copyDir(strSourceDir, strShopDir);

      // 返回处理后的CSV文件目录
      return OUTPUT_PATH + "\\" + iShopNumber + "\\" + sourceDir + "\\";
    } catch (Exception ex) {
      logger.error("MakeOutPutDir error " + strCvsFileName);
      ex.printStackTrace();

      throw ex;
    }
  }

}
