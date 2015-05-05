package com.unitop.framework.util;

/**
 * FileServer �ͻ��� ֧����
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

	private String FileServerIP; // �ļ�������IP

	private int FileServerPort; // �ļ��������˿ں�

	private int FileServerTimeOut = 0; // ��ʱ ��λ������

	/**
	 *  �ϴ��ļ�
	 * 
	 * @param ServerIP : �ļ�������IP
	 * @param ServerPort ���˿ں�
	 * @param TimeOut : ��ʱʱ��
	 * @param LocalFileName �������ļ�·��
	 * @param RemoteFileName ��Զ���ļ�·��
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
	 *  �����ļ�
	 * 
	 * @param ServerIP :  �ļ�������IP
	 * @param ServerPort : �˿ں�
	 * @param TimeOut : ��ʱʱ��
	 * @param LocalFileName : �����ļ�·��
	 * @param RemoteFileName : Զ���ļ�·��
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
	 *  ɾ���ļ�
	 * 
	 * @param ServerIP �� �ļ�������IP
	 * @param ServerPort �� �˿ں�
	 * @param TimeOut ����ʱʱ��
	 * @param RemoteFileName����Զ���ļ�·��
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
	 *   �ϴ��ļ�
	 * 
	 * @param LocalFileName �� �����ļ�·��
	 * @param RemoteFileName �� Զ���ļ�·��
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
	 *  �ϴ��ļ�
	 * 
	 * @param LocalFile ���ϴ��ļ���
	 * @param RemoteFileName ��Զ���ļ�·��
	 * @return true|false
	 */
	public boolean PutFile(File LocalFile, String RemoteFileName) {
		FileInputStream fio = null;
		try {
			// �ϴ��ļ�
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
	 *  �����ļ�
	 * 
	 * @param LocalFileName �������ļ�·��
	 * @param RemoteFileName �� Զ���ļ�·��
	 * @return true|false
	 */
	public boolean GetFile(String LocalFileName, String RemoteFileName) {
		String dir = LocalFileName.substring(0,LocalFileName.lastIndexOf("\\") + 1);
		File LocalFile = new File(dir);
		File file = new File(LocalFileName);
		// ����·��
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
	 *  �����ļ�
	 * 
	 * @param LocalFile �������ļ���
	 * @param RemoteFileName ��Զ���ļ�·��
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
	 *  �ϴ��ļ�
	 * 
	 * @param FileStream ���ϴ��ļ�������
	 * @param RemoteFileName �� Զ���ļ�·��
	 * @return true|false
	 */
	public boolean PutFile(FileInputStream FileStream, String RemoteFileName) {
		String message = "";
		Socket socket = null;
		OutputStream out = null;
		InputStream in = null;
		try {
			// ���ӷ����

			socket = new Socket(this.FileServerIP, this.FileServerPort);
			// ���� ��ʱʱ��
			socket.setSoTimeout(this.FileServerTimeOut);

			out = socket.getOutputStream();
			in = socket.getInputStream();

			// ��֤RemoteFileName�Ƿ�Ϸ�
			this.validate(RemoteFileName);

			// �����ϴ� ����
			long intFileSize = FileStream.getChannel().size();
			message = "FTCTOSP|\\\\" + RemoteFileName + "|" + intFileSize
					+ "||";
			out.write(MyByte.toBytes(message.getBytes().length + intFileSize));
			out.write(message.getBytes("gbk"));
			out.flush();

			// �ϴ��ļ�
			byte[] buff = new byte[1024];
			int L = FileStream.read(buff);
			while (L > 0) {
				out.write(buff, 0, L);
				L = FileStream.read(buff);
			}
			out.flush();

			// ȥ�� �ļ�����
			byte[] C = new byte[4];
			in.read(C);

			String M_Head = ReadField(in, '|');
			String M_Flag = ReadField(in, '|');
			String M_Message = ReadField(in, '|');
			if (M_Head.equals("FTSTOCP") && M_Flag.equals("F")) 
			{
				throw new Exception("����" + M_Message);
			} else if (M_Head.equals("FTSTOCP") && M_Flag.equals("T")) 
			{
				return true;
			} else {
				throw new Exception("����" + M_Message);
			}
		}catch (ConnectException e){
			//System.out.println("���ӷ�����ʧ�ܣ�IP:"+this.FileServerIP+" �˿ڣ�"+this.FileServerPort);
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
	 *  �����ļ�
	 * 
	 * @param FileStream  �� �ļ����������
	 * @param RemoteFileName  �� Զ���ļ�·��
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
			// ���ӷ����
			socket = new Socket(this.FileServerIP, this.FileServerPort);
			// ���� ��ʱʱ��
			socket.setSoTimeout(this.FileServerTimeOut);
			in = socket.getInputStream();
			out = socket.getOutputStream();
			message = "FTCTOSG|\\" + RemoteFileName + "|";
			out.write(MyByte.toBytes(message.getBytes().length));
			out.flush();
			out.write(message.getBytes("gbk"));
			out.flush();

			// ��֤RemoteFileName�Ƿ�Ϸ�
			this.validate(RemoteFileName);

			// ȥ�� �ļ�����
			byte[] c = new byte[4];
			in.read(c);

			String M_Head = ReadField(in, '|');
			String M_Flag = ReadField(in, '|');
			String M_Message = ReadField(in, '|');
			byte[] a = new byte[1];
			byte[] b = new byte[1024];
			// ȥ�� "/"
			in.read(a);
			int readCount = in.read(b);
			
			while (readCount > 0) 
			{
				FileStream.write(b, 0, readCount);
				readCount = in.read(b);
			}

			if (M_Head.equals("FTSTOCG") && M_Flag.equals("F"))
			{
				throw new Exception("����" + M_Message);
			} else if (M_Head.equals("FTSTOCG") && M_Flag.equals("T")) 
			{
				return true;
			} else {
				throw new Exception("����" + M_Message);
			}
		}catch (ConnectException e){
			//System.out.println("���ӷ�����ʧ�ܣ�IP:"+this.FileServerIP+" �˿ڣ�"+this.FileServerPort);
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
	 *  �����ļ�
	 * 
	 * @param FileStream  �� �ļ����������
	 * @param RemoteFileName  �� Զ���ļ�·��
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
			// ���ӷ����
			socket = new Socket(ip, port);
			// ���� ��ʱʱ��
			socket.setSoTimeout(timeout);
			in = socket.getInputStream();
			out = socket.getOutputStream();
			message = "FTCTOSG|" + RemoteFileName + "|";
			out.write(MyByte.toBytes(message.getBytes().length));
			out.flush();
			out.write(message.getBytes("gbk"));
			out.flush();

			// ��֤RemoteFileName�Ƿ�Ϸ�
			this.validate(RemoteFileName);

			// ȥ�� �ļ�����
			byte[] c = new byte[4];
			in.read(c);

			String M_Head = ReadField(in, '|');
			String M_Flag = ReadField(in, '|');
			String M_Message = ReadField(in, '|');
			byte[] a = new byte[1];
			byte[] b = new byte[1024];
			// ȥ�� "/"
			in.read(a);
			int readCount = in.read(b);
			
			while (readCount > 0) 
			{
				servletout.write(b, 0, readCount);
				readCount = in.read(b);
			}

			if (M_Head.equals("FTSTOCG") && M_Flag.equals("F"))
			{
				throw new Exception("����" + M_Message);
			} else if (M_Head.equals("FTSTOCG") && M_Flag.equals("T")) 
			{
				return true;
			} else {
				throw new Exception("����" + M_Message);
			}
		}catch (ConnectException e){
			//System.out.println("���ӷ�����ʧ�ܣ�IP:"+this.FileServerIP+" �˿ڣ�"+this.FileServerPort);
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
	 *  ɾ���ļ�
	 * 
	 * @param RemoteFileName ��Զ���ļ�·��
	 * @return true|false
	 */
	public boolean DelFile(String RemoteFileName) {
		String message = "";
		Socket socket = null;
		OutputStream out = null;
		InputStream in = null;
		// ��ȡ�ļ���

		try {
			// ���� �����
			socket = new Socket(this.FileServerIP, this.FileServerPort);
			// ���� ��ʱʱ��
			socket.setSoTimeout(this.FileServerTimeOut);
			out = socket.getOutputStream();
			in = socket.getInputStream();

			//��֤RemoteFileName�Ƿ�Ϸ�
			this.validate(RemoteFileName);

			// ���� ɾ������
			message = "FTCTOSD|\\\\" + RemoteFileName + "|";
			out.write(MyByte.toBytes(message.getBytes().length));
			out.flush();
			out.write(message.getBytes("gbk"));
			out.flush();

			byte[] C = new byte[4];
			in.read(C);

			// �ر�����
			//			int MessageLen = myByte.toInteger(C);
			String M_Head = ReadField(in, '|');
			String M_Flag = ReadField(in, '|');
			String M_Message = ReadField(in, '|');
			//			String M_FileName = ReadField(in, '|');
			if (M_Head.equals("FTSTOCD") && M_Flag.equals("F"))
			{
				throw new Exception("����" + M_Message);
			} else if (M_Head.equals("FTSTOCD") && M_Flag.equals("T"))
			{
				return true;
			} else {
				throw new Exception("����" + M_Message);
			}
		}catch (ConnectException e){
			//System.out.println("���ӷ�����ʧ�ܣ�IP:"+this.FileServerIP+" �˿ڣ�"+this.FileServerPort);
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
	 *  ����ر�
	 * 
	 * @param iso ������������
	 * @param FieldSplit �����ı���ָ���
	 * @return ��������һ������
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
	 *   ��֤Զ���û���
	 *   
	 * @param  Զ�������û���
	 * @throws Exception
	 */
	private void validate(String remoteName) throws Exception {
		File file = new File(remoteName);
		if (file.getName().indexOf(".") == 0)
		{
			throw new Exception("�����ļ�����[" + file.getName() + "] ����Ϊ�ջ򲻺Ϸ���");
		}
		if (file.getName().indexOf(".") > 255)
		{
			throw new Exception("�����ļ�����[" + file.getName() + "] �ļ���������255���ַ���");	
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