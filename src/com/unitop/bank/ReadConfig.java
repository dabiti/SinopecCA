package com.unitop.bank;

//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.Properties;
import java.util.ResourceBundle;

public class ReadConfig {
	private String SystemPropertyFile = "logConfig";

	private ResourceBundle bundle = null;

	//private Properties prop=null;
	
	//private String file = "";
	
	public ReadConfig() {

	}
//	
//	private void init(){
//		InputStream in;
//		prop = new Properties();
//		file = "/" +SystemPropertyFile + ".properties";
//		try {
//			in = ReadConfig.class.getResourceAsStream(file);
//			prop.load(in);
//			in.close();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//	public String getPropbyStream(String key) {
//		String val = "";
//		if(null == prop){
//			init();
//		}
//		if(!prop.containsKey(key)){
//			return val;
//		}
//		val = prop.getProperty(key).trim();
//		return val;
//	}
	
	public String getProp(String key) {
		bundle = ResourceBundle.getBundle(SystemPropertyFile);
		String val = bundle.getString(key);
		return val;
	}

}
