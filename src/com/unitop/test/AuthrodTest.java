package com.unitop.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.servlet.http.HttpServletRequest;

import com.unitop.config.SystemConfig;
import com.unitop.sysmgr.bo.Clerk;

public class AuthrodTest {
	
	public static void main(String[] args) {
		
		System.out.println(new AuthrodTest().checkFeature(null));
		
	}

	/**
	 * 拼接上行报文
	 * @param orgCode
	 * @param clerkCode
	 * @param passwd
	 * @param fingerMsg
	 * @return
	 */
	private String createSendMsg(String orgCode,String clerkCode,String passwd,String fingerMsg){
	StringBuffer msg=new StringBuffer(676);
	msg.append("FP");//起始标志
	msg.append("676 ");//包长度
	msg.append("C");//命令类型 C：发起交易  A：响应交易
	msg.append("000001");//交易码 默认000001
	while(orgCode.length()<20){
		orgCode+=" ";
	}
	msg.append(orgCode);//机构号
	msg.append("      1000");//业务系统号 10位
	msg.append(" ");//厂商标示 1位
	msg.append("  ");//保留字段 2位
	while(clerkCode.length()<20){
		clerkCode+=" ";
	}
	msg.append(clerkCode);//柜员号
	if(passwd==null){
		passwd="";
	}
	while(passwd.length()<10){
		passwd+=" ";
	}
	msg.append(passwd);//密码数据
	
	if(fingerMsg==null){
		fingerMsg="";
	}
	while(fingerMsg.length()<600){
		fingerMsg+=" ";
	}
	msg.append(fingerMsg);
		return msg.toString();
	};
	/**
	 * 解析下行报文
	 * @param msg
	 * @return
	 */
	public String resolveMsg(String msg){
		String result="";
		if(msg!=null&&!msg.equals("")){
			result=msg.substring(46, 52);
			return result;
		}else {
			return "000001";
		}
	
	};
	/**
	 * 身份验证
	 */
	public String checkFeature(HttpServletRequest request) {
		String clerkCode="06004";
		String fingerMsg="";
		String passwd="11111";
		//String orgCode=((Clerk)request.getSession().getAttribute("clerk")).getOrgcode();
		
		Socket socket=null;
		try {
			//从系统参数中获取身份验证ip地址和端口号
			//String ip =SystemConfig.getInstance().getValue("feature_ip");
			//String port=SystemConfig.getInstance().getValue("feature_port");
			//打开socket连接
			//System.out.println(ip + port);
			 socket = new Socket("108.199.8.142", 8000);
			if(socket!=null){
				
				OutputStream os=socket.getOutputStream();
				//创建上行报文
				String sendMsg=createSendMsg("0450", clerkCode, passwd, fingerMsg);
				//发送报文
				os.write(sendMsg.getBytes());
				InputStream is =socket.getInputStream();
				byte[] buff=new byte[182];
				//接受下行报文
				is.read(buff);
				//解析下行报文中验证结果
				String result=resolveMsg(new String(buff));
				return result;
				
			}
			//000000 通过 000001未通过 000002 密码过期 000003验证出错
			return "000003";
		} catch (Exception e) {
			return "000004";
		}finally{
			if(socket!=null){
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
		
	}
	
}
