package org.jpf.langs;

import java.io.*;
import java.util.*;

public class MutiLangMap
{
	// 语言存放MAP
	HashMap MutiMap = new HashMap();
	// 文件列表
	Vector v_FileList = new Vector();
	// 前台语言文件名
	private static final String ForegroundLangFile = "WBASSLANG";

	// 后台语言文件名
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
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	private void ReadLangFile(String in_FileName, String in_LanguageType)
			throws Exception
	{
		BufferedReader in = new BufferedReader(new FileReader(in_FileName)); // 建立BufferedReader对象，并实例化为br
		String Line;
		int m = -1;
		while ((Line = in.readLine()) != null)
		{
			// 判断是不是注释行
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
		// System.out.println(MutiMap.size());
	}

	private void PutLang2(String strKey, String strLanguage, String strKeyValue)
	{
		MutiLang m_ml = (MutiLang) MutiMap.get(strKey);
		if (m_ml == null)
		{
			PutLang(strKey, strLanguage, strKeyValue);
		} else
		{
			m_ml.m_map.put(strLanguage, strKeyValue);
		}

	}

	private String getMutiStr(String strKey, String strLanguage)
	{
		MutiLang m_ml = (MutiLang) MutiMap.get(strKey);
		if (m_ml != null)
		{
			String m_str = (String) m_ml.m_map.get(strLanguage);
			if (m_str != null)
			{
				return m_str;
			} else
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
