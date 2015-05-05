package com.unitop.sysmgr.bo;
import java.io.Serializable;

/*
 * by wp
 */

public class Pingzkcsyb implements Serializable {
	
	private PingzkcsybId pingzkcsybid= new PingzkcsybId();
	private Integer zongs;
	private Integer yilzs;
	private Integer shengyzs;
	private Integer zuofzs;
	
	public Pingzkcsyb(){
		super();
	}
	public Pingzkcsyb(PingzkcsybId pingzkcsybid,Integer zongs,Integer yilzs,Integer shengyzs,Integer zuofzs){
		super();
		this.pingzkcsybid=pingzkcsybid;
		this.zongs=zongs;
		this.yilzs=yilzs;
		this.shengyzs=shengyzs;
		this.zuofzs=zuofzs;
	}
	public void setPingzkcsybid(PingzkcsybId pingzkcsybid) {
		this.pingzkcsybid = pingzkcsybid;
	}
	public PingzkcsybId getPingzkcsybid() {
		return pingzkcsybid;
	}

	public void setZongs(Integer zongs) {
		this.zongs = zongs;
	}

	public Integer getZongs() {
		return zongs;
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
