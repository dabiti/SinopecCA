package com.unitop.bean;

import org.apache.log4j.lf5.LogLevel;

import com.unitop.bank.CommonUtil;
import com.unitop.bank.ReadConfig;

public class LoadConfigParam {

	private static LoadConfigParam instance = null;

	private ReadConfig logcfg = null;

	private String logLevel = "";

	private boolean ifDebug = false;

	private String outPut = "";

	private String pattern = "";

	private boolean isConsolePrint = false;

	public static LoadConfigParam I(String flag) {
		if (null == flag || "".equals(flag) ) {
			if (instance == null) {
			instance = new LoadConfigParam();
			instance.init();
		}
		}else{
			instance.setLogLevel(flag) ;
		}
		return instance;
	}

	public void init() {
		try {
			logcfg = new ReadConfig();
			if (logcfg != null) {
				logLevel = logcfg.getProp("log.lvl");
				String ifdebug = logcfg.getProp("ifdebug");
				outPut = logcfg.getProp("log.path");
				pattern = logcfg.getProp("log.pattern");
				String consloePrint = logcfg.getProp("isConsolePrint");
				if ("true".equals(consloePrint)) {
					isConsolePrint = true;
				}
				if ("true".equals(ifdebug)) {
					ifDebug = true;
				}
			} else {
				CommonUtil.debug("LoadConfigParam init failure");
			}
		} catch (Exception e) {
			CommonUtil.debug("LoadConfigParam init exception", e);
		}
	}

	private void setLogLevel(String logLevel) {
		this.logLevel = logLevel;
	}

	public boolean isIfDebug() {
		return ifDebug;
	}

	public String getOutPut() {
		return outPut;
	}

	public String getPattern() {
		return pattern;
	}

	public boolean isConsolePrint() {
		return isConsolePrint;
	}

	public String getLogLevel() {
		return logLevel;
	}

}
