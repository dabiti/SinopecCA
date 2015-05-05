package com.unitop.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ExportTxtUtil {
	private File file;
	public ExportTxtUtil(String fileName) {
		File file=new File(fileName);
		if(file.isFile()){
			file.delete();
		}
		this.file=new File(fileName);
	}
	public ExportTxtUtil() {
	}
	
	public void exportTxt(List<Object> dataList) throws IOException{
		FileOutputStream fos =null;
		PrintWriter pw =null;
		String data="";
		try{
		fos =new FileOutputStream(file,true);
		pw=new PrintWriter(fos);
		for (Object obj : dataList) {
			data=(String)obj;
			pw.println(data);
			pw.flush();
		}
		}finally{
			if(pw!=null){
				pw.close();
			}
			if(fos!=null){
				fos.close();
			}
		}
	}	
	
	
}
