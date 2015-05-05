$(document).ready(function()
{
	setLangw('orgCode',orgCode_maxlength,orgCode_style);
	setLangw('orgcode',orgCode_maxlength,orgCode_style);
	setLangw('netpointflag',orgCode_maxlength,orgCode_style);
	setLangw('code',code_maxlength,code_style);
	setLangw('clerknum_7',code_maxlength,code_style);
	setLangw('name',name_maxlength,name_style);
	setLangw('password',password_maxlength,password_style);
	setLangw('password1',password_maxlength,password_style);
	setLangw('newpassword',password_maxlength,password_style);
	setLangw('newpassword1',password_maxlength,password_style);
	setLangw('clerkCode',clerkCode_maxlength,clerkCode_style);
	setLangw('clerknum',clerkCode_maxlength,clerkCode_style);
	setLangw('account',account_maxlength,account_style);
	setLangw('englishname',englishname_maxlength,englishname_style);
	setLangw('checknum',checknum_maxlength,checknum_style);
	setLangw('oldaccount',oldaccount_maxlength,oldaccount_style);
	setLangw('opendate1',opendate1_maxlength,opendate1_style);
	setLangw('opendate2',opendate1_maxlength,opendate1_style);
	setLangw('startdate1',opendate1_maxlength,opendate1_style);
	setLangw('startdate2',opendate1_maxlength,opendate1_style);
	setLangw('begindate',opendate1_maxlength,opendate1_style);
	setLangw('enddate',opendate1_maxlength,opendate1_style);
	setLangw('time',time_maxlength,time_style);
	setLangw('TGL',TGL_maxlength,TGL_style);
	setLangw('paymentCode',paymentCode_maxlength,paymentCode_style);
	setLangw('sealrect',sealrect_maxlength,sealrect_style);
	setLangw('rvalverect',sealrect_maxlength,sealrect_style);
	setLangw('credencetype',credencetype_maxlength,credencetype_style);
	//ureport
	setLangw('startdate',opendate1_maxlength,opendate1_style);//开户日期
	setLangw('postalcode',postalcode_maxlength,postalcode_style);//幼稚
	
})

/*机构编号*/
{
	var  orgCode_maxlength='6';
	var  orgCode_style="width:150px;"; 
}

/*柜员代码*/
{
	var  code_maxlength='7';
	var  code_style='width:150px;';
}

/*柜员代码*/
{
	var  clerkCode_maxlength='13';
	var  clerkCode_style='width:150px;';
}

/*柜员密码*/
{
	var  password_maxlength='8';
	var  password_style='width:150px;';
}
/*柜员名称*/
{
	var  name_maxlength='15';
	var  name_style="width:150px;";
}
/*账号*/
{
	var  account_maxlength='20';
	var  account_style='width:140px;';
}

/*客户号*/
{
	var  englishname_maxlength='12';
	var  englishname_style='width:140px;';
}

/*凭证号*/
{
	var  checknum_maxlength='20';
	var  checknum_style='width:140px;';
}
/*旧账号*/
{	
	var oldaccount_maxlength ='20';
	var oldaccount_style="width:140px;";
}
/*人行支付密码行号*/
{
	var paymentCode_maxlength='12'
	var paymentCode_style="width:150px; ";
}
/*时间*/
{
	var opendate1_maxlength = '10';
	var opendate1_style="width:62px;";
}

/*统计时间*/
{
	var time_maxlength='6';
	var time_style="width:80px";
}
/*通过率*/
{	
	var TGL_maxlength='3';
	var TGL_style="width:25px";
}
/*凭证参数*/
{
	var credencetype_maxlength='2';
	var credencetype_style="width:150px; ";
}
/*阀值区*/
{
	var sealrect_maxlength = '20';
	var sealrect_style = "width:150px; " ;
}
/*邮政号码*/
{
	var postalcode_maxlength = '6';
	var postalcode_style = "width:50px; " ;
}

function setLangw(name,orgCode_maxlength,orgCode_style){
	if(document.getElementById(name)!=null)
	{
		$("#"+name).attr("maxlength",orgCode_maxlength);
		$("#"+name).attr("style",orgCode_style);
	}
}