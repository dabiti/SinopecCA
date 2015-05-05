package com.unitop.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;
import java.util.Map.Entry;

import com.unitop.framework.util.XmlTool;
import com.unitop.sysmgr.bo.Baobpz;
import com.unitop.sysmgr.bo.Pingzmx;

public class XmlTest {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		Pingzmx pingz = new Pingzmx();
		pingz.setZhangh("201204200001");
		pingz.setHum("ÀîÕñéª");
		pingz.setPingzh("123");
		StringBuffer s = new StringBuffer();
		String start = "<?xml version=\"1.0\" encoding=\"gbk\"?>";
		String allStart = "<ROOT FILEMODE=\"A0001\" NUM=\"1\">";
		String erwmNodeStart = "<BARCODEDATA>";
		String erwmImage = "<BARINFO KEYWORD=\"{erwmImge}\">"+pingz.getZhangh()+"|"+pingz.getHum()+"|"+pingz.getPingzh()+"</BARINFO>";
		String erwmNodeEnd = "</BARCODEDATA>";

		String textNodeStart="<PRINTDATA>";
		String zhangh = "<DATAINFO KEYWORD=\"{zhangh}\" VALUE=\""+pingz.getZhangh()+"\"></DATAINFO>";
		String hum = "<DATAINFO KEYWORD=\"{hum}\" VALUE=\""+pingz.getHum()+"\"></DATAINFO>";
		String textNodeEnd="</PRINTDATA>";
		String allEnd="</ROOT>";
		
		s.append(start);
		s.append(allStart);
		s.append(erwmNodeStart);
		s.append(erwmImage);
		s.append(erwmNodeEnd);
		s.append(textNodeStart);
		s.append(zhangh);
		s.append(hum);
		s.append(textNodeEnd);
		s.append(allEnd);
//		System.out.println(s.toString());
		
		Properties p = new Properties();
	    p.load(new FileInputStream(new File("c:\\SystemConfig.properties")));
	    Iterator itr = p.entrySet().iterator();
	    while (itr.hasNext())
	    {
	    	Entry e = (Entry)itr.next();
	    	System.out.println(e.getKey() + ": " + e.getValue());
	    }
	}
}
