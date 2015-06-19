package com.psk.pms.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PMSUtil {
	
	public static String getStringDate(Date dateToBeFormatted, SimpleDateFormat formatter){
		String date = null;
		if(dateToBeFormatted != null){
			date = formatter.format(dateToBeFormatted);
			return date;
		}		
		return date;
	}

}
