package com.obss.radar.searcher.util;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SystemDataUtils {
    private static DecimalFormat df = new DecimalFormat();
    
    public static Integer parseInteger(String strInt) throws ParseException{
        df.applyPattern("0,000.0#");
    	return df.parse(strInt).intValue();
    }
    
    public static String changeDateFormat(String value,String oriFormat,String newFormat){
    	String result = "";
    	try {
	        SimpleDateFormat sdf = new SimpleDateFormat(oriFormat);
	        Date date = sdf.parse(value);
	        sdf.applyPattern(newFormat);
	        result = sdf.format(date);
        } catch (ParseException e) {
	        e.printStackTrace();
        }
    	return result;
    }
    
    public static String getCurrentYearStr(){
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
    	return sdf.format(new Date());
    }
    
    public static String getDate(String format){
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		String dateString = formatter.format(currentTime);
		return dateString;
	}
    
    public static String getdate(String time, int count,String format) throws ParseException{
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		Date date = formatter.parse(time);
		long date_time = date.getTime();
		date_time -= (long)1000 * 60 * 60 * 24*count;
		date.setTime(date_time);
		String dateString = formatter.format(date);
		return dateString;
	}
}
