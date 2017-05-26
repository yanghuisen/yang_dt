package com.hzmd.iwrite.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateParesUtil extends Date {
//	public static void main(String args[]) {
//	    System.out.println( new DateParesUtil("2015年1月") );
//	}

	private static final long serialVersionUID = 123211978171L;

	public DateParesUtil(long value) {

		super(value);
		// System.out.println(value);

	}

	public DateParesUtil() {

	}

	public DateParesUtil(String s) {

		this(parseJH(s));

	}

	public int getNtime() {
		Long t = getTime();
		t = t / 1000;
		return t.intValue();

	}
	
	public static Date getDate(String s){
	  try {
	    return new DateParesUtil(s);
    } catch (Exception e) {
      return null;
    }
	}
	static private long parseJH(String time) {

		return parseLong(time);
	}

	static public long parseLong(Date time) {
		if (time != null)
			return time.getTime();
		return 0;
	}

	static public long parseLong(String time) {
		time = time.trim();
		// 20100426 2010-04-27 April 22, 2010 April 29, 2010 2010-05-17 22:30
		// 08:26 May 18 2010 2010-05-19 11:17
		SimpleDateFormat format0 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");

		SimpleDateFormat format3 = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat format31 = new SimpleDateFormat("yyyy.MM.dd");
		SimpleDateFormat format6 = new SimpleDateFormat("MMM dd, yyyy",
				Locale.ENGLISH);
		SimpleDateFormat format5 = new SimpleDateFormat("MMMMM dd, yyyy",
				Locale.ENGLISH);
		SimpleDateFormat format4 = new SimpleDateFormat("HH:mm MMM dd yyyy",
				Locale.ENGLISH);
		SimpleDateFormat format13 = new SimpleDateFormat("HH:mm, MMM dd, yyyy",
				Locale.ENGLISH);

		// "09:47, August 19, 2010"
		SimpleDateFormat format7 = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
		 SimpleDateFormat format71 = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
	  SimpleDateFormat format72 = new SimpleDateFormat("yyyy年MM月dd日 HH时");
	  SimpleDateFormat format73 = new SimpleDateFormat("yyyy年MM月dd日");
	  SimpleDateFormat format74 = new SimpleDateFormat("yyyy年MM月dd");
	  SimpleDateFormat format75 = new SimpleDateFormat("yyyy年MM月");
	
		SimpleDateFormat format8 = new SimpleDateFormat("yyyy/MM/dd");
		SimpleDateFormat format9 = new SimpleDateFormat("yyyyMM/dd");
		SimpleDateFormat format10 = new SimpleDateFormat("yyyyMM-dd");
		SimpleDateFormat format11 = new SimpleDateFormat("yyyy-MMdd");
		SimpleDateFormat format12 = new SimpleDateFormat("yyyy/MMdd");
		SimpleDateFormat[] formats = new SimpleDateFormat[] {format31, format7,format71,format72,format73,format74,format75,format0, format1,
				format2, format3, format4, format5, format6,  format8,
				format9, format10, format11, format13, format12 };
		for (SimpleDateFormat format : formats)
			try {
				return format.parse(time).getTime();
			} catch (Exception e) {

			}
		return 0;
	}

	/**
	 * @param args
	 */
//	public static void main(String[] args) {
//		// 20100426 2010-04-27 April 22, 2010 April 29, 2010 2010-05-17 22:30
//		// 08:26 May 18 2010 2010-05-19 11:17
//		System.out.println(new Date(parseLong("09:47, August 19, 2010"))
//				.toLocaleString());
//
//	}

	public String toString() {
		SimpleDateFormat bartDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		return bartDateFormat.format(this);
	}

	public String toCString() {
		SimpleDateFormat bartDateFormat = new SimpleDateFormat(
				"yyyy年MM月dd日 HH时mm分ss秒");
		return bartDateFormat.format(this);
	}

	public String toCDateStr() {
		// 200710/19
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy/MM/dd/");
		return bartDateFormat.format(this);
	}

	public String toDateStr() {
		// 200710/19
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return bartDateFormat.format(this);
	}

	public final String toPathStr() {
		// 200710/19
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyyMM/dd");
		return bartDateFormat.format(this);
	}

	public final String toPathStr2() {

		// 200710/19
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyyMMdd");
		return bartDateFormat.format(this);
	}

	/**
	 * 获得指定日期的前一天
	 * 
	 * @param specifiedDay
	 * @return
	 * @throws Exception
	 */
	public static String getSpecifiedDayBefore(String specifiedDay) {
		// SimpleDateFormat simpleDateFormat = new
		// SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - 1);

		String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c
				.getTime());
		return dayBefore;
	}

	/**
	 * 获得指定日期的后一天
	 * 
	 * @param specifiedDay
	 * @return
	 */
	public static String getSpecifiedDayAfter(String specifiedDay) {
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + 1);

		String dayAfter = new SimpleDateFormat("yyyy-MM-dd")
				.format(c.getTime());
		return dayAfter;
	}
	
	/**
	 * 获得指定日期的前几天
	 * 
	 * @param specifiedDay
	 * @return
	 * @throws Exception
	 */
	public static String getDayBefore(String currentDate,int i) {
		// SimpleDateFormat simpleDateFormat = new
		// SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat("yy-MM-dd").parse(currentDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - i);

		String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c
				.getTime());
		return dayBefore;
	}

	/**
	 * 获得指定日期的后几天
	 * 
	 * @param specifiedDay
	 * @return
	 */
	public static String getDayAfter(String currentDate,int i) {
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat("yy-MM-dd").parse(currentDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + i);

		String dayAfter = new SimpleDateFormat("yyyy-MM-dd")
				.format(c.getTime());
		return dayAfter;
	}

}
