package org.jpf.jpftool.filetool;

import java.io.*;
import java.util.*;

/**
 * <p>文件翻译</p>
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 * @author 吴平福
 * @version 1.0
 */

public class UtfJavaFile
{
  //处理的文件数目
  private static long FileWriteCount = 0;

  //读取文件目录下的文件列表
  private Vector g_Vector = new Vector();

  //写入的配置文件
  private String OutPropFileName = "d://sh.properties";

  //读取文件的目录
  private String InFilePath = "C:\\temp\\aa\\com\\openboss\\openjs";

  //配置中使用的序号
  private static int PropNum = 0;

  //写配置的缓存
  StringBuffer propBuf = new StringBuffer();

  //
  private String PropKeyOfFile = "";
  private final static String ReplaceCode = ".";
  private final static String KeyString = "\"";

  private static boolean b_HaveChinaStr = false;

  /**
   * @todo 判断是否是中文
   * @param in_str String
   * @return boolean
   */
  private boolean IsChina(String in_str)
  {
    String m_str = toUtf8String(in_str);
    return m_str.length() > 4;
  }

  /**
   * @todo 替换字符
   * @param in_str String
   * @return String
   */
  private String ReplaceAll(String in_str)
  {
    String m_str = "";
    int i_index = in_str.indexOf("\\");
    while (i_index > 0)
    {
      m_str += in_str.substring(0, i_index) + ReplaceCode;
      in_str = in_str.substring(i_index + 1);
      i_index = in_str.indexOf("\\");
    }
    m_str += in_str;
    return m_str;
  }

  /**
   * @todo 取要列出的PROP的KEY值。如d://aa//bb//cc.txt ,KEY值 bb_cc_
   * @param in_FullPathName String
   * @throws Exception
   * @return String
   */
  private String GetPropKeyName(String in_FullPathName)
      throws Exception
  {

    System.out.println(in_FullPathName);
    in_FullPathName = in_FullPathName.substring(InFilePath.length() + 1,
                                                in_FullPathName.length());

    int i = in_FullPathName.lastIndexOf(".");
    if (i > 0)
    {
      in_FullPathName = in_FullPathName.substring(0, i);
    }
    in_FullPathName = ReplaceAll(in_FullPathName);

    in_FullPathName += "_";
    System.out.println(in_FullPathName);
    return in_FullPathName;
  }

  /**
   * @todo 中文转换为UTF-8
   * @param s String
   * @return String
   */
  public static String toUtf8String(String s)
  {
    String unicode = "";
    char[] charAry = new char[s.length()];
    for (int i = 0; i < charAry.length; i++)
    {
      charAry[i] = (char)s.charAt(i);
      unicode += "\\u" + Integer.toString(charAry[i], 16);
    }
    return unicode;

  }

  /**
   *
   */
  public UtfJavaFile()
  {
    try
    {
      System.out.println("检查文件...");
      long sTime = System.currentTimeMillis();
      ListFile(InFilePath);
      UftFile();
      System.out.println("文件总数" + g_Vector.size());
      System.out.println("写入文件行数" + FileWriteCount);

      long eTime = System.currentTimeMillis();
      System.out.println("处理文件用时(单位MS):" + (eTime - sTime));
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }

  }

  /**
   * @todo 查找文件
   * @param in_FilePath String
   */
  private void ListFile(String in_FilePath)
  {
    File f1 = new File(in_FilePath);
    if (!f1.isDirectory())
    {
      return;
    }
    File[] f2 = f1.listFiles();
    for (int i = 0; i < f2.length; i++)
    {
      if (f2[i].isDirectory())
      {
        ListFile(f2[i].toString());
      }
      else
      {
        if (!f2[i].toString().endsWith("Bean.java") &&
            !f2[i].toString().endsWith("Engine.java") &&
            !f2[i].toString().endsWith("Map.java"))
        {
          g_Vector.add(f2[i].toString());
        }
      }
    }
  }

