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
	 * ƴ�����б���
	 * @param orgCode
	 * @param clerkCode
	 * @param passwd
	 * @param fingerMsg
	 * @return
	 */
	private String createSendMsg(String orgCode,String clerkCode,String passwd,String fingerMsg){
	StringBuffer msg=new StringBuffer(676);
	msg.append("FP");//��ʼ��־
	msg.append("676 ");//������
	msg.append("C");//�������� C��������  A����Ӧ����
	msg.append("000001");//������ Ĭ��000001
	while(orgCode.length()<20){
		orgCode+=" ";
	}
	msg.append(orgCode);//������
	msg.append("      1000");//ҵ��ϵͳ�� 10λ
	msg.append(" ");//���̱�ʾ 1λ
	msg.append("  ");//�����ֶ� 2λ
	while(clerkCode.length()<20){
		clerkCode+=" ";
	}
	msg.append(clerkCode);//��Ա��
	if(passwd==null){
		passwd="";
	}
	while(passwd.length()<10){
		passwd+=" ";
	}
	msg.append(passwd);//��������
	
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
	 * �������б���
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
	 * �����֤
	 */
	public String checkFeature(HttpServletRequest request) {
		String clerkCode="06004";
		String fingerMsg="";
		String passwd="11111";
		//String orgCode=((Clerk)request.getSession().getAttribute("clerk")).getOrgcode();
		
		Socket socket=null;
		try {
			//��ϵͳ�����л�ȡ�����֤ip��ַ�Ͷ˿ں�
			//String ip =SystemConfig.getInstance().getValue("feature_ip");
			//String port=SystemConfig.getInstance().getValue("feature_port");
			//��socket����
			//System.out.println(ip + port);
			 socket = new Socket("108.199.8.142", 8000);
			if(socket!=null){
				
				OutputStream os=socket.getOutputStream();
				//�������б���
				String sendMsg=createSendMsg("0450", clerkCode, passwd, fingerMsg);
				//���ͱ���
				os.write(sendMsg.getBytes());
				InputStream is =socket.getInputStream();
				byte[] buff=new byte[182];
				//�������б���
				is.read(buff);
				//�������б�������֤���
				String result=resolveMsg(new String(buff));
				return result;
				
			}
			//000000 ͨ�� 000001δͨ�� 000002 ������� 000003��֤����
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
