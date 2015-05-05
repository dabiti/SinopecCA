package com.unitop.test;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.unitop.exception.BusinessException;
import com.unitop.sysmgr.bo.Zhanghb;
import com.unitop.sysmgr.bo.qianzhi.Msg00400;
import com.unitop.util.ByteArrayUtil;

public class QianZhiSocketTest00400 {

	//private static String ip="103.160.1.19";
	private static String ip="103.160.64.25";
	//private static String ip="108.161.1.34";
	public static void main(String[] args) {
		Socket socket = null;
		String sendMsg = createMsg("", "", "");
		System.out.println("���ģ�" + sendMsg);
		try {
			socket = new Socket(ip, 8894);
			if (socket == null) {
			}
			if (socket != null) {
				String zhangh = "";
				String yinjkh = "";
				String yinjkzt = "";
				String length = sendMsg.length() + "";
				while (length.length() < 6) {
					length = "0" + length;
				}
				InputStream is = socket.getInputStream();
				OutputStream os = socket.getOutputStream();
				System.out.println("���ͱ��ĳ��ȣ�" + length);
				os.write(length.getBytes());
				System.out.println("���ͱ���"+sendMsg);
				os.write(sendMsg.getBytes());
				System.out.println("�������");
				byte[] b = new byte[6];
				System.out.println("�ȴ���ȡ���б���");
				is.read(b, 0, 6);
				int len = Integer.parseInt((new String(b).trim()));
				System.out.println("�������б��ĳ���" + len);
				byte[] buf = new byte[len];
				is.read(buf, 0, len);
				System.out.println("��ȡ���б������");
				System.out.println("buf length "+buf.length);
				System.out.println("���ģ�" + new String(buf));
				readMsg(buf);
				
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (Exception e) {

			}
		}

	}
	private static void readMsg(byte[] buf) throws BusinessException {
		String msg = "";
		try {
			msg = new String(buf);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("����ͷ");
		System.out.println("ǰ��ϵͳ��ˮ��:" + msg.substring(0, 20));
		System.out.println("������룺" + msg.substring(57, 70));
		System.out.println("ǰ��ϵͳ��ţ�" + msg.substring(70, 74));
		System.out.println("���׷��л����룺" + msg.substring(74, 82));
		System.out.println("���׻����룺" + msg.substring(82, 90));
		System.out.println("���״��룺" + msg.substring(90, 102));
		System.out.println("��Ӧ���룺" + msg.substring(102, 114));
		System.out.println("��Ӧ��Ϣ������" + msg.substring(114, 242));
		System.out.println("����ͷ������" + msg.substring(242, 342));
		System.out.println("�ն˱�ţ�" + msg.substring(342, 350));
		System.out.println("����Ա��ţ�" + msg.substring(350, 356));
		System.out.println("��Ȩ����Ա��" + msg.substring(356, 362));
		System.out.println("���ķ�����Ϣ��" + msg.substring(404, 407));
		System.out.println("�������ͣ�" + msg.substring(407, 408));
		System.out.println("�����壺" + msg.substring(501));
		Msg00400 msg00400=new Msg00400(buf);
		Zhanghb zhanghb =msg00400.getZhanghb();
		if(zhanghb==null){
			System.out.println("undefinde");
		}else{
			System.out.println(zhanghb.getZhangh());
			System.out.println(zhanghb.getHum());
			System.out.println(zhanghb.getZhanghxz());
			System.out.println(zhanghb.getZhanghzt());
			System.out.println(zhanghb.getJigh());
			System.out.println(zhanghb.getKaihrq());
			
		}

	
	}

	private static String createMsg(String zhangh, String yinjkh, String yinjkzt) {
		StringBuffer msg = new StringBuffer();
		msg.append(createHeadMsg());
		msg.append("10450000000411157");// �˺�
		msg.append("1");// 
		msg.append("0");// 
		msg.append("0");//
		msg.append("00000000");
		msg.append("00000000");
		// msg.append("0000000000000000000000104500000000183920000 E");

		return msg.toString();
	}

	private static Object createHeadMsg() {
		StringBuffer msg = new StringBuffer();
		new Random().nextInt();
		msg.append("604871000000        ");// ǰ��ϵͳ��ˮ��
		msg.append("                    ");// ԭ����ǰ����ˮ��
		msg.append("20160314");
		msg.append("150155000");
		msg.append("S001203000804");
		msg.append("    ");
		msg.append("2450    ");
		msg.append("2450    ");
		int i = 1;
		while (i <= 252) {
			msg.append(" ");
			i++;
		}
		msg.append("        ");// �ն˱��
		msg.append("06003 ");// ��Ա���
		msg.append("      ");// ��Ȩ����Ա���
		msg.append("20140623");// ǰ��ҵ������
		msg.append("150155");// ʱ��
		msg.append("            ");// ��̨ҵ����ˮ��
		msg.append("        ");
		msg.append("    ");// ҳ��
		msg.append(" ");// �������
		msg.append("000");// ����վ��
		msg.append("   ");// ������Ϣ��
		msg.append("0");// ������
		msg.append("E");// ����Դϵͳ��־
		int j = 1;
		while (j <= 92) {
			msg.append(" ");
			j++;
		}
		System.out.println("head:" + msg.length());
		return msg.toString();
	}
}
