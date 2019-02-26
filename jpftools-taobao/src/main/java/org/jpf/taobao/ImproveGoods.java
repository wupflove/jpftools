/**
 * @author 吴平福 E-mail:wupf@asiainfo.com
 * @version 创建时间：2017年5月18日 上午11:22:09 类说明
 */

package org.jpf.taobao;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Vector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jpf.utils.AiDateTimeUtil;
import org.jpf.utils.cvsutil.JpfCvsUtil;
import org.jpf.utils.ios.AiFileUtil;
import org.jpf.utils.logUtil.TextAreaLogAppender;

import com.opencsv.CSVReader;


/**
 * 
 */
public class ImproveGoods {
  private static final Logger logger = LogManager.getLogger();

  ImageUtils cImageUtils = new ImageUtils();

  /**
   * 
   */
  public ImproveGoods() {
    // TODO Auto-generated constructor stub
  }



  public String ChangeSKU(String oldSKU, String oldPrice) {
    String[] strSKU = oldSKU.split(";");
    return "";
  }



  /**
   * 
   * @category 没有填写品牌的，随机填写一个
   * @author 吴平福
   * @param strBrand
   * @return update 2017年8月22日
   */
  public String changeBrand(String strBrand) {
    if (strBrand.startsWith("other") || strBrand.startsWith("\"other")) {
      strBrand = strBrand.replaceFirst("other/其他", RandomUtil.getRandomString(9));
    }
    return strBrand;
  }

