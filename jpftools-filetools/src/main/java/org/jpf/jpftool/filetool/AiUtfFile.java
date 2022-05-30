/** 
* @author 吴平福 
* E-mail:421722623@qq.com 
* @version 创建时间：2014年4月16日 下午4:27:18 
* 类说明 
*/ 

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

public class AiUtfFile
{
  //处理的文件数目
  private static long FileWriteCount = 0;

  //读取文件目录下的文件列表
  private Vector g_Vector = new Vector();

  //写入的配置文件
  private String OutPropFileName = "d://sh.properties";

  //读取文件的目录
  private String InFilePath = "C://temp//aa";

  //已经存在的配置文件
  private String ExistPropFileName = "d://WBASSLANG_zh_CN.properties";

  //配置中使用的序号
  private static int PropNum = 0;

  //写配置的缓存
  StringBuffer propBuf = new StringBuffer();
  //读出的中文
  private Vector v_China = new Vector();

  //排除的文件
  private String PreclusiveFileName = "BO";

  //
  private String PropKeyOfFile = "";
  private final static String ReplaceCode = ".";
  private String ReplaceAll(String in_str)
  {
    String m_str = "";
    int i_index = in_str.indexOf("\\");
    while (i_index > 0)
    {
      m_str = in_str.substring(0, i_index) + ReplaceCode;
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

    in_FullPathName = in_FullPathName.substring(InFilePath.length() - 1);
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

  public AiUtfFile(int i)
      throws Exception
  {
    //ReplaceStr("if(rowset.getRow()!=-1) {");
    /*
         FileInputStream fis = new FileInputStream("C:\\temp\\bb\\oper.jsp");
         String fc = this.getString(fis, "GBK");
         StringBuffer sb = new StringBuffer(fc);
         doRangeCommentArea(sb);
         doSlashCommentArea(sb);
         doJspCommentArea(sb);
         doHtmlCommentArea(sb);
         System.out.println(sb);
     */

  }

  /**
   *
   */
  public AiUtfFile()
  {
    try
    {
      System.out.println("检查文件...");
      long sTime = System.currentTimeMillis();
      init();
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
   * @todo 初始化
   * @throws Exception
   */
  private void init()
      throws Exception
  {

    //v_China
    System.out.println("打开文件:" + ExistPropFileName);
    File f1 = new File(ExistPropFileName);
    if (!f1.exists() && !f1.canRead())
    {
      System.out.println("文件没有找到");
      return;
    }
    BufferedReader in = new BufferedReader(new FileReader(ExistPropFileName)); //建立BufferedReader对象，并实例化为br
    String Line;
    String m_str = "";
    while ( (Line = in.readLine()) != null)
    {
      if ("".endsWith(Line.trim()))
      {
        continue;
      }
      m_str = Line.trim();
      int i = m_str.indexOf("=");
      if (i > 0)
      {
        PropKey pk = new PropKey();
        pk.keyName = m_str.substring(0, i).trim();
        pk.keyValue = m_str.substring(i).trim();
        v_China.add(pk);
      }

    }
    in.close();
    //test_VChina();
  }

  private void test_VChina()
  {
    for (int i = 0; i < v_China.size(); i++)
    {
      PropKey pk = (PropKey)v_China.get(i);
      System.out.println("KEY=" + pk.keyName + " VALUE=" + pk.keyValue);
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
        if (f2[i].toString().indexOf(PreclusiveFileName) < 0)
        {
          g_Vector.add(f2[i].toString());
        }
      }
    }
  }

  /**
   * 得到路径分隔符在文件路径中指定位置前最后出现的位置。
   * 对于DOS或者UNIX风格的分隔符都可以。
   * @param fileName 文件路径
   * @param fromIndex 开始查找的位置
   * @return 路径分隔符在路径中指定位置前最后出现的位置，没有出现时返回-1。
   * @since  0.5
   */
  public static int getPathLastIndex(String fileName, int fromIndex)
  {
    int point = fileName.lastIndexOf('/', fromIndex);
    if (point == -1)
    {
      point = fileName.lastIndexOf('\\', fromIndex);
    }
    return point;
  }

  /**
   * 得到路径分隔符在文件路径中最后出现的位置。
   * 对于DOS或者UNIX风格的分隔符都可以。
   * @param fileName 文件路径
   * @return 路径分隔符在路径中最后出现的位置，没有出现时返回-1。
   * @since  0.5
   */
  public static int getPathLastIndex(String fileName)
  {
    int point = fileName.lastIndexOf('/');
    if (point == -1)
    {
      point = fileName.lastIndexOf('\\');
    }
    return point;
  }

  /**
   * 得到文件的名字部分。
   * 实际上就是路径中的最后一个路径分隔符后的部分。
   * @param fileName 文件名
   * @return 文件名中的名字部分
   * @since  0.5
   */
  public static String getNamePart(String fileName)
  {
    int point = getPathLastIndex(fileName);
    int length = fileName.length();
    if (point == -1)
    {
      return fileName;
    }
    else if (point == length - 1)
    {
      int secondPoint = getPathLastIndex(fileName, point - 1);
      if (secondPoint == -1)
      {
        if (length == 1)
        {
          return fileName;
        }
        else
        {
          return fileName.substring(0, point);
        }
      }
      else
      {
        return fileName.substring(secondPoint + 1, point);
      }
    }
    else
    {
      return fileName.substring(point + 1);
    }
  }

  /**
   * @todo 取文件名，不包含文件类型
   * @param fileName String
   * @return String
   */
  private String getPreNamePart(String fileName)
  {
    String m_str = getNamePart(fileName);
    int point = m_str.lastIndexOf(".");
    if (point >= 0)
    {
      return m_str.substring(0, point);
    }
    return m_str;
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
      BufferedReader in = new BufferedReader(new FileReader(in_FileName)); //建立BufferedReader对象，并实例化为br
      StringBuffer sb = new StringBuffer();
      String Line;
      PropKeyOfFile = GetPropKeyName(in_FileName);

      PrintWriter pwOK = new PrintWriter(new FileOutputStream(in_FileName +
          ".utf"));
      BufferedWriter out = new BufferedWriter(pwOK);

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
        m = Line.indexOf("<!--");
        if (m >= 0)
        {
          sb.append(Line).append("\r\n");
          continue;
        }
        sb.append(ReplaceStr(Line));
        sb.append("\r\n");

      }

      in.close();
      out.write(sb.toString());
      sb.setLength(0);
      //文件关闭
      out.close();
      pwOK.close();
      WritePropFile();
    }

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
   * @todo 找到中文并替换，同时写入properties
   * @param str String
   * @throws Exception
   * @return String
   */
  private String ReplaceStr(String str)
      throws Exception
  {
    StringBuffer sb = new StringBuffer();
    StringBuffer new_sb = new StringBuffer();

    boolean m_ischina = false;
    for (int i = 0; i < str.length(); i++)
    {
      String m_str = str.substring(i, i + 1);
      if ("".equalsIgnoreCase(m_str.trim()))
      {
        sb.append(" ");
        if (m_ischina == false)
        {
          new_sb.append(" ");
        }
        continue;
      }
      if (m_str.matches("[\\u4e00-\\u9fa5]+"))
      {
        sb.append(m_str);
        m_ischina = true;
      }
      else
      {
        if (m_ischina == true && sb.length() > 0)
        {
          String str_china = sb.toString().trim();
          PropNum = getChinaNum(str_china);
          if (PropNum < 0)
          {
            PropNum = v_China.size();
            //没有找到
            propBuf.append("#").append(sb.toString().trim()).append("\r\n")
                .append(PropKeyOfFile).append(PropNum).
                append("=")
                .append(toUtf8String(str_china)).append("\r\n");

          }
          else
          {
            PropNum = PropNum + 1;
          }
          new_sb.append("LangUtil.getBackLang(\"")
              .append(PropKeyOfFile).append(PropNum).append("\")");
          sb.setLength(0);
        }
        new_sb.append(m_str);
        m_ischina = false;
      }

    }
    if (m_ischina == true)
    {
      String str_china = sb.toString().trim();
      PropNum = getChinaNum(str_china);
      if (PropNum < 0)
      {
        PropNum = v_China.size();
        //没有找到
        propBuf.append("#").append(sb.toString().trim()).append("\r\n")
            .append(PropKeyOfFile).append(PropNum).
            append("=")
            .append(toUtf8String(str_china)).append("\r\n");

      }
      else
      {
        PropNum = PropNum + 1;
      }
      new_sb.append("<%=LangUtil.getLang(request,").append("\"")
          .append(PropKeyOfFile).append(PropNum).append("\"")
          .append(")/*").append(str_china).append("*/%>");
      sb.setLength(0);

    }
    System.out.println(new_sb.toString());
    return new_sb.toString();
  }

  /**
   * @todo 取中文在缓存中的位置。-1:没有找到,大于等于0:找到
   * @param in_str String
   * @throws Exception
   * @return int
   */
  private int getChinaNum(String in_str)
      throws Exception
  {
    int i = -1;
    in_str = toUtf8String(in_str);
    for (int j = 0; j < v_China.size(); j++)
    {
      PropKey m_PropKey = (PropKey)v_China.get(j);
      if (in_str.equals(m_PropKey.keyValue.trim()))
      {
        i = j;
        break;
      }
    }
    if (i < 0)
    {
      PropKey m_PropKey = new PropKey();
      m_PropKey.keyName = in_str;
      m_PropKey.keyValue = "";
      v_China.add(m_PropKey);
    }
    return i;

  }

  public static void main(String[] args)
  {
    try
    {
      //String a="aa//bb//";
      //System.out.println(a.replaceAll("//","11"));
      //UtfFile a = new UtfFile();
      System.out.println(toUtf8String("统计结果"));
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
  }

  class PropKey
  {
    String keyName;
    String keyValue;
  }

}
