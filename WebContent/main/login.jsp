<%@ page language="java"  pageEncoding="GBK"%>
<%@ include file="../common/struts1.2.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>管理中心登陆 V1.0</TITLE>
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="../style/admin.css" type="text/css" rel="stylesheet">
</HEAD>
	<BODY >
		<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%"  border=0>
		  <TR>
		     <TD align=middle>
			     <TABLE cellSpacing=0 cellPadding=0 width=468 border=0>
			        <TR>
			          <TD><IMG height=23 src="../images/mgr/login_1.jpg" 
			          width=468></TD></TR>
			        <TR>
			          <TD><IMG height=147 src="../images/mgr/login_2.jpg" 
			            width=468></TD></TR>
			      </TABLE>
			      <TABLE cellSpacing=0 cellPadding=0 width=468 bgColor=#ffffff border=0>
				        <TR>
				          <TD width=16><IMG height=122 src="../images/mgr/login_3.jpg" 
				            width=16></TD>
				          <TD align=middle>
				            <TABLE cellSpacing=0 cellPadding=0 width=230 border=0>
					              <TR height=5>
					                <TD colSpan=3>
					                	<form id=error method=post>
											<span id="orange" style="color:red">
												<html:errors />
											</span>
										</form>	
					                </TD>
					              </TR>
					             <FORM name=form1 action="../syslogin.do" method=post>
					              <TR height=36>
					                <TD></TD>
					                <TD>用户名</TD>
					                <TD><INPUT 
					                  style="BORDER-RIGHT: #000000 1px solid; BORDER-TOP: #000000 1px solid; BORDER-LEFT: #000000 1px solid; BORDER-BOTTOM: #000000 1px solid" 
					                  type="text" maxLength=30 size=24 value="admin"  name="code"></TD></TR>
					              <TR height=36>
					                <TD>&nbsp; </TD>
					                <TD>口　令</TD>
					                <TD><INPUT 
					                  style="BORDER-RIGHT: #000000 1px solid; BORDER-TOP: #000000 1px solid; BORDER-LEFT: #000000 1px solid; BORDER-BOTTOM: #000000 1px solid" 
					                  type="password" maxLength=30 size=24 value=""  name="password"></TD>
					              </TR>
					              <TR height=5>
					                <TD colSpan=3></TD></TR>
					              <TR>
					                <TD colSpan=2>&nbsp;</TD>
					                <TD><button type="submit"> 登 录 </button> &nbsp;&nbsp;&nbsp;&nbsp;
					                <button type="reset"> 取 消  </button></TD>
					              </TR>
				          		</FORM>
				          	</TABLE>
			          	</TD>
			          	<TD width=16><IMG height=122 src="../images/mgr/login_4.jpg"  width=16></TD>
			        </TR>
		      </TABLE>
		      <TABLE cellSpacing=0 cellPadding=0 width=468 border=0>
		        <TR>
		          <TD><IMG height=16 src="../images/mgr/login_5.jpg" 
		          width=468></TD>
		        </TR>
		      </TABLE>
		    </TD>
			</TR>
		</TABLE>
	</BODY>
</HTML>