package org.jpf.stocks;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Vector;

import org.jpf.utils.conf.JpfConfigUtil;
import org.jpf.utils.excelutils.JpfExcelJxlUtil;
import org.jpf.utils.ios.JpfFileUtil;

import jxl.Sheet;
import jxl.Workbook;
import jxl.write.WritableSheet;

/**
 *
 * <p>
 * Title:
 * </p>
 * <p>
 * Description: 基类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author wupingfu
 * @version 1.0
 */
public class StockBaseClass {
  // 输入文件目录
  public String InFilePath;

  // 输出文件名称(带目录)
  public String OutTextFileName;
  public String OutExeclFileName;;

  // 关键字前面行数
  public int RowForward = 0;

  // 关键字后面行数
  public int RowAfter = 0;

  private Vector g_LineVector = new Vector();
  // 文件列表
  public Vector g_FileVector = new Vector();

  // 不分析的股票代码
  private String OutStock;

  // 是否继续查找
  public boolean bFindResult = true;

  // 是否要从资料到文件文件
  private String ISMAKETEXTFILE;
  // #是否要从TEXT到EXCEL，0 不，1 是
  private String ISMAKEEXCELTFILE;

  private String strConfigName = "";

  public StockBaseClass() {
    try {
      // 是否要从资料到文件文件
      ISMAKETEXTFILE = JpfConfigUtil.getStrFromConfig(strConfigName, "ISMAKETEXTFILE");
      // #是否要从TEXT到EXCEL，0 不，1 是
      ISMAKEEXCELTFILE = JpfConfigUtil.getStrFromConfig(strConfigName, "ISMAKEEXCELTFILE");
      OutStock = JpfConfigUtil.getStrFromConfig(strConfigName, "OutStock");
      InFilePath = JpfConfigUtil.getStrFromConfig(strConfigName, "InFilePath");

      // 输出文件名称(带目录)
      OutTextFileName = JpfConfigUtil.getStrFromConfig(strConfigName, "OutTextFileName");
      OutExeclFileName = JpfConfigUtil.getStrFromConfig(strConfigName, "OutExeclFileName");;
    } catch (Exception e) {
      // TODO: handle exception
      e.printStackTrace();
    }
  }

  public void DoWork() {
    try {
      System.out.println("检查文件...");
      long sTime = System.currentTimeMillis();

      if (ISMAKETEXTFILE.equalsIgnoreCase("1")) {

        JpfFileUtil.getFiles(InFilePath, g_FileVector);
        CheckFile();
      }
      if (ISMAKEEXCELTFILE.equalsIgnoreCase("1")) {
        WriteToExecl();
      }
      long eTime = System.currentTimeMillis();
      System.out.println("处理文件用时(单位MS):" + (eTime - sTime));
    } catch (Exception ex) {
      ex.printStackTrace();
    }

  }

  /**
   * 检查文件
   * 
   * @throws Exception
   */
  public void CheckFile() throws Exception {
    String in_FileName = "";
    PrintWriter pwOK = new PrintWriter(new FileOutputStream(OutTextFileName, false));

    BufferedWriter out = new BufferedWriter(pwOK);
    for (int i = 0; i < g_FileVector.size(); i++) {
      in_FileName = (String) g_FileVector.get(i);
      // 不分析特定的股票
      if (RemoveStock(in_FileName)) {
        continue;
      }

      System.out.println("打开文件:" + in_FileName);
      File f1 = new File(in_FileName);
      if (!f1.exists() && !f1.canRead()) {
        System.out.println("文件没有找到");
        continue;
      }
      BufferedReader in =
          new BufferedReader(new InputStreamReader(new FileInputStream(in_FileName), "GB2312"));
      StringBuffer sb = new StringBuffer();
      String Line;
      int iRow = 0;
      bFindResult = true;
      String strStockNameCode = "";
      while ((Line = in.readLine()) != null && bFindResult) {
        // 支持中文
        Line = Line.trim();
        // 提取股票代码
        if (0 == iRow) {
          strStockNameCode = GetStockNameCode(Line);
        }

        iRow++;
        if (RowForward >= 0) {
          if (g_LineVector.size() > RowForward) {
            g_LineVector.remove(0);
          }
          g_LineVector.add(Line);
        }

        if (FindStr(Line)) {
          for (int j = 0; j < g_LineVector.size(); j++) {
            sb.append((String) g_LineVector.get(j)).append("\r\n");
          }
          // sb.append(Line).append("\r\n");
        }
      }
      System.out.println(sb.toString());

      in.close();
      if (sb.length() > 0) {
        out.write(strStockNameCode + "\r\n");
        out.write(sb.toString());
        sb.setLength(0);
      }
    }
    out.close();
    pwOK.close();
  }

