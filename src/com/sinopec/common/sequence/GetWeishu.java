package com.sinopec.common.sequence;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.Properties;

import org.andromda.core.common.ResourceUtils;



public class GetWeishu{
	private static final String propertiesURL = "number.properties";

	public static String getBcodeMesage(String code) {
		InputStream stream = null;
		try {
			URL configurationURL = ResourceUtils.getResource("number.properties");
			stream = configurationURL.openStream();
			Properties prop = new Properties();
			prop.load(stream);
			String BcodeMesage = new String(prop.getProperty(code).getBytes("ISO-8859-1"), "gbk");
			String str1 = BcodeMesage;
			return str1;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (stream != null) 
			{
				try{
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				stream = null;
			}
		}
		return code + "|指定参数不存在！";
	}
}
