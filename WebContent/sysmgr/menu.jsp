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
								<A class=menuParent onclick=expand(1) href="javascript:void(0);">ϵͳ��������</A>
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
									href="../doDanbwh.do?method=list&gongnid=bskongzcs" target=main>���Ʋ�������</A>
							</TD>
						</TR>
						<TR height=20>
							<TD align=middle width=30>
								<IMG height=9 src="../images/mgr/menu_icon.gif" width=9>
							</TD>
							<TD>
								<A class=menuChild href="../operDB.do?method=list" target=main>���ݲ���ƽ̨</A>
							</TD>
						</TR>
						<TR height=20>
							<TD align=middle width=30>
								<IMG height=9 src="../images/mgr/menu_icon.gif" width=9>
							</TD>
							<TD>
								<A class=menuChild href="../doDanbwh.do?method=list&gongnid=07" target=main>ocx�������� </A>
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
								<A class=menuParent onclick=expand(2) href="javascript:void(0);">�ӿڲ�������</A>
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
								<A class=menuChild href="../doDanbwh.do?method=list&gongnid=peizdpi" target=main>����DPI</A>
							</TD>
						</TR>
						<TR height=20>
							<TD align=middle width=30>
								<IMG height=9 src="../images/mgr/menu_icon.gif" width=9>
							</TD>
							<TD>
								<A class=menuChild href="../doDanbwh.do?method=list&gongnid=ci_jiedcs" target=main>�ڵ����ά��</A>
							</TD>
						</TR>
						<TR height=20>
							<TD align=middle width=30>
								<IMG height=9 src="../images/mgr/menu_icon.gif" width=9>
							</TD>
							<TD>
								<A class=menuChild href="../doDanbwh.do?method=list&gongnid=ci_xitjd" target=main>�ڵ���Ϣά��</A>
							</TD>
						</TR>
						<TR height=20>
							<TD align=middle width=30>
								<IMG height=9 src="../images/mgr/menu_icon.gif" width=9>
							</TD>
							<TD>
								<A class=menuChild href="../doDanbwh.do?method=list&gongnid=ci_yewxt" target=main>ϵͳ��Ϣά��</A>
							</TD>
						</TR>
						<TR height=20>
							<TD align=middle width=30>
								<IMG height=9 src="../images/mgr/menu_icon.gif" width=9>
							</TD>
							<TD>
								<A class=menuChild href="../doDanbwh.do?method=list&gongnid=ci_pingzcs " target=main>ϵͳƾ֤��Ϣά��</A>
							</TD>
						</TR>
						<TR height=20>
							<TD align=middle width=30>
								<IMG height=9 src="../images/mgr/menu_icon.gif" width=9>
							</TD>
							<TD>
								<A class=menuChild href="../doDanbwh.do?method=list&gongnid=ci_xitcs " target=main>ϵͳ������Ϣά��</A>
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
								<A class=menuParent onclick=expand(3) href="javascript:void(0);">���ܶ���</A>
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
								<A class=menuChild href="cdldz_manager.jsp" target=main>���ܲ˵�����</A>
							 -->
							    <A class=menuChild href="cdldz_manager.jsp" target=main>���ܲ˵�����</A>
							</TD>
						</TR>
						<TR height=20>
							<TD align=middle width=30>
								<IMG height=9 src="../images/mgr/menu_icon.gif" width=9>
							</TD>
							<TD>
								<A class=menuChild href="../danbwh.do?method=list" target=main>����ά������</A>
							</TD>
						</TR>
						<TR height=20>
							<TD align=middle width=30>
								<IMG height=9 src="../images/mgr/menu_icon.gif" width=9>
							</TD>
							<TD>
								<A class=menuChild href="../ureportMgr.do?method=list"
									target=main>��ѯ������</A>
							</TD>
						</TR>
						<TR height=20>
							<TD align=middle width=30>
								<IMG height=9 src="../images/mgr/menu_icon.gif" width=9>
							</TD>
							<TD>
								<A class=menuChild href="../yewgzConfig.do?method=list"
									target=main>ҵ�������</A>
							</TD>
						</TR>
						<TR height=20>
							<TD align=middle width=30>
								<IMG height=9 src="../images/mgr/menu_icon.gif" width=9>
							</TD>
							<TD>
								<A class=menuChild href="../tisxxConfig.do?method=list"
									target=main>������ʾ����</A>
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
								<A class=menuParent onclick=expand(0) href="javascript:void(0);">���˹���</A>
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
								<A class=menuChild href="main.htm" target=main>�޸Ŀ���</A>
							</TD>
						</TR>
						<TR height=20>
							<TD align=middle width=30>
								<IMG height=9 src="../images/mgr/menu_icon.gif" width=9>
							</TD>
							<TD>
								<A class=menuChild
									onclick="if (confirm('ȷ��Ҫ�˳���')) return true; else return false;"
									href="../systemlogin.jsp" target=_top>�˳�ϵͳ</A>
							</TD>
						</TR>
					</TABLE>
				</TD>
				<TD width=1 bgColor=#d1e6f7></TD>
			</TR>
		</TABLE>
	</BODY>
</HTML>
