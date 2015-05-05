package com.unitop.framework.util;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

public class FileReader {

	public static byte[] image2Bytes(String imagePath) throws IOException {
		  BufferedImage image = ImageIO.read(new File(imagePath));  
		  ByteArrayOutputStream out = new ByteArrayOutputStream();  
		  ImageIO.write(image, "jpg", out);  
		  byte[] b = out.toByteArray();  
		  out.close();
		  return b;
		 }


	public static void main(String args[]) throws IOException{
		System.out.println(FileReader.image2Bytes("g://piaoju.bmp"));
	}
	/**
	 * 转换file数据为byte数组
	 * 
	 * @return byte数组
	 * @throws IOException
	 */
	public static byte[] fileToBytes(String path) {
		BufferedInputStream in = null;
		ByteArrayOutputStream out = null;
		FileInputStream f = null;
		byte[] rb = null;
		try {
			f = new FileInputStream(path);
			in = new BufferedInputStream(f);
			out = new ByteArrayOutputStream();
			byte[] temp = new byte[1024];
			int size = 0;
			while ((size = in.read(temp)) != -1) {
				out.write(temp, 0, size);
			}
			temp = null;
			rb = out.toByteArray();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
				out = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				in.close();
				in = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				f.close();
				f = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return rb;
	}
}
