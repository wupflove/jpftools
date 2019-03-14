package org.jpf.jpftool.filetool;

import java.io.*;
import java.util.*;

public class MutiLangMap
{
  //���Դ��MAP
  HashMap MutiMap = new HashMap();
  //�ļ��б�
  Vector v_FileList = new Vector();
  //ǰ̨�����ļ���
  private static final String ForegroundLangFile = "WBASSLANG";

  //��̨�����ļ���
  private static final String BackGroundLangFile = "BackLangFileName";
  private static final String PropPath = "";
  private static final String KEYSIGN = "=";
  public MutiLangMap()
  {

    try
    {
      ReadLangFile("D:\\studio\\shprint\\classes\\WBASSLANG_zh_CN.properties",
                   "zh_CN");
      System.out.println(getMutiStr("busiparam_bpsirhightlimit_3", "zh_CN"));
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
  }

  private void ReadLangFile(String in_FileName, String in_LanguageType)
      throws
      Exception
  {
    BufferedReader in = new BufferedReader(new FileReader(in_FileName)); //����BufferedReader���󣬲�ʵ����Ϊbr
    String Line;
    int m = -1;
    while ( (Line = in.readLine()) != null)
    {
      //�ж��ǲ���ע����
      m = Line.indexOf(KEYSIGN);
      if (m >= 0)
      {
        PutLang2(Line.trim().substring(0, m), in_LanguageType,
                 Line.trim().substring(m + KEYSIGN.length()));
      }
    }

    in.close();

  }

  private void PutLang(String strKey, String strLanguage, String strKeyValue)
  {
    MutiLang m_ml = new MutiLang();
    m_ml.m_map.put(strLanguage, strKeyValue);
    MutiMap.put(strKey, m_ml);
    //System.out.println(MutiMap.size());
  }

  private void PutLang2(String strKey, String strLanguage, String strKeyValue)
  {
    MutiLang m_ml = (MutiLang)MutiMap.get(strKey);
    if (m_ml == null)
    {
      PutLang(strKey, strLanguage, strKeyValue);
    }
    else
    {
      m_ml.m_map.put(strLanguage, strKeyValue);
    }

  }

  private String getMutiStr(String strKey, String strLanguage)
  {
    MutiLang m_ml = (MutiLang)MutiMap.get(strKey);
    if (m_ml != null)
    {
      String m_str = (String)m_ml.m_map.get(strLanguage);
      if (m_str != null)
      {
        return m_str;
      }
      else
      {
        return "can not find Language:" + strLanguage + ";key:" + strKey;
      }
    }
    return "can not find key:" + strKey;
  }

  public static void main(String[] args)
      throws Exception
  {
    MutiLangMap mm = new MutiLangMap();
  }

  class MutiLang
  {
    HashMap m_map;
  }
}
