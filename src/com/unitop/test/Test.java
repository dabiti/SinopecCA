package com.unitop.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.fop.apps.Driver;
import org.apache.fop.apps.FOPException;
import org.apache.fop.fonts.apps.TTFReader;
import org.xml.sax.InputSource;

public class Test {

	protected void doStart(){
		System.out.println("aaaa");
	}
/*	public static void main(String args[]){
		String str="1.0";
		System.out.println("DELETE FROM R_JIEGYSPZ WHERE BAOBBS = :BAOBBS AND YAOSBS=:YAOSBS".toLowerCase());
		int[] index = {1,2,4,4,5,5,6,6,7,7};
		Map tempMap = new HashMap();
		for(int i=0;i<index.length;i++)
		{
			if(tempMap.get(index[i])==null)
			{
				System.out.println(index[i]);
				tempMap.put(index[i], "");
			}
		}
	}*/
	
	
/*	public static void main(String[] args) {
		for(int i=0;i<10;i++){
			System.out.println( 674+17*i+"    "+(690+17*i));
			
		}
	}*/
	//6970247468C6D07608087F824A5D5B16FAF6FFFAEDE9AC7AD02610C5E917442406B2270047EC1D4A216D0DC3D1B0A4404088320F7A612B78774A4E2B20D3ABCC1C84644B07B10952B58F9A22451543AF92DEDD6AB35DDDD0
/*	public static void main(String[] args) {
		
		String str="{'zhangh':'20140523111111111','hum':'qjk','zhanghxz':'»ù±¾»§','kaihrq':'2014-05-23'}";
		JSONObject jsonObj = JSONObject.fromObject(str);
		System.out.println(jsonObj.get("zhangh"));
		System.out.println(DesUtil.encrypt(str));
		System.out.println(DesUtil.decrypt("6970247468C6D07608087F824A5D5B16FAF6FFFAEDE9AC7AD02610C5E917442406B2270047EC1D4A216D0DC3D1B0A4404088320F7A612B78774A4E2B20D3ABCC1C84644B07B10952B58F9A22451543AF92DEDD6AB35DDDD0"));
		System.out.println(DesUtil.encrypt("agree2seal"));
		
	}*/
	
	/*public static void main(String[] args) throws IOException, FOPException {
		Driver driver=new Driver();
		driver.setInputSource(new InputSource("C:\\simkai.ttf"));
		driver.setOutputStream(new FileOutputStream("D:\\simkai.xml"));
		driver.setRenderer(Driver.RENDER_XML);
		driver.run();
	}*/
	
	public static void main(String[] args) {
		TTFReader.main(new String[]{"C:\\simkai.ttf","simkai.xml"});
		
	}
}
