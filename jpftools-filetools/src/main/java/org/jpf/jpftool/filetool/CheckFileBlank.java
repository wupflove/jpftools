package org.jpf.jpftool.filetool;

import java.io.*;

/**
 *
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 * @author 吴平福
 * @version 1.0
 */
public class CheckFileBlank
{
  private static long FileCount = 0;
  private static long FileRow = 0;
  private static String[] STRKEYFORWARD =
      {
      "public", "private"};
  private static String[] STRKEYNEXT =
      {
      "package"};
  public CheckFileBlank()
  {
    System.out.println("检查文件...");
    long sTime = System.currentTimeMillis();
    DelFileBlank2("D:\\studio\\openjs\\src\\com\\asiainfo\\openboss\\openjs\\commons\\utils\\common.java");
    //ListFile("D:\\studio\\openjs\\src\\com\\asiainfo\\openboss\\openjs\\");
    System.out.println("文件总数" + FileCount);
    System.out.println("文件行数" + FileRow);

    long eTime = System.currentTimeMillis();
    System.out.println("处理文件用时(单位MS):" + (eTime - sTime));
  }

  public static void main(String[] args)
  {
    //CheckFileBlank cfb=new CheckFileBlank();

    String CLASSFORNAME = "com.ai.billprint.Util.FileUtil";

    try
    {
      //RuleHtml("E:\\112.dat");

      //b=(FileUtilInterface)Class.forName("com.ai.billprint.Util.FileUtil4").newInstance();
      //b.abc(a);
      //Exception aa=new Exception("cuo");
      //throw new Exception("cuo");
    }

    catch (Exception ex)
    {
      //ex.getMessage();
      ex.printStackTrace();
      System.out.println(ex.getMessage());
    }
    //System.out.print("abc");
  }

  private static void abc(String in_str)
  {
    in_str = "def";
  }

  private static int getPercent(long in_long1, long in_long2)
  {
    if (in_long1 == 0)
    {
      if (in_long2 == 0)
      {
        return 0;
      }
      else
      {
        return 1 * 100 * 100;
      }
    }
    else
    {
      if (in_long2 == 0)
      {
        return 1 * 100 * 100;
      }
      else
      {
        return (int) ( (in_long1 - in_long2) * 2 * 100 * 100 /
                      (in_long1 + in_long2));
      }
    }
  }

  private static String getLastMonth(String strMonth)
  {
    String strLastMonth = null;
    if (strMonth != null)
    {
      int iYear = Integer.parseInt(strMonth.substring(0, 4));
      int iMonth = Integer.parseInt(strMonth.substring(4, 6));
      if (iMonth == 1)
      {
        iYear--;
        iMonth = 12;
      }
      else
      {
        iMonth--;
      }
      if (iMonth < 10)
      {
        strLastMonth = "" + iYear + "0" + iMonth;
      }
      else
      {
        strLastMonth = "" + iYear + "" + iMonth;
      }
    }
    return strLastMonth;
  }

  static void operate(StringBuffer x, StringBuffer y)
  {
    x.append(y);
    y = x;
  }

  public static long RuleHtml(String in_FileName)
      throws Exception
  {
    long count = 0;
    try
    {
      System.out.println("打开文件:" + in_FileName);

      File f1 = new File(in_FileName);

      if (!f1.exists() && !f1.canRead())
      {
        System.out.println("文件没有找到");
      }
      String m_tmpFileName = in_FileName + ".tmp";
      BufferedReader in = new BufferedReader(new FileReader(in_FileName)); //建立BufferedReader对象，并实例化为br
      FileOutputStream b = new FileOutputStream(m_tmpFileName);
      OutputStreamWriter a = new OutputStreamWriter(b);
      BufferedWriter out = new BufferedWriter(a);

      StringBuffer sb = new StringBuffer();
      String Line;
      while ( (Line = in.readLine()) != null)
      {
        /*
                 if (!Line.trim().equalsIgnoreCase(""))
                 {
          count++;
          String[] m_str=Line.split("<");
          System.out.println(Line);
          for(int i=0;i<m_str.length;i++)
          {
            if (m_str[i].trim().startsWith("/"))
            {
              sb.append("<").append(m_str[i].trim());
            }else
            {
                if (!m_str[i].trim().equalsIgnoreCase(""))
                {
                  sb.append("\r\n").append("<").append(m_str[i].trim());
                }
            }
          }


                 }*/
        sb.append(Line).append("\r\n");
      }
      in.close();
      f1.delete();
      out.write(sb.toString());
      out.close();
      sb.setLength(0);
      File f2 = new File(m_tmpFileName);
      f2.renameTo(f1);
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
      return -1;
    }
    return count;

  }

