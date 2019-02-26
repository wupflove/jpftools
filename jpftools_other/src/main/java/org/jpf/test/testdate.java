package org.jpf.test;

import java.sql.Timestamp;
import java.util.Date;
import java.text.SimpleDateFormat;

public class testdate
{
	public testdate()
	{
		Timestamp a = new Timestamp(new Date().getTime());
		System.out.println(formatDateTime(a));
	}

	public static void main(String[] args) throws Exception
	{
		testdate t = new testdate();
	}

	private static String DATE_TIME_FORMAT = "MM/dd/yyyy HH:mm:ss";
	static SimpleDateFormat dateTimeFormat = new SimpleDateFormat(DATE_TIME_FORMAT);

	public static String formatDateTime(Date obj)
	{
		if (obj != null)
			return dateTimeFormat.format(obj);
		else
			return "";
	}
	private Date date1;
	public void setmy(Date date1)
	{
		this.date1=date1;
	}
	public void setmy2(Date date1)
	{
		Date my=new Date();
		my=date1;
		this.date1=my;
	}
}
