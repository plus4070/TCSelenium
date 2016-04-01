package selenium;

import java.util.ResourceBundle;

public class PropertiesUtil {
	
	public static ResourceBundle rb = ResourceBundle.getBundle("server");
	
	public static String getProperty(String key){
		return rb.getString(key); 
	}
	
	public static int getWaitTime(String key){
		return   Integer.valueOf(rb.getString(key)); 
	}
}