  /**
   *
   * @param inStr String
   * @throws Exception
   * @return boolean
   */
  public boolean FindStr(String inStr) throws Exception {
    return false;
  }

  /**
   * 根据第一行取股票代码和名称
   * 
   * @param inStr String
   * @throws Exception
   * @return String
   */
  private String GetStockNameCode(String inStr) throws Exception {
    String[] m_str = inStr.split("≈≈");
    return m_str[1];
  }

  /**
   * 排出不需要的股票
   * 
   * @param inFileName String
   * @return boolean
   */
  private boolean RemoveStock(String inFileName) {
    int i = inFileName.lastIndexOf(java.io.File.separator);
    if (i > 0) {
      inFileName = inFileName.substring(i + 1, inFileName.length() - 4);
      if (OutStock.indexOf(inFileName) >= 0) {
        return true;
      }
    }
    // System.out.println(inFileName);
    return false;
  }

  /**
   * @todo 增加表头
   * @param m_sheet WritableSheet
   * @param strTitle String
   * @throws Exception
   */
  public void AddTitle(WritableSheet m_sheet, String strTitle) throws Exception {
    JpfExcelJxlUtil.addTitle(m_sheet, strTitle);
  }

  /**
   * 写入到execl
   */
  public void WriteToExecl() {
    jxl.Workbook rw = null;
    jxl.write.WritableWorkbook wwb = null;
    jxl.write.WritableSheet ws = null;
    BufferedReader in = null;
    String Line = "";

    try {

      // 创建只读的Excel工作薄的对象
      rw = jxl.Workbook.getWorkbook(new File(OutExeclFileName));
      // 创建可写入的Excel工作薄对象
      wwb = Workbook.createWorkbook(new File(OutExeclFileName), rw);

      Sheet[] sts = wwb.getSheets();
      int iSheet = -1;
      String strSheetName = this.getSheetName();
      for (int i = 0; i < sts.length; i++) {
        System.out.println(sts[i].getName());
        if (sts[i].getName().equalsIgnoreCase(strSheetName)) {
          iSheet = i;
          break;
        }
      }

      if (iSheet == -1) {
        ws = wwb.createSheet(strSheetName, 0);
      } else {
        ws = wwb.getSheet(iSheet);
      }

      // 增加title
      AddTitle(ws, getColNames());
      // 增加正文
      in = new BufferedReader(
          new InputStreamReader(new FileInputStream(OutTextFileName), "GB2312"));
      getContentFromExcel(in, ws);
    } catch (FileNotFoundException ex) {
      System.out.println("文件没有找到");
      ex.printStackTrace();
    } catch (Exception ex) {
      System.out.println(Line);
      ex.printStackTrace();
    } finally {
      try {
        if (in != null) {
          in.close();
          // 写入Excel对象
        }
        if (wwb != null) {
          wwb.write();
          // 关闭可写入的Excel对象
        }
        if (wwb != null) {
          wwb.close();
          // 关闭只读的Excel对象
        }
        if (rw != null) {
          rw.close();

        }
      } catch (Exception ex) {
      }

    }
  }

  public String getSheetName() {
    return "";
  }

  public String getColNames() {
    return "";
  }

  public void getContentFromExcel(BufferedReader in, jxl.write.WritableSheet ws) throws Exception {

  }
}
