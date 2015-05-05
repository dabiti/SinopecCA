package com.unitop.sysmgr.bo;
import java.io.Serializable;

/*
 *by wp 
 */

public class Pingzjgsyb implements Serializable{
	
	private PingzjgsybId pingzjgsybid = new PingzjgsybId();
	private Integer yilzs;
	private Integer shengyzs;
	private Integer zuofzs;
	
	public Pingzjgsyb(){
		super();
	}
	public Pingzjgsyb(PingzjgsybId pingzjgsybid,Integer yilzs,Integer shengyzs,Integer zuofzs){
		super();
		this.pingzjgsybid=pingzjgsybid;
		this.setYilzs(yilzs);
		this.setShengyzs(shengyzs);
		this.setZuofzs(zuofzs);
	}
	public void setPingzjgsybid(PingzjgsybId pingzjgsybid) {
		this.pingzjgsybid = pingzjgsybid;
	}
	public PingzjgsybId getPingzjgsybid() {
		return pingzjgsybid;
	}
	public void setYilzs(Integer yilzs) {
		this.yilzs = yilzs;
	}
	public Integer getYilzs() {
		return yilzs;
	}
	public void setShengyzs(Integer shengyzs) {
		this.shengyzs = shengyzs;
	}
	public Integer getShengyzs() {
		return shengyzs;
	}
	public void setZuofzs(Integer zuofzs) {
		this.zuofzs = zuofzs;
	}
	public Integer getZuofzs() {
		return zuofzs;
	}

}
