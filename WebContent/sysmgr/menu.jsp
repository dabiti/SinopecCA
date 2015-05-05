<%@ page language="java" contentType="text/html;charset=gbk" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/tld/unitop.tld" prefix="unitop"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
	<HEAD>
		<META http-equiv=Content-Type content="text/html; charset=gb2312">
		<LINK href="../style/admin.css" type="text/css" rel="stylesheet">
		<SCRIPT language=javascript>
			function expand(el)
			{
				childObj = document.getElementById("child" + el);
		
				if (childObj.style.display == 'none')
				{
					childObj.style.display = 'block';
				}
				else
				{
					childObj.style.display = 'none';
				}
				return;
			}
		</SCRIPT>
	</HEAD>
	<BODY>
		<TABLE height="100%" cellSpacing=0 cellPadding=0 width=170
			background=../images/mgr/menu_bg.jpg border=0>
			<TR>
				<TD vAlign=top align=middle>
					<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>

						<TR>
							<TD height=10></TD>
						</TR>
					</TABLE>
					<TABLE cellSpacing=0 cellPadding=0 width=150 border=0>

						<TR height=22>
							<TD style="PADDING-LEFT: 30px"
								background=../images/mgr/menu_bt.jpg>
								<A class=menuParent onclick=expand(1) href="javascript:void(0);">系统参数管理</A>
							</TD>
						</TR>
						<TR height=4>
							<TD></TD>
						</TR>
					</TABLE>
					<TABLE id=child1 style="DISPLAY: none" cellSpacing=0 cellPadding=0 width=150 border=0>
						<TR height=20>
							<TD align=middle width=30>
								<IMG height=9 src="../images/mgr/menu_icon.gif" width=9>
							</TD>
							<TD>
								<A class=menuChild
									href="../doDanbwh.do?method=list&gongnid=bskongzcs" target=main>控制参数管理</A>
							</TD>
						</TR>
						<TR height=20>
							<TD align=middle width=30>
								<IMG height=9 src="../images/mgr/menu_icon.gif" width=9>
							</TD>
							<TD>
								<A class=menuChild href="../operDB.do?method=list" target=main>数据操作平台</A>
							</TD>
						</TR>
						<TR height=20>
							<TD align=middle width=30>
								<IMG height=9 src="../images/mgr/menu_icon.gif" width=9>
							</TD>
							<TD>
								<A class=menuChild href="../doDanbwh.do?method=list&gongnid=07" target=main>ocx参数控制 </A>
							</TD>
						</TR>
						<TR height=4>
							<TD colSpan=2></TD>
						</TR>
					</TABLE>
					<TABLE cellSpacing=0 cellPadding=0 width=150 border=0>
						<TR height=22>
							<TD style="PADDING-LEFT: 30px"
								background=../images/mgr/menu_bt.jpg>
								<A class=menuParent onclick=expand(2) href="javascript:void(0);">接口参数管理</A>
							</TD>
						</TR>
						<TR height=4>
							<TD></TD>
						</TR>
					</TABLE>
					<TABLE id=child2 style="DISPLAY: none" cellSpacing=0 cellPadding=0
						width=150 border=0>
						<TR height=20>
							<TD align=middle width=30>
								<IMG height=9 src="../images/mgr/menu_icon.gif" width=9>
							</TD>
							<TD>
								<A class=menuChild href="../doDanbwh.do?method=list&gongnid=peizdpi" target=main>配置DPI</A>
							</TD>
						</TR>
						<TR height=20>
							<TD align=middle width=30>
								<IMG height=9 src="../images/mgr/menu_icon.gif" width=9>
							</TD>
							<TD>
								<A class=menuChild href="../doDanbwh.do?method=list&gongnid=ci_jiedcs" target=main>节点参数维护</A>
							</TD>
						</TR>
						<TR height=20>
							<TD align=middle width=30>
								<IMG height=9 src="../images/mgr/menu_icon.gif" width=9>
							</TD>
							<TD>
								<A class=menuChild href="../doDanbwh.do?method=list&gongnid=ci_xitjd" target=main>节点信息维护</A>
							</TD>
						</TR>
						<TR height=20>
							<TD align=middle width=30>
								<IMG height=9 src="../images/mgr/menu_icon.gif" width=9>
							</TD>
							<TD>
								<A class=menuChild href="../doDanbwh.do?method=list&gongnid=ci_yewxt" target=main>系统信息维护</A>
							</TD>
						</TR>
						<TR height=20>
							<TD align=middle width=30>
								<IMG height=9 src="../images/mgr/menu_icon.gif" width=9>
							</TD>
							<TD>
								<A class=menuChild href="../doDanbwh.do?method=list&gongnid=ci_pingzcs " target=main>系统凭证信息维护</A>
							</TD>
						</TR>
						<TR height=20>
							<TD align=middle width=30>
								<IMG height=9 src="../images/mgr/menu_icon.gif" width=9>
							</TD>
							<TD>
								<A class=menuChild href="../doDanbwh.do?method=list&gongnid=ci_xitcs " target=main>系统参数信息维护</A>
							</TD>
						</TR>
						<TR height=4>
							<TD colSpan=2></TD>
						</TR>
					</TABLE>
					<TABLE cellSpacing=0 cellPadding=0 width=150 border=0>
						<TR height=22>
							<TD style="PADDING-LEFT: 30px"
								background=../images/mgr/menu_bt.jpg>
								<A class=menuParent onclick=expand(3) href="javascript:void(0);">功能定制</A>
							</TD>
						</TR>
						<TR height=4>
							<TD></TD>
						</TR>
					</TABLE>
					<TABLE id=child3 style="DISPLAY: none" cellSpacing=0 cellPadding=0
						width=150 border=0>
						<TR height=20>
							<TD align=middle width=30>
								<IMG height=9 src="../images/mgr/menu_icon.gif" width=9>
							</TD>
							<TD>
							<!-- 
								<A class=menuChild href="cdldz_manager.jsp" target=main>功能菜单定制</A>
							 -->
							    <A class=menuChild href="cdldz_manager.jsp" target=main>功能菜单定制</A>
							</TD>
						</TR>
						<TR height=20>
							<TD align=middle width=30>
								<IMG height=9 src="../images/mgr/menu_icon.gif" width=9>
							</TD>
							<TD>
								<A class=menuChild href="../danbwh.do?method=list" target=main>单表维护定制</A>
							</TD>
						</TR>
						<TR height=20>
							<TD align=middle width=30>
								<IMG height=9 src="../images/mgr/menu_icon.gif" width=9>
							</TD>
							<TD>
								<A class=menuChild href="../ureportMgr.do?method=list"
									target=main>查询报表定制</A>
							</TD>
						</TR>
						<TR height=20>
							<TD align=middle width=30>
								<IMG height=9 src="../images/mgr/menu_icon.gif" width=9>
							</TD>
							<TD>
								<A class=menuChild href="../yewgzConfig.do?method=list"
									target=main>业务规则定制</A>
							</TD>
						</TR>
						<TR height=20>
							<TD align=middle width=30>
								<IMG height=9 src="../images/mgr/menu_icon.gif" width=9>
							</TD>
							<TD>
								<A class=menuChild href="../tisxxConfig.do?method=list"
									target=main>界面提示定制</A>
							</TD>
						</TR>
						<TR height=4>
							<TD colSpan=2></TD>
						</TR>
					</TABLE>

					<TABLE cellSpacing=0 cellPadding=0 width=150 border=0>
						<TR height=22>
							<TD style="PADDING-LEFT: 30px"
								background=../images/mgr/menu_bt.jpg>
								<A class=menuParent onclick=expand(0) href="javascript:void(0);">个人管理</A>
							</TD>
						</TR>
						<TR height=4>
							<TD></TD>
						</TR>
					</TABLE>
					<TABLE id=child0 style="DISPLAY: none" cellSpacing=0 cellPadding=0
						width=150 border=0>

						<TR height=20>
							<TD align=middle width=30>
								<IMG height=9 src="../images/mgr/menu_icon.gif" width=9>
							</TD>
							<TD>
								<A class=menuChild href="main.htm" target=main>修改口令</A>
							</TD>
						</TR>
						<TR height=20>
							<TD align=middle width=30>
								<IMG height=9 src="../images/mgr/menu_icon.gif" width=9>
							</TD>
							<TD>
								<A class=menuChild
									onclick="if (confirm('确定要退出吗？')) return true; else return false;"
									href="../systemlogin.jsp" target=_top>退出系统</A>
							</TD>
						</TR>
					</TABLE>
				</TD>
				<TD width=1 bgColor=#d1e6f7></TD>
			</TR>
		</TABLE>
	</BODY>
</HTML>
