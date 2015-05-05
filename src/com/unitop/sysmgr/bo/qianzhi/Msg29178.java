package com.unitop.sysmgr.bo.qianzhi;

import java.util.ArrayList;
import java.util.List;

import com.unitop.exception.BusinessException;
import com.unitop.sysmgr.bo.Yinjk;
import com.unitop.util.ByteArrayUtil;

public class Msg29178 {
	byte[]msg;
	Head head;
	//List<Yinjk> yinjkList;
	
	
	public Msg29178() {
		// TODO Auto-generated constructor stub
	}

	public byte[] getMsg() {
		return msg;
	}

	public void setMsg(byte[] msg) {
		this.msg = msg;
	}

	public Head getHead() {
		return head;
	}

	public void setHead(Head head) {
		this.head = head;
	}

	public Msg29178(byte [] msg) throws BusinessException {
		int length=msg.length;
		System.out.println(length);
		if(msg==null||msg.length<1398){
			throw new BusinessException("获取核心信息不正确");	
		}
		this.msg=msg;
		this.head = new Head(ByteArrayUtil.copyOfRange(msg, 0, 501));
	}
	
	public List<Yinjk> getYinjkList()throws BusinessException{
		if(msg==null||msg.length<1398){
			throw new BusinessException("获取核心信息不正确");
		}
		
		List<Yinjk> yinjkList = new ArrayList<Yinjk>();
		yinjkList.add(new Yinjk(new String( ByteArrayUtil.copyOfRange(msg, 567, 584)),
				new String( ByteArrayUtil.copyOfRange(msg, 545, 565)), new String(ByteArrayUtil.copyOfRange(msg, 565, 567))));
		yinjkList.add(new Yinjk(new String( ByteArrayUtil.copyOfRange(msg, 737, 754)),
				new String( ByteArrayUtil.copyOfRange(msg, 715, 735)), new String(ByteArrayUtil.copyOfRange(msg, 735, 737))));
		yinjkList.add(new Yinjk(new String( ByteArrayUtil.copyOfRange(msg, 907, 924)),
				new String( ByteArrayUtil.copyOfRange(msg, 885, 905)), new String(ByteArrayUtil.copyOfRange(msg, 905, 907))));
		yinjkList.add(new Yinjk(
				new String( ByteArrayUtil.copyOfRange(msg, 1077, 1094)), new String(ByteArrayUtil.copyOfRange(msg, 1055, 1075)), new String(ByteArrayUtil.copyOfRange(msg, 1075, 1077))));
		yinjkList.add(new Yinjk(
				new String(ByteArrayUtil.copyOfRange(msg, 1247, 1264)), new String(
						ByteArrayUtil.copyOfRange(msg, 1225, 1245)), new String(ByteArrayUtil.copyOfRange(msg, 1245, 1247))));
		return yinjkList;
	}

	public void doChange(String lsh){
		lsh = lsh==null?"":lsh;
		while(lsh.length()<20){
			lsh=lsh+" ";
		}
		 msg =(new String (lsh+new String(ByteArrayUtil.copyOfRange(msg, 20, 69))+"2"+new String(ByteArrayUtil.copyOfRange(msg, 70, msg.length-3))+"01S")).getBytes();
	}
	
}
