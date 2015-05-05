package com.unitop.config;
public class Rights {
private static Rights config;

private static  int nowonline=0;
public static Rights getInstance(){
	if(config==null){
		config = new Rights();
	}
	return config;
}

public int getNowonline() {
	return nowonline;
} 

public void setNowonline(int nowonline) {
	this.nowonline = nowonline;
}
}
