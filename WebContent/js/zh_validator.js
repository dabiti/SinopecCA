/*
 *  中行BS电子验印系统 前台验印验证规则 定义库 V1.0
 *	
 */
 
var account =/(^\d{17}$)|(^\d{22}$)/; //账户正则表达式 新账号12 

var oldaccount = /[0-9]{14}/; //旧账户正则表达式 旧账号14位

var account6 = /(^\d{17}$)|(^\d{22}$)/; //账户正则表达式 6位数字

var englishname = /\w{9}/; //客户号正则表达式 9位  数字+字母

var clerknum = /(^\d{7}$)/;///([0-9]{5}\w{7})|([0-9]{6}\w{7})/; //柜员号正则表达式 12\13位 6位数字+7位（数字或字母）

var clerknum_7 = /\w{7}/; //柜员代码正则表达式 7位（数字或字母）

var billnum = /\w{10}/;  //凭证号 验证 必须10位 数字

var clerk_password = /\w{8}/; //柜员密码正则表达式 8位（数字或字母）

var netpointflag = /(^[0-9]{4}$)/; //机构号必须5位或6位

var paymentCode = /[0-9]{12}/;//人行支付系统行号必须12位

var netpoint_2or8 = /(^\d{2}$)|(^\d{8}$)/; //行号必须2位或者8位

var netpoint_2or6 = /(^\d{2}$)|(^\d{6}$)/; //行号必须2位或者6位

var voucher_2 = /\w{2}/; //凭证编号 必须2位

var voucher = /^[0-9]{1,4}\,[0-9]{1,4}\,[0-9]{1,4}\,[0-9]{1,4}$/; //凭证 找章区域  实例： 1234,1234,1234,1234

var state_code_2 = /[0-9]{2}/; // 省行代码 必须2位 数字

var organization_code_4 = /[0-9]{4}/; // 机构代码 必须4位 数字

var department_code_2 = /[0-9]{2}/; // 部门代码 必须2位 数字

var clerk_code_4 = /\w{7}/; // 柜员代码 必须4位 

var percentage = /([0-9]{1})|([0-9]{2})|([0-9]{3})/; //百分比必须输入三位，最多输入三位。

var yyyymm = /[0-9]{6}/; //时间格式 YYYYMM 年+月份。

var parameternum = /[^\u4e00-\u9fa5]+/; //验证验印参数编号不能是汉字

var parameterrefername = /[^\u4e00-\u9fa5]+/;

var gongnid = /[^\u4e00-\u9fa5]+/;

var zignid = /[^\u4e00-\u9fa5]+/;

var BAOBBS = /[^\u4e00-\u9fa5]+/;

//省行代码 规则
$.validator.addMethod("state_code_2", function(value,element) {
		return this.optional(element) || state_code_2.test(value);
	},'省行代码必须填写数字，而且必须2位！');
	
//机构代码 规则
$.validator.addMethod("organization_code_4", function(value,element) {
		return this.optional(element) || organization_code_4.test(value);
	},'机构代码必须填写数字，而且必须4位！');
	
//部门代码 规则
$.validator.addMethod("department_code_2", function(value,element) {
		return this.optional(element) || department_code_2.test(value);
	},'部门代码必须填写数字，而且必须2位！');
	
//柜员代码 规则
$.validator.addMethod("clerk_code_4", function(value,element) {
		return this.optional(element) || clerk_code_4.test(value);
	},'柜员号必须4位！');
	
//凭证号 规则
$.validator.addMethod("billnum", function(value,element) {
		return this.optional(element) || billnum.test(value);
	},'凭证号必须输入10位！');				
 
 //验印日志查询"账户+柜员号" 组合查询（必填其一） 
$.validator.addMethod("account||clerknum", function(value,element) {
	var account = $("#account").val();
	var clerknum = $("#clerknum").val();
	return account!=('')||clerknum!=('');
	},'账户和柜员号二者必须填其一');
	
//验印日志查询"新账户+(旧账号、网点机构号)" 组合查询（必填其一） 
$.validator.addMethod("account||oldaccount", function(value,element) {
	var account = $("#account").val();
	var oldaccount = $("#oldaccount").val();
	return account!=('')||oldaccount!=('');
	},'账户和旧账号二者必须填其一');

//验印日志查询"旧账号、网点机构号" 组合查询（填了其一，必须全填） 
$.validator.addMethod("organnum||oldaccount", function(value,element) {
	var account = $("#organnum").val();
	var oldaccount = $("#oldaccount").val();
	return (organnum!=('')&& oldaccount==('')) || (organnum ==('')&& oldaccount!=('')) ;
	},'网点机构号和旧账号填了其一，另一个也必须填');


