/**
 * @author 吴平福 E-mail:wupf@asiainfo.com
 * @version 创建时间：2017年5月29日 上午7:54:52 类说明
 */

package org.jpf.taobao;


import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
<<<<<<< .mine
import org.jpf.utils.JpfStringUtil;
import org.jpf.utils.ios.JpfFileUtil;

=======
import org.jpf.utils.JpfStringUtil;
import org.jpf.utils.ios.AiFileUtil;

>>>>>>> .theirs
/**
 * 
 */
public class TaoBaoUtils {
  private static final Logger logger = LogManager.getLogger();

  /**
   * 
   */
  private TaoBaoUtils() {
    // TODO Auto-generated constructor stub
  }

  /**
   * 
   * @category 删除手机详情目录
   * @author 吴平福
   * @param strFileName
   * @param SpecificPath
   * @throws IOException update 2017年6月19日
   */
  public static void removeMediaPath(String strFileName, final String SpecificPath)
      throws Exception {
    String strFilePath =
        strFileName.substring(0, strFileName.length() - 4) + java.io.File.separator + SpecificPath;
    // logger.debug(strFilePath);

<<<<<<< .mine
    JpfFileUtil.delDirWithFiles(strFilePath);
=======
    AiFileUtil.delDirWithFiles(strFilePath);
>>>>>>> .theirs

  }



  /**
   * 
   * @category 当前商品的目录
   * @author 吴平福
   * @param strCvsName
   * @return update 2017年10月23日
   */
  public static String getCurrentGoodPath(String strCvsName) {
    return strCvsName.substring(0, strCvsName.length() - 4).trim();
  }

  /**
   * 
   * @category @author 吴平福
   * @param strCvsName
   * @return update 2017年10月28日
   */
  public static String getOutPutLastPath(String strCvsName) {
    String strCvsFilePath = getCurrentGoodPath(strCvsName);
    int i = strCvsFilePath.lastIndexOf(File.separator);
    if (i > 0) {
      strCvsFilePath = strCvsFilePath.substring(i, strCvsFilePath.length());
    } else {
      i = strCvsFilePath.lastIndexOf("/");
      if (i > 0) {
        strCvsFilePath = strCvsFilePath.substring(i, strCvsFilePath.length());
      }
    }
    return strCvsFilePath;

<<<<<<< .mine
  }

