package es.albandroid.feria11.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatoFechas {
	
	public static String getDiaHora(Date d){
		if(d==null)
			 return "";
		 SimpleDateFormat format =
	            new SimpleDateFormat(Constants.FORMAT_DATE_HOUR_DAY);		 
		 return format.format(d);
	}
	public static String getDiaNum(Date d){
		if(d==null)
			 return "";
		 SimpleDateFormat format =
	            new SimpleDateFormat(Constants.FORMAT_DATE_DAY_NUM);		 
		 return format.format(d);
	}
	
	public static String getHora(Date d){
		if(d==null)
			 return "";
		 SimpleDateFormat format =
	            new SimpleDateFormat(Constants.FORMAT_DATE_HOUR);
		 return format.format(d);
	}

	
}