  /**
   * @todo 写入文件
   * @param in_FileName String
   * @return long
   */
  private void UftFile()
      throws Exception
  {

    String in_FileName = "";
    for (int i = 0; i < g_Vector.size(); i++)
    {
      in_FileName = (String)g_Vector.get(i);
      System.out.println("打开文件:" + in_FileName);
      File f1 = new File(in_FileName);
      if (!f1.exists() && !f1.canRead())
      {
        System.out.println("文件没有找到");
        continue;
      }
      BufferedReader in = new BufferedReader(new InputStreamReader(new
          FileInputStream(in_FileName), "GB2312"));

      //BufferedReader in = new BufferedReader(new FileReader(in_FileName)); //建立BufferedReader对象，并实例化为br
      StringBuffer sb = new StringBuffer();
      String Line;
      PropKeyOfFile = GetPropKeyName(in_FileName);

      PropNum = 0;
      b_HaveChinaStr = false;
      while ( (Line = in.readLine()) != null)
      {
        FileWriteCount++;
        //判断是不是注释行
        int m = Line.indexOf("//");
        if (m >= 0)
        {
          sb.append(Line).append("\r\n");
          continue;
        }

        m = Line.indexOf("/*");
        if (m >= 0)
        {
          sb.append(Line).append("\r\n");
          continue;
        }

        sb.append(GetChinaStr(Line));
        sb.append("\r\n");

      }

      in.close();
      f1.delete();
      if (b_HaveChinaStr)
      {
        PrintWriter pwOK = new PrintWriter(new FileOutputStream(in_FileName));
        BufferedWriter out = new BufferedWriter(pwOK);
        out.write(sb.toString());
        out.close();
        pwOK.close();
      }
      sb.setLength(0);
      //文件关闭

    }
    WritePropFile();
  }

  /**
   * @todo 将缓存中文件写入properties
   * @throws Exception
   */
  private void WritePropFile()
      throws Exception
  {
    System.out.println(propBuf.toString());

    PrintWriter pwProp = new PrintWriter(new FileOutputStream(OutPropFileName));
    BufferedWriter outProp = new BufferedWriter(pwProp);
    outProp.write(propBuf.toString());
    propBuf.setLength(0);
    outProp.close();
    pwProp.close();

  }

  /**
   * @todo 判断是否有中文
   * @param in_str String
   * @return boolean
   */
  private boolean HaveChinaStr(String in_str)
  {
    for (int i = 0; i < in_str.length(); i++)
    {
      if (IsChina(in_str.substring(i, i + 1)))
      {
        return true;
      }
    }
    return false;
  }

  private String GetChinaStr(String in_str)
  {
    StringBuffer sb = new StringBuffer();
    //System.out.println(in_str);
    String[] s = in_str.split(KeyString);
    //System.out.println(s.length);
    boolean b_IsChina = false;
    for (int i = 0; i < s.length; i++)
    {
      if (i % 2 == 1)
      {
        //System.out.println(s[i]);
        if (HaveChinaStr(s[i]))
        {
          PropNum = PropNum + 1;
          sb.append(ReplaceStr(s[i]));
          b_IsChina = true;
          b_HaveChinaStr = true;
        }
        else
        {
          sb.append(KeyString).append(s[i]);
          b_IsChina = false;
        }

      }
      else
      {
        if (i > 1 && !b_IsChina)
        {
          sb.append(KeyString);
        }
        sb.append(s[i]);
        b_IsChina = false;
      }
    }
    if (!b_IsChina && in_str.endsWith(KeyString))
    {
      sb.append(KeyString);
    }
    return sb.toString();
  }

  /**
   * @todo 找到中文并替换，同时写入properties
   * @param str String
   * @throws Exception
   * @return String
   */
  private String ReplaceStr(String str)
  {

    //没有找到
    propBuf.append("#").append(str).append("\r\n")
        .append(PropKeyOfFile).append(PropNum).
        append("=")
        .append(toUtf8String(str)).append("\r\n");

    StringBuffer new_sb = new StringBuffer();
    new_sb.append("LangUtil.getBackLang(\"")
        .append(PropKeyOfFile).append(PropNum).append("\")");

    //System.out.println(new_sb.toString());
    return new_sb.toString();

  }

  public static void main(String[] args)
  {
    try
    {
      //String a="aa//bb//";

      UtfJavaFile a = new UtfJavaFile();

    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
  }

}