  public static String GetInfo(String in_Str)
      throws Exception
  {
    String m_StrArray[] = in_Str.split("@");
    String m_OutStr[] =
        {
        "=", "<=", ">=", ">", "<"};
    String m_reStr = "0";
    for (int i = 1; i < m_StrArray.length; i++)
    {
      for (int j = 0; j < m_OutStr.length; j++)
      {

        int m = m_StrArray[i].indexOf(m_OutStr[j]);
        if (m > 0)
        {
          m_StrArray[i] = m_StrArray[i].substring(0, m).trim();

        }
      }
      System.out.println(m_StrArray[i]);
      m_reStr = m_reStr + "," + m_StrArray[i];
    }
    System.out.println(m_reStr);
    return m_reStr;

  }

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
        //System.out.println(f2[i].toString());
        FileCount++;
        FileRow += DelFileBlank(f2[i].toString());
      }
    }
  }

  private boolean AddLinePre(String in_str)
  {
    in_str = in_str.trim();

    if (in_str.startsWith("public"))
    {
      return true;
    }
    /*
         for(int j=0;j<STRKEYFORWARD.length;j++)
     {
       int m=STRKEYFORWARD[j].length();
       if (in_str.length()>m &&  in_str.substring(0, m).equalsIgnoreCase(STRKEYFORWARD[j]))
       {
         m_str="\r\n";
       }

     }*/
    return false;
  }

  private long DelFileBlank2(String in_FileName)
  {
    long count = 0;
    try
    {
      System.out.println("打开文件:" + in_FileName);

      File f1 = new File(in_FileName);

      if (!f1.exists() && !f1.canRead())
      {
        System.out.println("文件没有找到");
      }
      //FileReader fr = new FileReader(in_FileName); //建立FileReader对象，并实例化为fr
      String m_tmpFileName = in_FileName + ".tmp";
      BufferedReader in = new BufferedReader(new FileReader(in_FileName)); //建立BufferedReader对象，并实例化为br
      //PrintWriter out = new PrintWriter(new FileOutputStream(m_tmpFileName));
      FileOutputStream b = new FileOutputStream(m_tmpFileName);
      OutputStreamWriter a = new OutputStreamWriter(b);
      BufferedWriter out = new BufferedWriter(a);

      StringBuffer sb = new StringBuffer();
      String Line;
      String m_tmpStr = "";
      while ( (Line = in.readLine()) != null)
      {
        if (!Line.trim().equalsIgnoreCase(""))
        {
          count++;
          /*
                     if (AddLinePre(Line))
                     {
            //sb.append("\n");
                     }
           */
          sb.append(Line).append("\r\n");
          //System.out.println(Line);
          /*
                     for(int j=0;j<STRKEYNEXT.length;j++)
                     {
            int m=STRKEYNEXT[j].length();
            if (Line.trim().length()>m &&  Line.trim().substring(0, m).equalsIgnoreCase(STRKEYNEXT[j]))
            {
              sb.append("\r\n");
            }
                     }*/
        }
        //Line = in.readLine();
      }
      in.close();
      f1.delete();
      out.write(sb.toString());
      out.close();
      sb.setLength(0);
      File f2 = new File(m_tmpFileName);
      f2.renameTo(f1);
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
      return -1;
    }
    return count;
  }

  private long DelFileBlank(String in_FileName)
  {
    long count = 0;
    if (in_FileName.endsWith("Bean.java"))
    {
      return 0;
    }
    if (in_FileName.endsWith("Engine.java"))
    {
      return 0;
    }
    if (in_FileName.endsWith("Map.java"))
    {
      return 0;
    }

    try
    {
      System.out.println("打开文件:" + in_FileName);

      File f1 = new File(in_FileName);

      if (!f1.exists() && !f1.canRead())
      {
        System.out.println("文件没有找到");
      }
      BufferedReader in = new BufferedReader(new FileReader(in_FileName)); //建立BufferedReader对象，并实例化为br
      String m_tmpFileName = in_FileName + ".tmp";
      PrintWriter pwOK = new PrintWriter(new FileOutputStream(m_tmpFileName));
      BufferedWriter out = new BufferedWriter(pwOK);

      StringBuffer sb = new StringBuffer();
      String Line;
      while ( (Line = in.readLine()) != null)
      {
        if (!Line.trim().equalsIgnoreCase(""))
        {
          count++;

          if (AddLinePre(Line))
          {
            sb.append("\r\n");
          }
          sb.append(Line).append("\r\n");
          //System.out.println(Line);
          //pwOK.println(Line);
          for (int j = 0; j < STRKEYNEXT.length; j++)
          {
            int m = STRKEYNEXT[j].length();
            if (Line.trim().length() > m &&
                Line.trim().substring(0, m).equalsIgnoreCase(STRKEYNEXT[j]))
            {
              sb.append("\r\n");
            }
          }
        }
        //out.write();
        if (count % 10 == 0)
        {
          out.write(sb.toString());
          sb.setLength(0);
        }
      }
      in.close();
      f1.delete();
      out.write(sb.toString());
      sb.setLength(0);
      out.close();
      pwOK.close();
      File f2 = new File(m_tmpFileName);
      f2.renameTo(f1);
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
      return -1;
    }
    return count;
  }

}
