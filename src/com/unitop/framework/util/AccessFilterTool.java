package com.unitop.framework.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;
import org.andromda.core.common.ResourceUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/*
 * 过滤器 过滤器访问请求设置初始化类
 */
public class AccessFilterTool {

	private static Log log = LogFactory.getLog(AccessFilterTool.class);

	private static final String DEFAULT_URI = "accessFilter.properties";
	
	private static Map AccessFilterMap = new HashMap();
	
	static {
		final URL configurationURL = ResourceUtils.getResource(DEFAULT_URI);
		try {
			InputStream in = configurationURL.openStream();
			Properties properties = new Properties();
			properties.load(in);
			Iterator itr = properties.entrySet().iterator();
			while (itr.hasNext())
			{
				Entry e = (Entry) itr.next();
				AccessFilterMap.put(e.getValue(), e.getKey());
			}
		} catch (IOException e) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			log.error(sw.toString());
		}
	}
	
	static public Map getAccessFilterMap() {
		return AccessFilterMap;
	}
}
