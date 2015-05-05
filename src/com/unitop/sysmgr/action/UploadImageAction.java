package com.unitop.sysmgr.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.springframework.stereotype.Controller;

import com.unitop.sysmgr.bo.Voucher;
import com.unitop.sysmgr.bo.VoucherPk;
import com.unitop.sysmgr.dao.VoucherDao;
import com.unitop.sysmgr.form.VoucherForm;

/**
 * @version 上传图片
 * @author ldd
 * 
 */
@Controller("/uploadImage")
public class UploadImageAction extends ExDispatchAction {
	@Resource
	private VoucherDao voucherDao;
	
//	/**
//	 * @version 上传图片
//	 * @author ldd
//	 * 
//	 */
//	public ActionForward uploadImage(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response) {
//		String netpointflag = request.getParameter("netpointflag");
//		String orgcode = request.getParameter("orgcode");
//		String vouchcode = request.getParameter("vouchcode");
//		//System.out.println("orgcode:"+orgcode+"vouchode:"+vouchcode);
//		VoucherForm uf = (VoucherForm) form;
//		//判断图片格式是否正确
//		String imageType = uf.getFile().getContentType();
//		if(!(("image/pjpeg").equals(imageType)))
//		{
//			return this.showMessageJSP(mapping,request,"success","图片格式不正确,上传图片格式必须JPG格式!");
//		}
//		uf.setNetpointflag(netpointflag);
//		if(!orgcode.equals("")&&!vouchcode.equals(""))
//		{
//			VoucherPk id = new VoucherPk();
//			id.setNetpointflag(orgcode);
//			id.setPingzbs(vouchcode);
//			
//			//Voucher vouch = this.getSystemMgrService().getVoucherByOrgCodeAndVouchType(orgcode,vouchcode);
//			try{
//			Voucher vouch = voucherDao.getVoucher(id);
//			uf.setPingzbs(vouch.getId().getPingzbs());
//			uf.setNetpointflag(vouch.getId().getNetpointflag());
//			uf.setPingzmc(vouch.getPingzmc());
//			uf.setZhaozq(vouch.getZhaozq());
//			uf.setYouxq(vouch.getYouxq());
//			uf.setShifqy(vouch.getShifqy());
//			}catch(Exception e){
//				e.printStackTrace();
//			}
//		}
//		String imageName =!netpointflag.equals("")?netpointflag:orgcode;
//		FormFile file = (FormFile) uf.getFile();
//		//System.out.println("file:"+file);
//		String path = request.getSession().getServletContext().getRealPath("/");
//		//System.out.println("path:"+path);
//		InputStream in = null;
//		OutputStream out = null;
//		try{
//		in = file.getInputStream();
//		out = new FileOutputStream(path + "/images/voucher/" + imageName+".jpg");
//		int read = 0;
//		byte[] buffer = new byte[1024];
//		while ((read = in.read(buffer, 0, 1024)) != -1) {
//			out.write(buffer, 0, read);
//		}
//		}catch (Exception e){
//			e.printStackTrace();
//		}finally{
//				try {
//					if(in!=null)in.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//				try {
//					if(out!=null)out.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//		}
//		File f = new File(path + "/images/voucher/" + imageName+".jpg");
//		ImageInputStream iis = null;
//		try {
//			   Iterator readers = ImageIO.getImageReadersByFormatName("jpg");
//			   ImageReader reader = (ImageReader)readers.next();
//			   iis = ImageIO.createImageInputStream(f);
//			   reader.setInput(iis, true);
//			   int  width = reader.getWidth(0);
//			   int  height =  reader.getHeight(0);
//			   
//			   int i = 1;
//			   int n = 1;
//			   
//			 //如果发现分子小于分母 不进行等比例转换
//				if(width>500&&height>400)
//				{
//					 i =4;
//					 n =4;
//				}
//				
//			   request.setAttribute("width",width);
//			   request.setAttribute("height",height);
//			   if(width <300&&height<300)
//			   {
//					request.setAttribute("img_width", height);
//					request.setAttribute("img_width", height);
//			   }else{
//					request.setAttribute("img_width", width/i);
//					request.setAttribute("img_height", height/n);
//			   }
//			  } catch (IOException e) {
//		}finally{
//			try {
//				if(iis!=null)
//				iis.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			request.setAttribute("type",request.getParameter("type"));
//		}
//		request.setAttribute("netpointflag", netpointflag);
//		request.setAttribute("orgcode", orgcode);
//		return mapping.findForward("success");
//	}

	public ActionForward image(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String fileName =request.getParameter("filename"); 
		String path = request.getSession().getServletContext().getRealPath("/");
		String imageUrl = path+"/images/voucher/"+fileName+".jpg";
		InputStream in = null;
		ServletOutputStream out =null;
		try {
			File file = new File(imageUrl);
			if(!file.exists())file = new File(path + "/images/voucher/bill.jpg");
			in = new FileInputStream(file);
			out = response.getOutputStream();
			// 一次读多个字节
			byte[] tempbytes = new byte[1024];
			int byteread = 0;
			while ((byteread = in.read(tempbytes)) != -1) {
				out.write(tempbytes, 0, byteread);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
				try {
					if (in != null)
					in.close();
				} catch (IOException e1) {
				}
				try {
					if(out!=null)out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return mapping.findForward("success");
	}
	
	public void setVoucherDao(VoucherDao voucherDao) {
		this.voucherDao = voucherDao;
	}
}
