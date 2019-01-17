package me.huqiao.httpserver;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

	public static String format(Date date,String pattern){
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}
	public static String format(Date date,String pattern,Locale l){
		SimpleDateFormat sdf = new SimpleDateFormat(pattern,l);
		return sdf.format(date);
	}
	
	public static String format(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("EEE,MMM dd yyyy HH:mm:ss z",Locale.ENGLISH);
		return sdf.format(date);
	}
}
