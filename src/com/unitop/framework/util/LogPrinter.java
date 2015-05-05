package com.unitop.framework.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LogPrinter {
	
	protected static Log log = LogFactory.getLog(LogPrinter.class);
	
	/**
	 * @author lhz
	 * @param e
	 * 工具类，打印异常到日志
	 */
	public static void error(Exception e){
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		try {
			sw.close();
			pw.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		log.error(sw);
	}
	
	public static void info(String logStr){
		log.info(logStr);
	}
	
	
	
	

}
