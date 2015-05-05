package com.unitop.framework.util;

/**
 * FileServer 客户端 支持类
 * 
 * @author liudongdong
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.Socket;

public class FileServerClient {

	private String FileServerIP; // 文件服务器IP

	private int FileServerPort; // 文件服务器端口号

	private int FileServerTimeOut = 0; // 超时 单位：毫秒

	/**
	 *  上传文件
	 * 
	 * @param ServerIP : 文件服务器IP
	 * @param ServerPort ：端口号
	 * @param TimeOut : 超时时长
	 * @param LocalFileName ：本地文件路径
	 * @param RemoteFileName ：远程文件路径
	 * @return true|false
	 */
	public static boolean PutFile(String ServerIP, int ServerPort, int TimeOut,String LocalFileName, String RemoteFileName) {

		FileServerClient client = new FileServerClient();
		client.setFileServerIP(ServerIP);
		client.setFileServerPort(ServerPort);
		client.setFileServerTimeOut(TimeOut);
		return client.PutFile(LocalFileName, RemoteFileName);
	}

	/**
	 *  下载文件
	 * 
	 * @param ServerIP :  文件服务器IP
	 * @param ServerPort : 端口号
	 * @param TimeOut : 超时时长
	 * @param LocalFileName : 本地文件路径
	 * @param RemoteFileName : 远程文件路径
	 * @return true|false
	 */
	public static boolean GetFile(String ServerIP, int ServerPort, int TimeOut, String LocalFileName, String RemoteFileName) {
		FileServerClient client = new FileServerClient();
		client.setFileServerIP(ServerIP);
		client.setFileServerPort(ServerPort);
		client.setFileServerTimeOut(TimeOut);
		return client.GetFile(LocalFileName, RemoteFileName);
	}

	/**
	 *  删除文件
	 * 
	 * @param ServerIP ： 文件服务器IP
	 * @param ServerPort ： 端口号
	 * @param TimeOut ：超时时长
	 * @param RemoteFileName　：远程文件路径
	 * @return true|false
	 */
	public static boolean DelFile(String ServerIP, int ServerPort, int TimeOut, String RemoteFileName) {
		FileServerClient client = new FileServerClient();
		client.setFileServerIP(ServerIP);
		client.setFileServerPort(ServerPort);
		client.setFileServerTimeOut(TimeOut);
		return client.DelFile(RemoteFileName);
	}

	/**
	 *   上传文件
	 * 
	 * @param LocalFileName ： 本地文件路径
	 * @param RemoteFileName ： 远程文件路径
	 * @return true|false
	 */
	public boolean PutFile(String LocalFileName, String RemoteFileName) {
		File LocalFile = new File(LocalFileName);
		try {
			return PutFile(LocalFile, RemoteFileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 *  上传文件
	 * 
	 * @param LocalFile ：上传文件类
	 * @param RemoteFileName ：远程文件路径
	 * @return true|false
	 */
	public boolean PutFile(File LocalFile, String RemoteFileName) {
		FileInputStream fio = null;
		try {
			// 上传文件
			fio = new FileInputStream(LocalFile);
			return this.PutFile(fio, RemoteFileName);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (fio != null)
				{
					fio.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	/**
	 *  下载文件
	 * 
	 * @param LocalFileName ：本地文件路径
	 * @param RemoteFileName ： 远程文件路径
	 * @return true|false
	 */
	public boolean GetFile(String LocalFileName, String RemoteFileName) {
		String dir = LocalFileName.substring(0,LocalFileName.lastIndexOf("\\") + 1);
		File LocalFile = new File(dir);
		File file = new File(LocalFileName);
		// 创建路径
		if (!LocalFile.exists()) 
		{
			LocalFile.mkdirs();
		}
		try {
			return GetFile(file, RemoteFileName);
		} catch (Exception e) {
			new File(LocalFileName).delete();
			new File(dir).delete();
			e.printStackTrace();
			return false;
		}
	}

	/**
	 *  下载文件
	 * 
	 * @param LocalFile ：下载文件类
	 * @param RemoteFileName ：远程文件路径
	 * @return true|false
	 */
	public boolean GetFile(File LocalFile, String RemoteFileName)
			throws Exception {
		FileOutputStream FileStream = null;
		try {
			FileStream = new FileOutputStream(LocalFile);
			return this.GetFile(FileStream, RemoteFileName);
		} catch (Exception e){
			throw e;
		}finally{
			if(FileStream!=null)
			{
				FileStream.close();
			}
		}

	}

	/**
	 *  上传文件
	 * 
	 * @param FileStream ：上传文件输入流
	 * @param RemoteFileName ： 远程文件路径
	 * @return true|false
	 */
	public boolean PutFile(FileInputStream FileStream, String RemoteFileName) {
		String message = "";
		Socket socket = null;
		OutputStream out = null;
		InputStream in = null;
		try {
			// 连接服务端

			socket = new Socket(this.FileServerIP, this.FileServerPort);
			// 设置 超时时长
			socket.setSoTimeout(this.FileServerTimeOut);

			out = socket.getOutputStream();
			in = socket.getInputStream();

			// 验证RemoteFileName是否合法
			this.validate(RemoteFileName);

			// 发送上传 报文
			long intFileSize = FileStream.getChannel().size();
			message = "FTCTOSP|\\\\" + RemoteFileName + "|" + intFileSize
					+ "||";
			out.write(MyByte.toBytes(message.getBytes().length + intFileSize));
			out.write(message.getBytes("gbk"));
			out.flush();

			// 上传文件
			byte[] buff = new byte[1024];
			int L = FileStream.read(buff);
			while (L > 0) {
				out.write(buff, 0, L);
				L = FileStream.read(buff);
			}
			out.flush();

			// 去除 文件长度
			byte[] C = new byte[4];
			in.read(C);

			String M_Head = ReadField(in, '|');
			String M_Flag = ReadField(in, '|');
			String M_Message = ReadField(in, '|');
			if (M_Head.equals("FTSTOCP") && M_Flag.equals("F")) 
			{
				throw new Exception("错误：" + M_Message);
			} else if (M_Head.equals("FTSTOCP") && M_Flag.equals("T")) 
			{
				return true;
			} else {
				throw new Exception("错误：" + M_Message);
			}
		}catch (ConnectException e){
			//System.out.println("连接服务器失败！IP:"+this.FileServerIP+" 端口："+this.FileServerPort);
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(in != null)
				{
					in.close();
				}
				if (socket != null)
				{
					socket.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	/**
	 *  下载文件
	 * 
	 * @param FileStream  ： 文件下载输出流
	 * @param RemoteFileName  ： 远程文件路径
	 * @return true|false
	 * @throws Exception
	 */
	public boolean GetFile(FileOutputStream FileStream, String RemoteFileName)
			throws Exception {
		String message = "";
		Socket socket = null;
		InputStream in = null;
		OutputStream out = null;
		try {
			// 连接服务端
			socket = new Socket(this.FileServerIP, this.FileServerPort);
			// 设置 超时时长
			socket.setSoTimeout(this.FileServerTimeOut);
			in = socket.getInputStream();
			out = socket.getOutputStream();
			message = "FTCTOSG|\\" + RemoteFileName + "|";
			out.write(MyByte.toBytes(message.getBytes().length));
			out.flush();
			out.write(message.getBytes("gbk"));
			out.flush();

			// 验证RemoteFileName是否合法
			this.validate(RemoteFileName);

			// 去除 文件长度
			byte[] c = new byte[4];
			in.read(c);

			String M_Head = ReadField(in, '|');
			String M_Flag = ReadField(in, '|');
			String M_Message = ReadField(in, '|');
			byte[] a = new byte[1];
			byte[] b = new byte[1024];
			// 去除 "/"
			in.read(a);
			int readCount = in.read(b);
			
			while (readCount > 0) 
			{
				FileStream.write(b, 0, readCount);
				readCount = in.read(b);
			}

			if (M_Head.equals("FTSTOCG") && M_Flag.equals("F"))
			{
				throw new Exception("错误：" + M_Message);
			} else if (M_Head.equals("FTSTOCG") && M_Flag.equals("T")) 
			{
				return true;
			} else {
				throw new Exception("错误：" + M_Message);
			}
		}catch (ConnectException e){
			//System.out.println("连接服务器失败！IP:"+this.FileServerIP+" 端口："+this.FileServerPort);
			throw e;
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if(in != null)
				{
					in.close();
				}
				if(socket!=null)
				{
					socket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 *  下载文件
	 * 
	 * @param FileStream  ： 文件下载输出流
	 * @param RemoteFileName  ： 远程文件路径
	 * @return true|false
	 * @throws Exception
	 */
	public boolean GetFile(String ip,int port,int timeout, OutputStream servletout, String RemoteFileName)
			throws Exception {
		String message = "";
		Socket socket = null;
		InputStream in = null;
		OutputStream out = null;
		try {
			// 连接服务端
			socket = new Socket(ip, port);
			// 设置 超时时长
			socket.setSoTimeout(timeout);
			in = socket.getInputStream();
			out = socket.getOutputStream();
			message = "FTCTOSG|" + RemoteFileName + "|";
			out.write(MyByte.toBytes(message.getBytes().length));
			out.flush();
			out.write(message.getBytes("gbk"));
			out.flush();

			// 验证RemoteFileName是否合法
			this.validate(RemoteFileName);

			// 去除 文件长度
			byte[] c = new byte[4];
			in.read(c);

			String M_Head = ReadField(in, '|');
			String M_Flag = ReadField(in, '|');
			String M_Message = ReadField(in, '|');
			byte[] a = new byte[1];
			byte[] b = new byte[1024];
			// 去除 "/"
			in.read(a);
			int readCount = in.read(b);
			
			while (readCount > 0) 
			{
				servletout.write(b, 0, readCount);
				readCount = in.read(b);
			}

			if (M_Head.equals("FTSTOCG") && M_Flag.equals("F"))
			{
				throw new Exception("错误：" + M_Message);
			} else if (M_Head.equals("FTSTOCG") && M_Flag.equals("T")) 
			{
				return true;
			} else {
				throw new Exception("错误：" + M_Message);
			}
		}catch (ConnectException e){
			//System.out.println("连接服务器失败！IP:"+this.FileServerIP+" 端口："+this.FileServerPort);
			throw e;
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if(in != null)
				{
					in.close();
				}
				if(socket!=null)
				{
					socket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 *  删除文件
	 * 
	 * @param RemoteFileName ：远程文件路径
	 * @return true|false
	 */
	public boolean DelFile(String RemoteFileName) {
		String message = "";
		Socket socket = null;
		OutputStream out = null;
		InputStream in = null;
		// 获取文件名

		try {
			// 连接 服务端
			socket = new Socket(this.FileServerIP, this.FileServerPort);
			// 设置 超时时长
			socket.setSoTimeout(this.FileServerTimeOut);
			out = socket.getOutputStream();
			in = socket.getInputStream();

			//验证RemoteFileName是否合法
			this.validate(RemoteFileName);

			// 发送 删除报文
			message = "FTCTOSD|\\\\" + RemoteFileName + "|";
			out.write(MyByte.toBytes(message.getBytes().length));
			out.flush();
			out.write(message.getBytes("gbk"));
			out.flush();

			byte[] C = new byte[4];
			in.read(C);

			// 回报长度
			//			int MessageLen = myByte.toInteger(C);
			String M_Head = ReadField(in, '|');
			String M_Flag = ReadField(in, '|');
			String M_Message = ReadField(in, '|');
			//			String M_FileName = ReadField(in, '|');
			if (M_Head.equals("FTSTOCD") && M_Flag.equals("F"))
			{
				throw new Exception("错误：" + M_Message);
			} else if (M_Head.equals("FTSTOCD") && M_Flag.equals("T"))
			{
				return true;
			} else {
				throw new Exception("错误：" + M_Message);
			}
		}catch (ConnectException e){
			//System.out.println("连接服务器失败！IP:"+this.FileServerIP+" 端口："+this.FileServerPort);
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(in != null)
				{
					in.close();
				}
				if (socket != null)
				{
					socket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	/**
	 *  处理回报
	 * 
	 * @param iso ：返回输入流
	 * @param FieldSplit ：报文报域分隔符
	 * @return 输入流第一个报域
	 */
	private String ReadField(InputStream iso, char FieldSplit) {
		String ResultValue = "";
		byte[] C = new byte[1];
		byte[] CC = new byte[1];
		String CH = "";
		try {
			int L = iso.read(C);

			CC[0] = C[0];
			CH = new String(C, "gbk");
			while (!CH.equals(FieldSplit) && L > 0)
			{
				L = iso.read(C);
				if (L > 0) 
				{
					CH = new String(C, "gbk");
					if (!CH.equals(String.valueOf(FieldSplit))) 
					{
						CC = MyByte.byteCon(CC, C);
					} else {
						break;
					}
				}
			}
			ResultValue = new String(CC, "gbk");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ResultValue;
	}

	/**
	 *   验证远程用户名
	 *   
	 * @param  远程命名用户名
	 * @throws Exception
	 */
	private void validate(String remoteName) throws Exception {
		File file = new File(remoteName);
		if (file.getName().indexOf(".") == 0)
		{
			throw new Exception("错误：文件名：[" + file.getName() + "] 不能为空或不合法！");
		}
		if (file.getName().indexOf(".") > 255)
		{
			throw new Exception("错误：文件名：[" + file.getName() + "] 文件命名超过255个字符！");	
		}
			
	}

	public String getFileServerIP() {
		return FileServerIP;
	}

	public void setFileServerIP(String fileServerIP) {
		FileServerIP = fileServerIP;
	}

	public int getFileServerPort() {
		return FileServerPort;
	}

	public void setFileServerPort(int fileServerPort) {
		FileServerPort = fileServerPort;
	}

	public int getFileServerTimeOut() {
		return FileServerTimeOut;
	}

	public void setFileServerTimeOut(int fileServerTimeOut) {
		FileServerTimeOut = fileServerTimeOut;
	}

}