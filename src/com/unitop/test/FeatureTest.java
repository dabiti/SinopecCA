package com.unitop.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.servlet.http.HttpServletRequest;

import com.unitop.config.SystemConfig;
import com.unitop.sysmgr.bo.Clerk;

public class FeatureTest {

	public static void main(String[] args) {
		System.out.println(checkFeature());
	}
	
	//ƴ�����б���
	private  static String createSendMsg(String orgCode,String clerkCode,String passwd,String fingerMsg){
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
	msg.append("1");//���̱�ʾ 1λ
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
	//�������б���
	public static String resolveMsg(String msg){
		String result="";
		if(msg!=null&&!msg.equals("")){
			result=msg.substring(46, 52);
			return result;
		}else {
			return "000001";
		}
	
	};
	//�����֤
	public static String checkFeature() {
	
		Socket socket=null;
		try {
			
			 socket = new Socket("108.199.8.142", 8000);
			 System.out.println("----------------------������---------------------");
			 
			 String sendMsg=createSendMsg("0230", "0000111", "aaaaaaaa", "67=8;8>6;65:3=9:=9;?0:5>40???2:<3;=332>7:73?5885=2=663554;94998710;859<<8<54330:5==144?2><;3;>20;79?7>6;2;73145=426?1<?01:0?<4200;69460=?18:3?183250?42<64:135?54487752>:8=12122280922?>2?;029<=:52;=<97?14:;::99:;>28?41?>18=5=472607711?6?180:>20:66:;1377:95<7=96>18?5;=0>:=<<42=449=:<436491?;9639021>=42166=497;<0405=248?<9>78=0:=03369>:?:>7001=??>84924022629>>;;6<12=1=6>;105=>?6=;85769=?55928044?2=14:610:03?6>6:>370?1734400==<3:8:21013>571=;4<820>=???191>?06;4<3242<>78=9;:>2:<6614??1?4;;?>780==8:0>;;0=134<4113");
			 System.out.println("���ɱ��ģ�"+sendMsg);
			if(socket!=null){
				OutputStream os=socket.getOutputStream();
				System.out.println("���ͱ���");
				os.write(sendMsg.getBytes());
				System.out.println("���ͳɹ�");
				InputStream is =socket.getInputStream();
				byte[] buff=new byte[182];
				
				is.read(buff);
				System.out.println("��ȡ�����"+new String(buff));
				String result=resolveMsg(new String(buff));
				System.out.println("���������"+result);
				return result;
				
			}
			return "000003";
		} catch (Exception e) {
			return "000003";
		}finally{
			if(socket!=null){
				try {
					System.out.println("-------------------��֤���--------------------");
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
		
	}
}
