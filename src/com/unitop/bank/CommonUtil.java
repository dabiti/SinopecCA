package com.unitop.bank;

import java.io.File;
import java.io.IOException;

import com.unitop.bean.LoadConfigParam;

public class CommonUtil {
	public static final String logChannel = "main";

	public static final String logLevel_debug = "debug";

	public static final String logLevel_info = "info";

	public static final String logLevel_warn = "warn";

	public static final String logSort_message = "message";

	public static final String logSort_exception = "exception";

	public static boolean useJndiLogging = true;

	protected static boolean isDebug = true;

	protected static boolean isConsolePrint = false;

	protected static String logPath = "";

	protected static String logPattern = "";

	protected static int logLevel = 1;

	protected Log4jBean log = null;

	protected static CommonUtil util = new CommonUtil();

	String tempPath = this.getClass().getResource("/").getPath();

	public static void setDebug(boolean val) {
		isDebug = val;
	}
	
	/**
	 * @author luoxiaoyi
	 * @date 2012-08-08
	 * 实现日志级别动态修改
	 */
	protected CommonUtil(String flag) {
		try {
			LoadConfigParam param = LoadConfigParam.I(flag);
			logPath = param.getOutPut();
			isConsolePrint = param.isConsolePrint();
			logPattern = param.getPattern();
			logLevel = Integer.valueOf(param.getLogLevel());
			Log4jBean log4j = new Log4jBean();
			log4j.initLog(logChannel, logPath, logPattern, logLevel);
			log = log4j;
		} catch (Exception ex) {
			CommonUtil.consoleOut("日志参数配置错误，请检查logconfig.properties 文件！", null);
			CommonUtil.consoleOut("CommnUtil construct exception "
					+ ex.getMessage(), null);
			logLevel = 1;
			Log4jBean log4j = new Log4jBean();
			log4j.initLog(logChannel, logPath, logPattern, logLevel);
			log = log4j;
		}
	}

	protected CommonUtil() {
		try {
			LoadConfigParam param = LoadConfigParam.I("");
			logPath = param.getOutPut();
			isConsolePrint = param.isConsolePrint();
			logPattern = param.getPattern();
			logLevel = Integer.valueOf(param.getLogLevel());
			Log4jBean log4j = new Log4jBean();
			log4j.initLog(logChannel, logPath, logPattern, logLevel);
			log = log4j;
		} catch (Exception ex) {
			CommonUtil.consoleOut("日志参数配置错误，请检查logconfig.properties 文件！", null);
			CommonUtil.consoleOut("CommnUtil construct exception "
					+ ex.getMessage(), null);
			logLevel = 1;
			Log4jBean log4j = new Log4jBean();
			log4j.initLog(logChannel, logPath, logPattern, logLevel);
			log = log4j;
		}
	}

	public static void debug(int msg) {
		debug(String.valueOf(msg));
	}

	public static void debug(long msg) {
		debug(String.valueOf(msg));
	}

	public static void debug(boolean msg) {
		debug(String.valueOf(msg));
	}

	public static void debug(Object msg) {
		debug(msg != null ? msg.toString() : "(null)");
	}

	public static void debug(String msg, Throwable e) {
		e.printStackTrace();
		if (!isDebug)
			return;
		if (isConsolePrint) {
			System.out.println(msg);
			e.printStackTrace();
		}
		Log4jBean log = util.log;
		try {
			if (log != null) {
				log.debug(logChannel, msg, e);
			} else {
				consoleOut(msg, e);
			}
		} catch (Exception ex) {
			consoleOut(msg, e);
		}
	}

	public static void info(String msg, Throwable e) {
		if (isConsolePrint) {
			System.out.println(msg);
			e.printStackTrace();
		}
		Log4jBean log = util.log;
		try {
			if (log != null) {
				log.info(logChannel, msg, e);
			} else {
				consoleOut(msg, e);
			}
		} catch (Exception ex) {
			consoleOut(msg, e);
		}
	}

