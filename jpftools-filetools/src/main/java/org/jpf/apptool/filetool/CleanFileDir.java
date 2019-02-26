package org.jpf.apptool.filetool;

import java.io.*;
import java.util.*;

/**
 * 
 * <p>
 * Title: 清除空文件目录
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author 吴平福
 * @version 1.0
 */
public class CleanFileDir
{
	// 读取文件目录下的文件列表
	private Vector g_Vector = new Vector();
	private String g_WorkDir = "D:/Jenkins/workspace/easyframe";
	private String g_DelDirStr = ".svn";

	private long g_DelDirCount = 0;
	private long g_CleanDirCount = 0;
	private long g_TotalDirCount = 0;

	public CleanFileDir()
	{
		DelSpecifyFileDir(g_WorkDir);
		DelBlankDir(g_WorkDir);
		for (int i = 0; i < g_Vector.size(); i++)
		{
			System.out.println((String) g_Vector.get(i));
		}
		System.out.println("g_DelDirCount:" + g_DelDirCount);
		System.out.println("g_CleanDirCount:" + g_CleanDirCount);
		System.out.println("g_TotalDirCount:" + g_TotalDirCount);

	}

	public static void main(String[] args)
	{
		CleanFileDir a = new CleanFileDir();
	}

	/**
	 * @todo 比较是否是需要删除的目录
	 * @param in_FilePath
	 *            String
	 * @return boolean
	 */
	private boolean JustDelDir(String in_FilePath)
	{
		int i = in_FilePath.lastIndexOf("\\");
		if (i > 0)
		{
			String m_str = in_FilePath.substring(i + 1);
			if (m_str.equalsIgnoreCase(g_DelDirStr))
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @param path
	 *            String
	 */
	public void DelDirWithFiles(String path)
	{
		try
		{
			File dir = new File(path);
			if (dir.exists())
			{
				File[] tmp = dir.listFiles();
				for (int i = 0; i < tmp.length; i++)
				{
					if (tmp[i].isDirectory())
					{
						DelDirWithFiles(path + "\\" + tmp[i].getName());
					} else
					{
						System.out.println("del file:" + tmp[i]);
						tmp[i].delete();
						
					}
				}
				System.out.println("del dir:" + dir.getAbsolutePath());
				dir.delete();
			}
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	private void DelSpecifyFileDir(String in_FilePath)
	{
		//System.out.println("current dir:" + in_FilePath);
		File f1 = new File(in_FilePath);
		if (!f1.isDirectory())
		{
			return;
		}

		if (JustDelDir(in_FilePath))
		{
			System.out.println("delete dir:" + in_FilePath);
			DelDirWithFiles(in_FilePath);
			g_DelDirCount++;
			return;
		}
		File[] f2 = f1.listFiles();

		for (int i = 0; i < f2.length; i++)
		{
			if (f2[i].isDirectory())
			{
				DelSpecifyFileDir(f2[i].toString());
			}
		}

	}

	/**
	 * 删除空目录
	 * 
	 * @param in_FilePath
	 *            String
	 */
	private void DelBlankDir(String in_FilePath)
	{
		//System.out.println("current dir:" + in_FilePath);
		File f1 = new File(in_FilePath);
		if (!f1.isDirectory())
		{
			return;
		}

		File[] f2 = f1.listFiles();
		if (0 == f2.length)
		{
			System.out.println("delete dir:" + in_FilePath);
			g_CleanDirCount++;
			f1.delete();
			return;
		}
		else
		{
			for (int i = 0; i < f2.length; i++)
			{
				if (f2[i].isDirectory())
				{
					// g_Vector.add(f2[i].toString());
					DelBlankDir(f2[i].toString());
				}
			}

		}

		File[] f3 = f1.listFiles();
		if (0 == f3.length)
		{
			System.out.println(in_FilePath);
			g_CleanDirCount++;
			f1.delete();
		}

		g_TotalDirCount = g_TotalDirCount + f2.length;

	}

}
