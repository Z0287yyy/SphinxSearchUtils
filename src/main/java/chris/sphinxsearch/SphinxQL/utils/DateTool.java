/**
 * License: Apache 2.0
 * @author Chris
 * z0287yyy@foxmail.com
 */

package chris.sphinxsearch.SphinxQL.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;



public class DateTool {
	
	private static final String dateFormat="yyyy-MM-dd HH:mm:ss";
	private static final String dateFormat_ms="yyyy-MM-dd HH:mm:ss:SSS";
	
	public static String getNowTime() {
		// TODO Auto-generated method stub
		return new SimpleDateFormat(dateFormat).format(new Date());
	}
	
	public static String getTimeString(Date date) {
		return new SimpleDateFormat(dateFormat).format(date);
	}
	
	public static int getTwoTimeSub(String beginTime, String endTime) {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf=new SimpleDateFormat(dateFormat);
		int tts=0;
		try {
			Date bt=sdf.parse(beginTime);
			Date et=sdf.parse(endTime);
			tts=(int) ((et.getTime()-bt.getTime())/1000);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Math.abs(tts);
	}

	public static double getTwoTimeSub(Date beginTime, Date endTime) {
		// TODO Auto-generated method stub
		double tts=0;
		try {
			tts=(endTime.getTime()-beginTime.getTime())*1.0/1000;
//			System.out.println("开始时间:"+beginTime.getTime()+"。减去结束时间："+endTime.getTime()+"结果为："+(beginTime.getTime()-endTime.getTime())+"。运算结果为："+tts);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.err.println("有个为空!");
		}
		return Math.abs(tts);
	}
	//字符转时间类型
	public static Timestamp getTimestamp(String date) {
		SimpleDateFormat sdf=new SimpleDateFormat(dateFormat);
		Timestamp time=null;
		try {
			time = new Timestamp(sdf.parse(date).getTime());
		} catch (ParseException e) {
			return new Timestamp(new Date().getTime());
		}
		return time;
	}
//	字符转时间类型
	public static Date getDateTime(String date) {
		SimpleDateFormat sdf=new SimpleDateFormat(dateFormat);
		Date dTime=null;
		try {
			dTime = sdf.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			System.err.println("字符串转为时间出错！");
		}
		return dTime;
	}
	/**
	 * 得到当前系统日期， 格式为"yyyy-MM-dd"
	 */
	public static String getNowDate() {
		String format = "yyyy-MM-dd";
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		Date currentTime = new Date();
		return formatter.format(currentTime);
	}

	public static String getNowMonth() {
		String format = "yyyyMM";
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		Date currentTime = new Date();
		return formatter.format(currentTime);
	}

	public static String getNextMonthStartDate(String month) {
		String str = "";
		if (month.substring(4).equals("12")) {
			int year = Integer.parseInt(month.substring(0, 4)) + 1;
			str = year + "-01";
		} else {
			str = (Integer.parseInt(month) + 1) + "";
			str = str.substring(0, 4) + "-" + str.substring(4);
		}
		return str + "-01";
	}

	public static String getNextMonth(String month) {
		String str = "";
		if (month.substring(4).equals("12")) {
			int year = Integer.parseInt(month.substring(0, 4)) + 1;
		} else {
			str = (Integer.parseInt(month) + 1) + "";
		}
		return str;
	}

	public static boolean ifCurDateOver(String month) {
		String cur = getNowDate();
		long i = getDateDiff(cur, getNextMonthStartDate(month));
		if (i >= 0) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * 取得两日期之间天数差
	 */
	public static long getDateDiff(String BegDate, String EndDate) {
		Date begDate = StrToDate(BegDate);
		Date endDate = StrToDate(EndDate);
		return getDateDiff(begDate, endDate);
	}

	/**
	 * 把一个日期字符串转化为日期类型 *
	 * 
	 * @param date
	 *            日期字符串 日期字符格串式必须为"yyyy-MM-dd"
	 */
	public static Date StrToDate(String date) {
		if (!validDate(date)) {
			return null;
		}
		String [] param=date.split("-");
		int year = Integer.valueOf(param[0]);
		int month = Integer.valueOf(param[1]);
		int dd = Integer.valueOf(param[2]);
		Calendar calendar = new GregorianCalendar(year, month - 1, dd);
		Date d = calendar.getTime();
		return d;
	}
	

	//	 输入的时间校验字符格串式必须为"yyyy-MM-dd"
	public static boolean validDate(String date) {
		if (date == null || date.length() != 10) {
			return false;
		} else {
			String [] param=date.split("-");
			int yy = Integer.valueOf(param[0]);
			int mm = Integer.valueOf(param[1]);
			int dd = Integer.valueOf(param[2]);
			switch (mm) {
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				if (dd > 31 || dd < 1) {
					return false;
				} else {
					return true;
				}
			case 2:
				if ((yy % 4 == 0 && yy % 100 != 0) || yy % 400 == 0) {
					if (dd > 29 || dd < 1) {
						return false;
					} else {
						return true;
					}
				} else {
					if (dd > 28 || dd < 1) {
						return false;
					} else {
						return true;
					}
				}
			default:
				if (dd > 30 || dd < 1) {
					return false;
				} else {
					return true;
				}
			}
		}
	}

	/*
	 * 取得两日期之间天数差
	 */
	public static long getDateDiff(Date BegDate, Date EndDate) {
		return (BegDate.getTime() / 1000 - EndDate.getTime() / 1000)
				/ (24 * 60 * 60);
	}

	public static String getNowDateTime() {
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		Date currentTime = new java.sql.Date(new java.util.Date().getTime());
		return formatter.format(currentTime);
	}

	public static double strTodouble(String str) {
		double i = 0;
		if (str != null && str.length() != 0) {
			try {
				i = Double.parseDouble(str.trim());
			} catch (NumberFormatException nfe) {
				nfe.printStackTrace();
			}
		}
		return i;
	}
	
	public static void main(String[] args) {
		System.out.println(getTwoTimeSub("2005-10-10 20:00:00","2005-10-10 20:00:05"));
	}
}
