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
		System.out.println("报文：" + sendMsg);
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
				System.out.println("发送报文长度：" + length);
				os.write(length.getBytes());
				System.out.println("发送报文"+sendMsg);
				os.write(sendMsg.getBytes());
				System.out.println("发送完毕");
				byte[] b = new byte[6];
				System.out.println("等待读取下行报文");
				is.read(b, 0, 6);
				int len = Integer.parseInt((new String(b).trim()));
				System.out.println("接受下行报文长度" + len);
				byte[] buf = new byte[len];
				is.read(buf, 0, len);
				System.out.println("读取下行报文完毕");
				System.out.println("buf length "+buf.length);
				System.out.println("报文：" + new String(buf));
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

		System.out.println("报文头");
		System.out.println("前端系统流水号:" + msg.substring(0, 20));
		System.out.println("服务编码：" + msg.substring(57, 70));
		System.out.println("前端系统编号：" + msg.substring(70, 74));
		System.out.println("交易分行机构码：" + msg.substring(74, 82));
		System.out.println("交易机构码：" + msg.substring(82, 90));
		System.out.println("交易代码：" + msg.substring(90, 102));
		System.out.println("响应代码：" + msg.substring(102, 114));
		System.out.println("响应信息描述：" + msg.substring(114, 242));
		System.out.println("技术头保留域：" + msg.substring(242, 342));
		System.out.println("终端编号：" + msg.substring(342, 350));
		System.out.println("操作员编号：" + msg.substring(350, 356));
		System.out.println("授权操作员：" + msg.substring(356, 362));
		System.out.println("核心返回信息：" + msg.substring(404, 407));
		System.out.println("交易类型：" + msg.substring(407, 408));
		System.out.println("报文体：" + msg.substring(501));
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
		msg.append("10450000000411157");// 账号
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
		msg.append("604871000000        ");// 前端系统流水号
		msg.append("                    ");// 原服务前端流水号
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
		msg.append("        ");// 终端编号
		msg.append("06003 ");// 柜员编号
		msg.append("      ");// 授权操作员编号
		msg.append("20140623");// 前段业务日期
		msg.append("150155");// 时间
		msg.append("            ");// 后台业务流水号
		msg.append("        ");
		msg.append("    ");// 页码
		msg.append(" ");// 结束标记
		msg.append("000");// 工作站号
		msg.append("   ");// 返回信息码
		msg.append("0");// 渠道号
		msg.append("E");// 交易源系统标志
		int j = 1;
		while (j <= 92) {
			msg.append(" ");
			j++;
		}
		System.out.println("head:" + msg.length());
		return msg.toString();
	}
}
