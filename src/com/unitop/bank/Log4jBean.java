package com.unitop.bank;

import org.apache.log4j.*;

public class Log4jBean {

	public void debug(String app, String msg) {
		debug(app, msg, null);
	}

	public void debug(String app, String msg, Throwable e) {
		Logger cata = Logger.getLogger(app);
		if (e == null) {
			cata.debug(msg);
		} else {
			cata.debug(msg, e);
		}
	}

	public void info(String app, String msg) {
		info(app, msg, null);
	}

	public void info(String app, String msg, Throwable e) {
		Logger cata = Logger.getLogger(app);
		if (e == null) {
			cata.info(msg);
		} else {
			cata.warn(msg, e);
		}
	}

	public void warn(String app, String msg) {
		warn(app, msg, null);
	}

	public void warn(String app, String msg, Throwable e) {
		Logger cata = Logger.getLogger(app);
		if (e == null) {
			cata.warn(msg);
		} else {
			cata.error(msg, e);
		}
	}

	public void error(String app, String msg) {
		warn(app, msg, null);
	}

	public void error(String app, String msg, Throwable e) {
		Logger cata = Logger.getLogger(app);
		if (e == null) {
			cata.error(msg);
		} else {
			cata.error(msg, e);
		}
	}
	
	/**
	 * @param app
	 * @param output
	 * @param pattern
	 * @param outLevel
	 *            -1:off, 0:debug, 1:info, 2:warn, 3:error
	 */
	public void initLog(String app, String output, String pattern, int outLevel) {
		System.out.println("log mode changed!");
		// String pattern = Constants.getSystemParameter("log.ptn");
		if (pattern.length() == 0) {
			pattern = "[%d{dd,HH:mm:ss,SSS}][%-5p][%c{1}] %m%n";
		}
		// String output = Constants.getSystemParameter("log.file");
		// String output = "";
		if (output.length() == 0) {
			output = "undefine_logname.log";
		}
		// String size = Constants.getSystemParameter("log.size");
		String size = "";
		if (size.length() == 0) {
			size = "5MB";
		}
		// String index = Constants.getSystemParameter("log.index");
		String index = "";
		if (index.length() == 0) {
			index = "1000";
		}
		Logger curLogger = Logger.getLogger(app);
		curLogger.removeAllAppenders();
		curLogger.getLoggerRepository().shutdown();
		curLogger = Logger.getLogger(app);
		PatternLayout layout = new PatternLayout(pattern);
		Appender appender = null;
		try {
			RollingFileAppender fileAppender = new RollingFileAppender(layout,
					output, true);
			fileAppender.setMaxBackupIndex(Integer.parseInt(index));
			fileAppender.setMaxFileSize(size);
			appender = fileAppender;
			// if (Constants.getSystemParameter("log.std").equals("1")) {
			// curLogger.addAppender(new ConsoleAppender(layout));
			// }
		} catch (Exception ex) {
			ex.printStackTrace();
			ConsoleAppender consoleAppender = new ConsoleAppender(layout);
			appender = consoleAppender;
		}
		curLogger.addAppender(appender);
		// int outLevel =
		// Integer.parseInt(Constants.getSystemParameter("log.lvl").equals("")
		// ? "0" : Constants.getSystemParameter("log.lvl"));
		// int outLevel = Integer.parseInt("".equals("") ? "0" :
		// LoadConfigParam.I().getLogLevel());
		String lvlStr = "";
		switch (outLevel) {
		case -1:
			lvlStr = "OFF";
			curLogger.setLevel(Level.OFF);
			break;
		case 0: // '\0'
			lvlStr = "DEBUG";
			curLogger.setLevel(Level.DEBUG);
			break;
		case 1: // '\001'
			lvlStr = "INFO";
			curLogger.setLevel(Level.INFO);
			break;
		case 2: // '\002'
			lvlStr = "WARN";
			curLogger.setLevel(Level.WARN);
			break;
		case 4: // '\004'
			lvlStr = "ERROR";
			curLogger.setLevel(Level.ERROR);
			break;
		}
		System.out.println("log file : " + output);
		System.out.println("pattern : " + pattern);
		System.out.println("size : " + size);
		System.out.println("index : " + index);
		System.out.println("level : " + lvlStr);
	}

	public void initLog(String app, String output, int level) {
		initLog(app, output, "", level);
	}
	
	public void initLog(String app, String output) {
		initLog(app, output, "", 0);
	}
	
	// public void initLog(String app,String output) {
	// System.out.println("log mode changed!");
	// // String pattern = Constants.getSystemParameter("log.ptn");
	// String pattern = "";
	// if (pattern.length() == 0) {
	// pattern = "[%d{dd,HH:mm:ss,SSS}][%-5p][%c{1}] %m%n";
	// }
	//
	// //String output = Constants.getSystemParameter("log.file");
	// //String output = "";
	// if (output.length() == 0) {
	// output = "undefine_logname.log";
	// }
	//				
	// //String size = Constants.getSystemParameter("log.size");
	// String size = "";
	// / if (size.length() == 0) {
	// size = "5MB";
	// }
	//				
	// //String index = Constants.getSystemParameter("log.index");
	// String index = "";
	// if (index.length() == 0) {
	// index = "1000";
	// }
	// Logger curLogger = Logger.getLogger(app);
	// / curLogger.removeAllAppenders();
	// curLogger.getLoggerRepository().shutdown();
	// curLogger = Logger.getLogger(app);
	// PatternLayout layout = new PatternLayout(pattern);
	// org.apache.log4j.Appender appender = null;
	// try {
	// RollingFileAppender fileAppender = new
	// RollingFileAppender(layout, output, true);
	// fileAppender.setMaxBackupIndex(Integer.parseInt(index));
	// fileAppender.setMaxFileSize(size);
	// appender = fileAppender;
	// //if (Constants.getSystemParameter("log.std").equals("1")) {
	// // curLogger.addAppender(new ConsoleAppender(layout));
	// //}
	// }
	// catch (Exception ex) {
	// ex.printStackTrace();
	// ConsoleAppender consoleAppender = new ConsoleAppender(layout);
	// appender = consoleAppender;
	// }
	// curLogger.addAppender(appender);
	// //int outLevel =
	// Integer.parseInt(Constants.getSystemParameter("log.lvl").equals("") ? "0"
	// : Constants.getSystemParameter("log.lvl"));
	// int outLevel = Integer.parseInt("".equals("") ? "0" :
	// LoadConfigParam.I().getLogLevel());
	// String lvlStr = "";
	// switch (outLevel) {
	// case 0: // '\0'
	// lvlStr = "DEBUG";
	// curLogger.setLevel(Level.DEBUG);
	// break;
	//
	// case 1: // '\001'
	// lvlStr = "INFO";
	// curLogger.setLevel(Level.INFO);
	// break;
	//
	// case 2: // '\002'
	// lvlStr = "WARN";
	// curLogger.setLevel(Level.WARN);
	// break;
	//
	// case 4: // '\004'
	// lvlStr = "ERROR";
	// curLogger.setLevel(Level.ERROR);
	// break;
	// }
	// System.out.println("log file : " + output);
	// System.out.println("pattern : " + pattern);
	// System.out.println("size : " + size);
	// System.out.println("index : " + index);
	// System.out.println("level : " + lvlStr);
	// }

}