  /**
   * 
   * @category 重写CVS文件优化内容
   * @author 吴平福
   * @param strCvsName update 2017年6月20日
   */
  public void ReWriteCsv(String strCvsName) {

    logger.debug(strCvsName);
    CSVReader csvReader = null;
    BufferedReader bufferedReader = null;
    String strOldPrice = "0";
    try {
      // String strCvsName = "D:\\zgb\\1拖3新店1套\\33_学步带_500.csv";
      MainPicInfo cMainPicInfo = new MainPicInfo();


      // 打开CSV文件
      bufferedReader = new BufferedReader(
          new InputStreamReader(new FileInputStream(strCvsName), Charset.forName("Unicode")));

      // 增加的图片文件路径
      String stAddPicPath = strCvsName.substring(0, strCvsName.length() - 4);
      // 描述图中的图片路径
      String strDescPicPath = "";
      csvReader = new CSVReader(bufferedReader, '\t');
      // csvReader = new CSVReader(bufferedReader, '\t', '\'');
      String[] nextLine;
      Vector<String[]> vStrings = new Vector<String[]>();
      int iRow = 0;
      while ((nextLine = csvReader.readNext()) != null) {
        logger.trace(iRow);
        logger.trace(nextLine[0]);
        if (iRow >= 3) {
          // 宝贝价格
          logger.debug(nextLine.length);
          // strOldPrice = nextLine[7];

          // logger.debug(nextLine[20]);

          if (3 == iRow) {
            // 初始化工作
            // 拷贝描述图到主图目录下
            selectPicDesc2Main(cMainPicInfo, strCvsName, nextLine[28]);
            logger.debug(nextLine[20]);
            strDescPicPath = getPicPath(nextLine[20]);
            // cutPic();
            cutPic(stAddPicPath);
            strOldPrice = nextLine[7];
          }
          nextLine[7] = RandomUtil.getRandomPrice(nextLine[7]);
          // 33:商家编码
          if (null == nextLine[33] || "".equalsIgnoreCase(nextLine[33].trim())) {
            logger.error("商家编码为空");
            // TextAreaLogAppender.log("商家编码为空");
          }
          // 描述图
          nextLine[20] = nextLine[20].replaceAll("\"\"", "\"");
          nextLine[20] =
              addDescBegin(strDescPicPath) + RandomUtil.getRandomDescPicOrder(nextLine[20])
                  + addDescEnd(strDescPicPath) + addDescCode(nextLine[33]);
          // 新图片，主图顺序
          nextLine[28] = getNewMainPicOrder(nextLine[28], cMainPicInfo);
          // 销售属性
          nextLine[30] = nextLine[30].replaceAll(strOldPrice + ":", nextLine[7] + ":");
          // 商家编码
          nextLine[33] = RandomUtil.getRandomStringInt(12);
          // 商品名称不能超过30个字符
          if (nextLine[0].length() > 30) {
            nextLine[0] = nextLine[0].substring(0, 30);
          }
          // 品牌修改
          nextLine[32] = changeBrand(nextLine[32]);
        }
        vStrings.add(nextLine);
        iRow++;
      }


      JpfCvsUtil.writeToCsv(strCvsName, vStrings);

      JpfCvsUtil.appendCsv("zgb_goods.csv", AiDateTimeUtil.getCurrDate() + "\t" + strCvsName + "\t"
          + vStrings.size() + "\t" + strOldPrice + "\n");
      vStrings.clear();

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

  }


  private String getPicPath(String strDesc) {
    try {
      int iPos1 = strDesc.indexOf("file:///");
      int iPos2 = strDesc.indexOf("contentPic", iPos1);
      if (iPos1 > 0 && iPos2 > iPos1) {
        return strDesc.substring(iPos1, iPos2);
      } else {
        logger.error("图片有问题，可能不在本地");
        logger.error(strDesc);
        TextAreaLogAppender.log("图片有问题，可能不在本地");
      }
    } catch (Exception ex) {
      // TODO: handle exception
      ex.printStackTrace();
      logger.error(strDesc);
    }
    return "";
  }

  /**
   * 
   * @category 增加描述的结尾
   * @author 吴平福
   * @param strPicPath
   * @return update 2017年7月1日
   */
  private String addDescEnd(String strPicPath) {
    if (RunParam.ADD_END_PIC && strPicPath != null && strPicPath.trim().length() > 0) {
      return "<img src=\"https://img.alicdn.com/imgextra/i4/14625668/TB2CKwPbogQMeJjy0FeXXXOEVXa_!!14625668.jpg\" size=\"789x230\"alt=\" end.jpg\"/> ";
    }
    return "";
  }

  /**
   * 
   * @category 增加描述的开始
   * @author 吴平福
   * @param strPicPath
   * @return update 2017年7月1日
   */
  private String addDescBegin(String strPicPath) {
    if (RunParam.ADD_BEGIN_PIC && strPicPath != null && strPicPath.trim().length() > 0) {
      return "<img align=\"absmiddle\" src=\"" + strPicPath + "begin.jpg\" size=\"751x444\">";
    }
    return "";
  }

  /**
   * 
   * @category 根据原有商品编码随机加入字符
   * @author 吴平福
   * @param strCode
   * @return update 2017年7月1日
   */
  private String addDescCode(String strCode) {
    return "<p><span style=\"color:#ffffff;\">"
        + RandomUtil.getShopCode(strCode).replaceAll("\"", "") + "</span></p>";
  }



  /**
   * 
   * @category 从描述图里面选择图片增加到主图
   * @author 吴平福
   * @param cMainPicInfo
   * @param strCvsName
   * @param strPicOrder
   * @throws Exception update 2017年7月24日
   */
  public void selectPicDesc2Main(MainPicInfo cMainPicInfo, String strCvsName,
      final String strPicOrder) throws Exception {

    final String strDescPicPath = strCvsName.substring(0, strCvsName.length() - 4);

    selectPicBySize(strDescPicPath, cMainPicInfo.v_MainPic);

    int iPos = strPicOrder.lastIndexOf(":|");
    String strMainPic = strPicOrder.substring(0, iPos + 1);

    logger.debug(strMainPic);
    String[] strPics = strMainPic.split(";");

    cMainPicInfo.iMainPicCount = strPics.length;

    logger.info("主图数量：" + strPics.length);


    // 将描述图增加到主图VECTOR中
    for (int i = 0; i < cMainPicInfo.v_MainPic.size(); i++) {
      // 新主图文件名
      String strNewFileName = RandomUtil.getRandomLowerStringInt(30);
      AiFileUtil.copyFile(strDescPicPath + "\\" + strNewFileName + ".tbi",
          cMainPicInfo.v_MainPic.get(i));
      cMainPicInfo.v_MainPic.set(i, strNewFileName);
      logger.debug(strNewFileName);
      logger.debug(cMainPicInfo.v_MainPic.get(i));
    }


    logger.debug(cMainPicInfo.v_MainPic.size());
    for (int i = 0; i < strPics.length; i++) {
      if (strPics[i].startsWith("\"")) {
        strPics[i] = strPics[i].substring(1, strPics[i].length());
      }
      iPos = strPics[i].indexOf(":1");
      if (iPos >= 0) {
        strPics[i] = strPics[i].substring(0, iPos);
        cMainPicInfo.v_MainPic.add(strPics[i]);
      }
    }

    // 处理白底图
    cImageUtils.getWhiteGroundPic(strDescPicPath, cMainPicInfo.v_LastMainPic);
    for (int i = 0; i < cMainPicInfo.v_LastMainPic.size(); i++) {
      logger.debug(cMainPicInfo.v_LastMainPic.get(i));
      cMainPicInfo.v_LastMainPic.set(i, cMainPicInfo.v_LastMainPic.get(i)
          .substring(strDescPicPath.length() + 1, cMainPicInfo.v_LastMainPic.get(i).length() - 4));
      logger.debug(cMainPicInfo.v_LastMainPic.get(i));
    }
    // logger.debug(cMainPicInfo.vector.size());

    for (int i = 0; i < cMainPicInfo.v_MainPic.size(); i++) {
      logger.trace(cMainPicInfo.v_MainPic.get(i) + ".tbi");
    }
  }

  /**
   * 
   * @category 根据图片大小选择图片
   * @author 吴平福
   * @param strPicPath
   * @param vector update 2017年7月24日
   */
  public void selectPicBySize(final String strPicPath, Vector<String> vector) {
    try {

      AiFileUtil.getFiles(strPicPath, vector, ".jpg");
      // logger.info(vector.size());
      for (int i = 0; i < vector.size(); i++) {
        String strPicFileName = vector.get(i);
        if (!cImageUtils.justPic(strPicFileName)) {
          vector.remove(i);
          i--;
        }

      }
      logger.info(strPicPath + ": 选中的描述图数量:" + vector.size());
    } catch (Exception ex) {
      // TODO: handle exception
      ex.printStackTrace();
    }
  }

  /**
   * 
   * @category 随机修改主图顺序
   * @author 吴平福
   * @param strPicOrder
   * @return update 2017年6月17日
   */
  public String getNewMainPicOrder(String strPicOrder, MainPicInfo cMainPicInfo) throws Exception {
    try {
      if (cMainPicInfo.v_MainPic.size() == 0) {
        return strPicOrder;
      }
      logger.debug(strPicOrder);

      if (strPicOrder.startsWith("\"")) {
        strPicOrder = strPicOrder.substring(1, strPicOrder.length());
      }
      if (strPicOrder.endsWith("\"")) {
        strPicOrder = strPicOrder.substring(0, strPicOrder.length() - 1);
      }

      // logger.debug(strPicOrder);
      int iPos = strPicOrder.lastIndexOf(":|;");
      if (iPos > 0) {

        StringBuilder sb = new StringBuilder();
        int iMaxLength = cMainPicInfo.v_MainPic.size();
        if (iMaxLength >= 5) {
          iMaxLength = 5;
        }
        // 多取一个，用着没有白底图的替补
        String[] strOders =
            RandomUtil.getRandomInt(cMainPicInfo.v_MainPic.size(), iMaxLength).split(",");

        for (int i = 0; i < iMaxLength - 1; i++) {
          sb.append(cMainPicInfo.v_MainPic.get(Integer.parseInt(strOders[i])) + ":1:" + i + ":|;");
        }
        if (cMainPicInfo.v_LastMainPic.size() > 0) {
          int iLastMainPic = RandomUtil.getRandomInt(cMainPicInfo.v_LastMainPic.size());
          sb.append(
              cMainPicInfo.v_LastMainPic.get(iLastMainPic) + ":1:" + (iMaxLength - 1) + ":|;");
        } else {

          sb.append(cMainPicInfo.v_MainPic.get(Integer.parseInt(strOders[iMaxLength - 1])) + ":1:"
              + (iMaxLength - 1) + ":|;");
        }
        sb.append(strPicOrder.substring(iPos + 3, strPicOrder.length()));
        logger.debug(sb);
        return sb.toString();

      } else {
        // 没有主图
        return strPicOrder;
      }



    } catch (Exception ex) {
      // TODO: handle exception
      ex.printStackTrace();
      logger.error(strPicOrder);
      throw ex;
    }
  }

  public void FileRename(String OldName, String NewName) throws Exception {
    File newFile = new File(NewName);
    if (newFile.exists() && newFile.isFile()) {
      newFile.delete();
    }
    File file = new File(OldName);
    file.renameTo(new File(NewName));
    file.delete();
  }

  /**
   * 
   * @category 裁剪TBI
   * @author 吴平福
   * @param strCvsName
   * @throws Exception update 2017年7月6日
   */
  public void cutPic(String strMainPicPath) throws Exception {

    Vector<String> v_tbi = new Vector<String>();
    AiFileUtil.getFiles(strMainPicPath, v_tbi, ".tbi");
    logger.info("主图数量:" + v_tbi.size());
    TextAreaLogAppender.log("主图数量:" + v_tbi.size());
    for (int i = 0; i < v_tbi.size(); i++) {
      String tbiImg = v_tbi.get(i);
      if (AiFileUtil.getFileSize(tbiImg) == 0) {
        AiFileUtil.delFile(tbiImg);
        continue;
      }

      cImageUtils.resize(tbiImg, 500, 0.9f);
      if (RunParam.b_WaterPic) {
        cImageUtils.generateWaterFile(tbiImg, "D:\\zgb\\begin.jpg");
      }


    }

    // 描述图修改
    v_tbi.clear();
    AiFileUtil.getFiles(strMainPicPath, v_tbi, ".jpg");
    logger.info("描述图数量:" + v_tbi.size());
    TextAreaLogAppender.log("描述图数量:" + v_tbi.size());
    for (int i = 0; i < v_tbi.size(); i++) {
      String tbiImg = v_tbi.get(i);
      if (AiFileUtil.getFileSize(tbiImg) == 0) {
        AiFileUtil.delFile(tbiImg);
        continue;
      }

      cImageUtils.resize(tbiImg, 640, 0.9f);

    }
    v_tbi.clear();
  }



}
