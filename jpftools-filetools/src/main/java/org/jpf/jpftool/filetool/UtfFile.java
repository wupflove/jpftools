package org.jpf.jpftool.filetool;

import java.io.*;
import java.util.*;

/**
 * <p>�ļ�����</p>
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 * @author ��ƽ��
 * @version 1.0
 */

public class UtfFile
{
  //������ļ���Ŀ
  private static long FileWriteCount = 0;

  //��ȡ�ļ�Ŀ¼�µ��ļ��б�
  private Vector g_Vector = new Vector();

  //д��������ļ�
  private String OutPropFileName = "d://sh.properties";

  //��ȡ�ļ���Ŀ¼
  private String InFilePath = "C://temp//aa";

  //�Ѿ����ڵ������ļ�
  private String ExistPropFileName = "d://WBASSLANG_zh_CN.properties";

  //������ʹ�õ����
  private static int PropNum = 0;

  //д���õĻ���
  StringBuffer propBuf = new StringBuffer();
  //����������
  private Vector v_China = new Vector();

  //�ų����ļ�
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
   * @todo ȡҪ�г���PROP��KEYֵ����d://aa//bb//cc.txt ,KEYֵ bb_cc_
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
   * @todo ����ת��ΪUTF-8
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

  public UtfFile(int i)
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
  public UtfFile()
  {
    try
    {
      System.out.println("����ļ�...");
      long sTime = System.currentTimeMillis();
      init();
      ListFile(InFilePath);
      UftFile();
      System.out.println("�ļ�����" + g_Vector.size());
      System.out.println("д���ļ�����" + FileWriteCount);

      long eTime = System.currentTimeMillis();
      System.out.println("�����ļ���ʱ(��λMS):" + (eTime - sTime));
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }

  }

  /**
   * @todo ��ʼ��
   * @throws Exception
   */
  private void init()
      throws Exception
  {

    //v_China
    System.out.println("���ļ�:" + ExistPropFileName);
    File f1 = new File(ExistPropFileName);
    if (!f1.exists() && !f1.canRead())
    {
      System.out.println("�ļ�û���ҵ�");
      return;
    }
    BufferedReader in = new BufferedReader(new FileReader(ExistPropFileName)); //����BufferedReader���󣬲�ʵ����Ϊbr
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
   * @todo �����ļ�
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
   * �õ�·���ָ������ļ�·����ָ��λ��ǰ�����ֵ�λ�á�
   * ����DOS����UNIX���ķָ��������ԡ�
   * @param fileName �ļ�·��
   * @param fromIndex ��ʼ���ҵ�λ��
   * @return ·���ָ�����·����ָ��λ��ǰ�����ֵ�λ�ã�û�г���ʱ����-1��
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
   * �õ�·���ָ������ļ�·���������ֵ�λ�á�
   * ����DOS����UNIX���ķָ��������ԡ�
   * @param fileName �ļ�·��
   * @return ·���ָ�����·���������ֵ�λ�ã�û�г���ʱ����-1��
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
   * �õ��ļ������ֲ��֡�
   * ʵ���Ͼ���·���е����һ��·���ָ�����Ĳ��֡�
   * @param fileName �ļ���
   * @return �ļ����е����ֲ���
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
   * @todo ȡ�ļ������������ļ�����
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
   * @todo д���ļ�
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
      System.out.println("���ļ�:" + in_FileName);
      File f1 = new File(in_FileName);
      if (!f1.exists() && !f1.canRead())
      {
        System.out.println("�ļ�û���ҵ�");
        continue;
      }
      BufferedReader in = new BufferedReader(new FileReader(in_FileName)); //����BufferedReader���󣬲�ʵ����Ϊbr
      StringBuffer sb = new StringBuffer();
      String Line;
      PropKeyOfFile = GetPropKeyName(in_FileName);

      PrintWriter pwOK = new PrintWriter(new FileOutputStream(in_FileName +
          ".utf"));
      BufferedWriter out = new BufferedWriter(pwOK);

      while ( (Line = in.readLine()) != null)
      {
        FileWriteCount++;
        //�ж��ǲ���ע����
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
      //�ļ��ر�
      out.close();
      pwOK.close();
      WritePropFile();
    }

  }

  /**
   * @todo ���������ļ�д��properties
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
   * @todo �ҵ����Ĳ��滻��ͬʱд��properties
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
            //û���ҵ�
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
        //û���ҵ�
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
   * @todo ȡ�����ڻ����е�λ�á�-1:û���ҵ�,���ڵ���0:�ҵ�
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
      UtfFile a = new UtfFile();

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
