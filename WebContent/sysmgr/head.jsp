<%@page language="java" contentType="text/html;charset=GBK"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN" "http://www.w3.org/TR/html4/frameset.dtd">
<HTML>
	<HEAD>
		<META http-equiv=Content-Type content="text/html; charset=gb2312">
		<LINK href="../style/admin.css" type="text/css" rel="stylesheet">
	</HEAD>
	<BODY>
		<TABLE cellSpacing=0 cellPadding=0 width="100%" background="../images/mgr/header_bg.jpg" border=0>
			<TR>
				<TD><IMG src="../images/mgr/header_left.jpg"></TD>
				<TD></TD>
				<TD></TD>
				<TD width="50%" ></TD>
				<TD style="FONT-WEIGHT: bold; COLOR: #fff; PADDING-TOP: 20px" align=middle>
					当前用户：admin &nbsp;&nbsp;
					<A style="COLOR: #fff" href="main.htm" target=main>修改口令</A>
					<A style="COLOR: #fff" onclick="if (confirm('确定要退出吗？')) return true; else return false;" href="../systemlogin.jsp" target=_top>退出系统</A>
				</TD>
			</TR>
		</TABLE>
		<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
			<TR bgColor=#1c5db6 height=4>
				<TD></TD>
			</TR>
		</TABLE>
	</BODY>
</HTML>