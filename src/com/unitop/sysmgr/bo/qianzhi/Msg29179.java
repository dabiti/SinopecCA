package com.unitop.sysmgr.bo.qianzhi;

import java.util.ArrayList;
import java.util.List;

import com.unitop.exception.BusinessException;
import com.unitop.sysmgr.bo.Yinjk;
import com.unitop.util.ByteArrayUtil;

public class Msg29179 {
	byte[]msg;
	Head head;
	
	
	public Msg29179() {
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

	public Msg29179(byte [] msg) throws BusinessException {
		if(msg==null||msg.length<843){
			throw new BusinessException("获取核心信息不正确");	
		}
		this.msg=msg;
		this.head = new Head(ByteArrayUtil.copyOfRange(msg, 0, 501));
	}
	
	public List<String> getZhanghList()throws BusinessException{
		if(msg==null||msg.length<843){
			throw new BusinessException("获取核心信息不正确");
		}
		List<String> zhanghList = new ArrayList<String>();
		
		for(int i=0;i<10;i++){
			String str =new String(ByteArrayUtil.copyOfRange(msg, 674+17*i, 690+17*i)).trim();
			if(str!=null&&!str.equals("")){
				zhanghList.add(str);
			}
		}
		return zhanghList;
	}
	public void doChange(String lsh){
		lsh = lsh==null?"":lsh;
		while(lsh.length()<20){
			lsh=lsh+" ";
		}
		 msg =(new String (lsh+new String(ByteArrayUtil.copyOfRange(msg, 20, 69))+"3"+new String(ByteArrayUtil.copyOfRange(msg, 70, 672))+"N"+new String(ByteArrayUtil.copyOfRange(msg, 673, msg.length)))).getBytes();
			
	}
	
}