//新、旧账户不能相同
$.validator.addMethod("account_new_old", function(value,element) {
	var account_old = $("#account").val();
	var account_new = $("#account2").val();
	return this.optional(element)||account_old!=account_new;
	},'账号不能与旧账户相同!');
	
//验证账户 规则
$.validator.addMethod("account", function(value,element) {
		return this.optional(element) || account.test(value);
	},'账户必须填写数字，新账号填写[12]位！');
	
//验证账户 规则
$.validator.addMethod("oldaccount", function(value,element) {
		return this.optional(element) || account.test(value);
	},'账户必须填写数字，  旧账号填写[14]位！');

//验证账户 规则
$.validator.addMethod("account6", function(value,element) {
		return this.optional(element) || account6.test(value);
	},'账户必须填写数字，而且必须6位!');

//验证客户号 规则
$.validator.addMethod("englishname", function(value, element) {
		return this.optional(element) || englishname.test(value);
	},'客户号格式不正确,必须9位!');

//验证柜员号 规则
$.validator.addMethod("clerknum", function(value, element) {
		return this.optional(element) || clerknum.test(value);
	},'柜员号格式不正确,必须7位数字!');
	
//验证柜员代码 规则
$.validator.addMethod("clerknum_7", function(value, element) {
		return this.optional(element) || clerknum_7.test(value);
	},'柜员号格式不正确,必须7位数字!');
	
//柜员密码验证 规则 （必须8位）
$.validator.addMethod("clerk_password", function(value, element) {
		return this.optional(element) || clerk_password.test(value);
	},'柜员密码必须8位！');
	
//机构号验证 规则 （5、6位）
$.validator.addMethod("netpointflag", function(value, element) {
		return this.optional(element) || netpointflag.test(value);
	},'机构号必须填写数字，并且必须4位！');

$.validator.addMethod("paymentCode", function(value, element) {
	return this.optional(element) || paymentCode.test(value);
},'人行支付系统行号必须填写数字，并且必须12位');

	
//行号验证 规则 （必须2位或者8位）
$.validator.addMethod("netpoint_2or8", function(value, element) {
		return this.optional(element) || netpoint_2or8.test(value);
	},'行号必须2位或者8位！');

//行号验证 规则 （必须2位或者6位）
$.validator.addMethod("netpoint_2or6", function(value, element) {
		return this.optional(element) || netpoint_2or6.test(value);
	},'行号必须2位或者6位！');
	
//凭证号验证 规则 （必须6位）
$.validator.addMethod("checknum", function(value, element) {
		return this.optional(element) || checknum.test(value);
	},'凭证号必须6位！');
	
//凭证编号验证 规则 （必须2位）
$.validator.addMethod("voucher_2", function(value, element) {
		return this.optional(element) || voucher_2.test(value);
	},'凭证编号必须2位！');
	
//凭证 找章区域 
$.validator.addMethod("voucher_area", function(value, element) {
		return this.optional(element) || voucher.test(value);
	},'找章区域格式非法！正确的格式（示例）：1,11,111,1111');

//凭证 阀值区域 
$.validator.addMethod("voucher_fz", function(value, element) {
		return this.optional(element) || voucher.test(value);
	},'阀值区域格式非法！正确的格式（示例）：1,11,111,1111');
	
//百分比
$.validator.addMethod("percentage", function(value, element) {
		return this.optional(element) || percentage.test(value);
	},'通过率必须输入数字,只能输入3位数字！');
	
//时间格式 YYYYMM
$.validator.addMethod("yyyymm", function(value, element) {
		return this.optional(element) || yyyymm.test(value);
	},'时间格式：YYYYMM[年+月],例如：201101');
	
//通存通兑 区域名称 
$.validator.addMethod("region_name", function(value, element) {
  var j = 0;
  for (var i=0; i<s.length; i++)
  {
      if (value.substr(i,1).charCodeAt(0)>255){ j = j + 2;}
      else {j++;}
  }
  if(j>50){
  	return this.optional(element) || true;
  }else{
  	return this.optional(element) || false;
  }
	},'区域名称输入过长');	
//验印参数规则
$.validator.addMethod("parameternum", function(value, element) {
	return this.optional(element) || parameternum.test(value);
},'验印参数编号不能为汉字！');
	
$.validator.addMethod("parameterrefername", function(value, element) {
		return this.optional(element) || parameterrefername.test(value);
	},'此处不能填写汉字！');
//菜单定义
$.validator.addMethod("gongnid",function(value,element){
	return this.optional(element)||gongnid.test(value);
},"不能在此填写汉字");
$.validator.addMethod("zignid",function(value,element){
	return this.optional(element)||zignid.test(value);
},"不能在此填写汉字");
$.validator.addMethod("BAOBBS",function(value,element){
	return this.optional(element)||BAOBBS.test(value);
},"不能在此填写汉字");

