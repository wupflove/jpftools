/**
 * @author 吴平福 E-mail:421722623@qq.com
 * @version 创建时间：2012-5-9 上午10:12:27 类说明
 */

package org.jpf.jpftool.filetool;

import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jpf.utils.encrypts.MD5Filter;
import org.jpf.utils.ios.JpfFileUtil;



/**
 * 
 */
public class FindDupFile {

  private static final Logger logger = LogManager.getLogger();

  public static void main(String[] args) throws Exception {

    if (1 == args.length) {
      FindDupFile cFindDupFile = new FindDupFile(args[0], "pdf;pptx;");
    } else {
      FindDupFile cFindDupFile =
          new FindDupFile("F:\\private\\webchat\\WeChat Files\\wupflove\\Files", "pdf;pptx;");
    }
    System.out.println("game over");
  }

  /**
   * 
   * @param strFilePath
   */
  public FindDupFile(final String strFilePath, final String fileFilter) {
    try {
      logger.info("检查文件...");
      long sTime = System.currentTimeMillis();

      Vector<String> fileNames = new Vector<String>();
      JpfFileUtil.getFiles(strFilePath, fileNames, fileFilter);
      logger.info("查找到文件数:" + fileNames.size());
      for (int i = 0; i < fileNames.size(); i++) {
        logger.info(i + ":" + fileNames.get(i));

      }
      Vector<FileInfo> fileInfos = new Vector<FileInfo>();
      for (int i = 0; i < fileNames.size(); i++) {
        if (i % 100 == 0) {
          logger.info("增加文件数:" + fileInfos.size());
        }
        FileInfo cFileInfo = new FileInfo();
        cFileInfo.FileName = fileNames.get(i);
        cFileInfo.FileLength = JpfFileUtil.FileSize(fileNames.get(i));
        cFileInfo.FileMd5 = MD5Filter.getMd5ByFile(fileNames.get(i));
        fileInfos.add(cFileInfo);

      }
      logger.info("增加文件总数:" + fileInfos.size());
      fileNames.clear();
      Collections.sort(fileInfos, new Comparator<FileInfo>() {
        @Override
        public int compare(FileInfo f1, FileInfo f2) {
          long diff = JpfFileUtil.FileSize(f1.FileName) - JpfFileUtil.FileSize(f2.FileName);
          if (diff > 0)
            return 1;
          else if (diff == 0)
            return 0;
          else
            return -1;
        }

        @Override
        public boolean equals(Object obj) {
          return true;
        }
      });

      /*
       * for (int iPos = 0; iPos < fileInfos.size(); iPos++) {
       * System.out.println(fileInfos.get(iPos).FileName+" "+
       * fileInfos.get(iPos).FileLength+" "+fileInfos.get(iPos).FileMd5); }
       */
      for (int iPos = 0; iPos < fileInfos.size(); iPos++) {
        // System.out.println(fileInfos.get(i).FileName+":"+fileInfos.get(i).FileLength);
        int i = iPos + 1;
        while (i < fileInfos.size()) {
          if (fileInfos.get(iPos).FileLength != fileInfos.get(i).FileLength) {
            break;
          } else {
            if (fileInfos.get(iPos).FileMd5.equalsIgnoreCase(fileInfos.get(i).FileMd5)) {
              String fileName = fileInfos.get(i).FileName;
              fileInfos.remove(i);
              logger.info(fileInfos.get(i).FileName + "  删除文件：" + fileName);
              // AiFileUtil.delFile(fileName);
              i--;
            }
          }
          i++;
        }
      }

      logger.info("剩余文件数:" + fileInfos.size());

      // FindDupNameFile(fileInfos);

      long eTime = System.currentTimeMillis();
      logger.info("处理文件用时(单位MS):" + (eTime - sTime));
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  class FileInfo {
    String FileName;
    long FileLength;
    String FileMd5;

  }

}