  /**
   * 
   * @category @author 吴平福
   * @param strPicInfo:D:/胡强101/82_套装_200/contentPic/长袖卫衣连帽三件套装4/434fe05-1.gif
   * @param strCvsName
   * @param strConstPicPath
   * @return
   * @throws Exception update 2017年10月27日
   */
  public static String getPicPath(final String strOldPicInfo, String strCvsName,
      String strConstPicPath) throws Exception {
    // D:/胡强101/82_套装_200/contentPic/长袖卫衣连帽三件套装4/434fe05-1.gif
    // logger.info(strOldPicInfo);
    String strExistPicFile = getOutPutLastPath(strCvsName);
    int i = strOldPicInfo.indexOf(strExistPicFile);
    strExistPicFile = strCvsName.substring(0, strCvsName.length() - 4)
        + strOldPicInfo.substring(i + strExistPicFile.length(), strOldPicInfo.length());
    if (!JpfFileUtil.FileExist(strExistPicFile)) {
      // logger.error("文件不存在:{}",strOldPicInfo);
      // throw new Exception("文件不存在:"+strOldPicInfo);
      return null;
=======
  }

  /**
   * 
   * @category @author 吴平福
   * @param strPicInfo:D:/胡强101/82_套装_200/contentPic/长袖卫衣连帽三件套装4/434fe05-1.gif
   * @param strCvsName
   * @param strConstPicPath
   * @return
   * @throws Exception update 2017年10月27日
   */
  public static String getPicPath(final String strOldPicInfo, String strCvsName,
      String strConstPicPath) throws Exception {
    // D:/胡强101/82_套装_200/contentPic/长袖卫衣连帽三件套装4/434fe05-1.gif
    // logger.info(strOldPicInfo);
    String strExistPicFile = getOutPutLastPath(strCvsName);
    int i = strOldPicInfo.indexOf(strExistPicFile);
    strExistPicFile = strCvsName.substring(0, strCvsName.length() - 4)
        + strOldPicInfo.substring(i + strExistPicFile.length(), strOldPicInfo.length());
    if (!AiFileUtil.FileExist(strExistPicFile)) {
      // logger.error("文件不存在:{}",strOldPicInfo);
      // throw new Exception("文件不存在:"+strOldPicInfo);
      return null;
>>>>>>> .theirs
    }
    // logger.info(File.separator);
    // String strOldPicPath = getFilePath(strOldPicInfo);

    String strNewPicPath = strConstPicPath + strOldPicInfo.substring(i, strOldPicInfo.length());
    String strNewPicInfo = JpfStringUtil.ReplaceAll(strOldPicInfo, strOldPicInfo, strNewPicPath);
    // logger.info(strNewPicInfo);
    return strNewPicInfo;
  }

  /**
   * @category获取文件路径
   * @param sFilePathName String
   * @return String
   */
  public static String getFilePath(String sFilePathName) {
    int i = sFilePathName.lastIndexOf(File.separator);
    if (i > 0) {
      sFilePathName = sFilePathName.substring(0, i);
    } else {
      i = sFilePathName.lastIndexOf("/");
      if (i > 0) {
        sFilePathName = sFilePathName.substring(0, i);
      }
    }
    return sFilePathName;
  }

  /**
   * 
   * @category 显示描述图片的路径
   * @author 吴平福
   * @param strPicInfo update 2017年6月22日
   */
  public static String getPicsPath(final String strPicInfo, String strCvsName,
      String strConstPicPath) throws Exception {

    strCvsName = strCvsName.replaceAll("\\\\", "/");
    strConstPicPath = strConstPicPath.replaceAll("\\\\", "/");
    // logger.info("strCvsName:"+strCvsName);
    // logger.info("strConstPicPath:"+strConstPicPath);
    String strKey = "<IMG";
    String strKeyFile = "file:///";
    // logger.info(strPicInfo);
    String[] strImgFiles = strPicInfo.replaceAll("<img", "<IMG").split(strKey);

    logger.debug(strImgFiles.length);

    if (strImgFiles.length > 1) {

      StringBuilder sb = new StringBuilder();
      sb.append(strImgFiles[0]);
      for (int i = 1; i < strImgFiles.length; i++) {
        // logger.info(strImgFiles[i]);
        int iPosBegin = strImgFiles[i].indexOf(strKeyFile);
        int iPosEnd = strImgFiles[i].indexOf("\"", iPosBegin + strKeyFile.length());
        if (iPosBegin > 0 && iPosEnd > iPosBegin + strKeyFile.length()) {
          // logger.info(strImgFiles[i].substring(iPosBegin+strKeyFile.length(),
          // iPosEnd));
          String strNewPicInfo =
              getPicPath(strImgFiles[i].substring(iPosBegin + strKeyFile.length(), iPosEnd),
                  strCvsName, strConstPicPath);
          if (null != strNewPicInfo) {
            sb.append(strKey).append(strImgFiles[i].substring(0, iPosBegin)).append(strKeyFile)
                .append(strNewPicInfo)
                .append(strImgFiles[i].substring(iPosEnd, strImgFiles[i].length()));
          } else {
            int iPos = strImgFiles[i].indexOf(">");
            sb.append(strImgFiles[i].substring(iPos + 1, strImgFiles[i].length()));
            logger.warn("图片不存在：" + strImgFiles[i]);
          }
          // sb.append(getPicPath(strImgFiles[i].substring(0, j),
          // strCvsName,strConstPicPath) ).append(strImgFiles[i].substring(j,
          // strImgFiles[i].length()));
        } else {
          throw new Exception("非法的图片:" + strImgFiles[i]);
        }
        // sb.append(strKey).append(strImgFiles[i]);
      }
      logger.info(sb);
      return sb.toString();
    } else {
      throw new Exception("没有找到的图片");
    }

  }

}