	public static void warn(String msg, Throwable e) {
		if (isConsolePrint) {
			System.out.println(msg);
			e.printStackTrace();
		}
		Log4jBean log = util.log;
		try {
			if (log != null) {
				log.warn(logChannel, msg, e);
			} else {
				consoleOut(msg, e);
			}
		} catch (Exception ex) {
			consoleOut(msg, e);
		}
	}

	public static void error(String msg, Throwable e) {
		if (isConsolePrint) {
			System.out.println(msg);
			e.printStackTrace();
		}
		Log4jBean log = util.log;
		try {
			if (log != null) {
				log.error(logChannel, msg, e);
			} else {
				consoleOut(msg, e);
			}
		} catch (Exception ex) {
			consoleOut(msg, e);
		}
	}

	public static void debug(String msg) {
		if (!isDebug)
			return;
		if (isConsolePrint) {
			System.out.println(msg);
		}
		Log4jBean log = util.log;
		try {
			if (log != null) {
				log.debug(logChannel, msg);
			} else {
				consoleOut(msg, null);
			}
		} catch (Exception ex) {
			consoleOut(msg, null);
		}
	}

	public static void info(String msg) {
		if (isConsolePrint) {
			System.out.println(msg);
		}
		Log4jBean log = util.log;
		try {
			if (log != null) {
				log.info(logChannel, msg);
			} else {
				consoleOut(msg, null);
			}
		} catch (Exception ex) {
			consoleOut(msg, null);
		}
	}

	public static void warn(String msg) {
		if (isConsolePrint) {
			System.out.println(msg);
		}
		Log4jBean log = util.log;
		try {
			if (log != null) {
				log.warn(logChannel, msg);
			} else {
				consoleOut(msg, null);
			}
		} catch (Exception ex) {
			consoleOut(msg, null);
		}
	}

	public static void error(String msg) {
		if (isConsolePrint) {
			System.out.println(msg);
		}
		Log4jBean log = util.log;
		try {
			if (log != null) {
				log.error(logChannel, msg);
			} else {
				consoleOut(msg, null);
			}
		} catch (Exception ex) {
			consoleOut(msg, null);
		}
	}

	protected static void consoleOut(String msg, Throwable e) {
		System.out.println(msg);
		if (e != null) {
			e.printStackTrace();
		}
	}

	public static Log4jBean logging() {
		if (!useJndiLogging) {
			return null;
		}
		try {
			// Context context = new InitialContext();
			// CommonLoggingHome home = (CommonLoggingHome)
			// PortableRemoteObject.narrow(context.lookup("CommonLogging"),
			// CommonLoggingHome.class);
			// CommonLogging code = home.create();
			Log4jBean code = new Log4jBean();
			return code;
		} catch (Exception ex) {
			useJndiLogging = false;
			CommonUtil.consoleOut("Logging Service not found, "
					+ ex.getMessage(), null);
		}
		return null;
	}

	public static void setOff() {
		Log4jBean log4j = new Log4jBean();
		log4j.initLog(CommonUtil.logChannel, logPath, -1);
		util.log = log4j;
	}

	public static void setOn() {
		Log4jBean log4j = new Log4jBean();
		log4j.initLog(CommonUtil.logChannel, logPath, 0);
		util.log = log4j;
	}

	public static void setConsolePrint(boolean val) {
		isConsolePrint = val;
	}

	/**
	 * 创建日志文件夹
	 * 
	 * @param Dir
	 * @return
	 */
	public boolean makeDirs(String Dir) {
		boolean ReturnValue = true;
		File f = new File(Dir);
		if (!f.exists())
			ReturnValue = f.mkdirs();
		return ReturnValue;
	}

	/**
	 * 创建文件
	 * 
	 * @param path
	 * @return
	 */
	public File makeFile(String path) {
		File file = new File(path);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				if (file.canWrite()) {
					file.delete();
					if (!file.exists()) {
						file.createNewFile();
					}
				} else {
					System.out.println("文件没有写权限");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return file;
	}
}
