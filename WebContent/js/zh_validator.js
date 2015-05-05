/*
 *  ����BS������ӡϵͳ ǰ̨��ӡ��֤���� ����� V1.0
 *	
 */
 
var account =/(^\d{17}$)|(^\d{22}$)/; //�˻�������ʽ ���˺�12 

var oldaccount = /[0-9]{14}/; //���˻�������ʽ ���˺�14λ

var account6 = /(^\d{17}$)|(^\d{22}$)/; //�˻�������ʽ 6λ����

var englishname = /\w{9}/; //�ͻ���������ʽ 9λ  ����+��ĸ

var clerknum = /(^\d{7}$)/;///([0-9]{5}\w{7})|([0-9]{6}\w{7})/; //��Ա��������ʽ 12\13λ 6λ����+7λ�����ֻ���ĸ��

var clerknum_7 = /\w{7}/; //��Ա����������ʽ 7λ�����ֻ���ĸ��

var billnum = /\w{10}/;  //ƾ֤�� ��֤ ����10λ ����

var clerk_password = /\w{8}/; //��Ա����������ʽ 8λ�����ֻ���ĸ��

var netpointflag = /(^[0-9]{4}$)/; //�����ű���5λ��6λ

var paymentCode = /[0-9]{12}/;//����֧��ϵͳ�кű���12λ

var netpoint_2or8 = /(^\d{2}$)|(^\d{8}$)/; //�кű���2λ����8λ

var netpoint_2or6 = /(^\d{2}$)|(^\d{6}$)/; //�кű���2λ����6λ

var voucher_2 = /\w{2}/; //ƾ֤��� ����2λ

var voucher = /^[0-9]{1,4}\,[0-9]{1,4}\,[0-9]{1,4}\,[0-9]{1,4}$/; //ƾ֤ ��������  ʵ���� 1234,1234,1234,1234

var state_code_2 = /[0-9]{2}/; // ʡ�д��� ����2λ ����

var organization_code_4 = /[0-9]{4}/; // �������� ����4λ ����

var department_code_2 = /[0-9]{2}/; // ���Ŵ��� ����2λ ����

var clerk_code_4 = /\w{7}/; // ��Ա���� ����4λ 

var percentage = /([0-9]{1})|([0-9]{2})|([0-9]{3})/; //�ٷֱȱ���������λ�����������λ��

var yyyymm = /[0-9]{6}/; //ʱ���ʽ YYYYMM ��+�·ݡ�

var parameternum = /[^\u4e00-\u9fa5]+/; //��֤��ӡ������Ų����Ǻ���

var parameterrefername = /[^\u4e00-\u9fa5]+/;

var gongnid = /[^\u4e00-\u9fa5]+/;

var zignid = /[^\u4e00-\u9fa5]+/;

var BAOBBS = /[^\u4e00-\u9fa5]+/;

//ʡ�д��� ����
$.validator.addMethod("state_code_2", function(value,element) {
		return this.optional(element) || state_code_2.test(value);
	},'ʡ�д��������д���֣����ұ���2λ��');
	
//�������� ����
$.validator.addMethod("organization_code_4", function(value,element) {
		return this.optional(element) || organization_code_4.test(value);
	},'�������������д���֣����ұ���4λ��');
	
//���Ŵ��� ����
$.validator.addMethod("department_code_2", function(value,element) {
		return this.optional(element) || department_code_2.test(value);
	},'���Ŵ��������д���֣����ұ���2λ��');
	
//��Ա���� ����
$.validator.addMethod("clerk_code_4", function(value,element) {
		return this.optional(element) || clerk_code_4.test(value);
	},'��Ա�ű���4λ��');
	
//ƾ֤�� ����
$.validator.addMethod("billnum", function(value,element) {
		return this.optional(element) || billnum.test(value);
	},'ƾ֤�ű�������10λ��');				
 
 //��ӡ��־��ѯ"�˻�+��Ա��" ��ϲ�ѯ��������һ�� 
$.validator.addMethod("account||clerknum", function(value,element) {
	var account = $("#account").val();
	var clerknum = $("#clerknum").val();
	return account!=('')||clerknum!=('');
	},'�˻��͹�Ա�Ŷ��߱�������һ');
	
//��ӡ��־��ѯ"���˻�+(���˺š����������)" ��ϲ�ѯ��������һ�� 
$.validator.addMethod("account||oldaccount", function(value,element) {
	var account = $("#account").val();
	var oldaccount = $("#oldaccount").val();
	return account!=('')||oldaccount!=('');
	},'�˻��;��˺Ŷ��߱�������һ');

//��ӡ��־��ѯ"���˺š����������" ��ϲ�ѯ��������һ������ȫ� 
$.validator.addMethod("organnum||oldaccount", function(value,element) {
	var account = $("#organnum").val();
	var oldaccount = $("#oldaccount").val();
	return (organnum!=('')&& oldaccount==('')) || (organnum ==('')&& oldaccount!=('')) ;
	},'��������ź;��˺�������һ����һ��Ҳ������');


//�¡����˻�������ͬ
$.validator.addMethod("account_new_old", function(value,element) {
	var account_old = $("#account").val();
	var account_new = $("#account2").val();
	return this.optional(element)||account_old!=account_new;
	},'�˺Ų�������˻���ͬ!');
	
//��֤�˻� ����
$.validator.addMethod("account", function(value,element) {
		return this.optional(element) || account.test(value);
	},'�˻�������д���֣����˺���д[12]λ��');
	
//��֤�˻� ����
$.validator.addMethod("oldaccount", function(value,element) {
		return this.optional(element) || account.test(value);
	},'�˻�������д���֣�  ���˺���д[14]λ��');

//��֤�˻� ����
$.validator.addMethod("account6", function(value,element) {
		return this.optional(element) || account6.test(value);
	},'�˻�������д���֣����ұ���6λ!');

//��֤�ͻ��� ����
$.validator.addMethod("englishname", function(value, element) {
		return this.optional(element) || englishname.test(value);
	},'�ͻ��Ÿ�ʽ����ȷ,����9λ!');

//��֤��Ա�� ����
$.validator.addMethod("clerknum", function(value, element) {
		return this.optional(element) || clerknum.test(value);
	},'��Ա�Ÿ�ʽ����ȷ,����7λ����!');
	
//��֤��Ա���� ����
$.validator.addMethod("clerknum_7", function(value, element) {
		return this.optional(element) || clerknum_7.test(value);
	},'��Ա�Ÿ�ʽ����ȷ,����7λ����!');
	
//��Ա������֤ ���� ������8λ��
$.validator.addMethod("clerk_password", function(value, element) {
		return this.optional(element) || clerk_password.test(value);
	},'��Ա�������8λ��');
	
//��������֤ ���� ��5��6λ��
$.validator.addMethod("netpointflag", function(value, element) {
		return this.optional(element) || netpointflag.test(value);
	},'�����ű�����д���֣����ұ���4λ��');

$.validator.addMethod("paymentCode", function(value, element) {
	return this.optional(element) || paymentCode.test(value);
},'����֧��ϵͳ�кű�����д���֣����ұ���12λ');

	
//�к���֤ ���� ������2λ����8λ��
$.validator.addMethod("netpoint_2or8", function(value, element) {
		return this.optional(element) || netpoint_2or8.test(value);
	},'�кű���2λ����8λ��');

//�к���֤ ���� ������2λ����6λ��
$.validator.addMethod("netpoint_2or6", function(value, element) {
		return this.optional(element) || netpoint_2or6.test(value);
	},'�кű���2λ����6λ��');
	
//ƾ֤����֤ ���� ������6λ��
$.validator.addMethod("checknum", function(value, element) {
		return this.optional(element) || checknum.test(value);
	},'ƾ֤�ű���6λ��');
	
//ƾ֤�����֤ ���� ������2λ��
$.validator.addMethod("voucher_2", function(value, element) {
		return this.optional(element) || voucher_2.test(value);
	},'ƾ֤��ű���2λ��');
	
//ƾ֤ �������� 
$.validator.addMethod("voucher_area", function(value, element) {
		return this.optional(element) || voucher.test(value);
	},'���������ʽ�Ƿ�����ȷ�ĸ�ʽ��ʾ������1,11,111,1111');

//ƾ֤ ��ֵ���� 
$.validator.addMethod("voucher_fz", function(value, element) {
		return this.optional(element) || voucher.test(value);
	},'��ֵ�����ʽ�Ƿ�����ȷ�ĸ�ʽ��ʾ������1,11,111,1111');
	
//�ٷֱ�
$.validator.addMethod("percentage", function(value, element) {
		return this.optional(element) || percentage.test(value);
	},'ͨ���ʱ�����������,ֻ������3λ���֣�');
	
//ʱ���ʽ YYYYMM
$.validator.addMethod("yyyymm", function(value, element) {
		return this.optional(element) || yyyymm.test(value);
	},'ʱ���ʽ��YYYYMM[��+��],���磺201101');
	
//ͨ��ͨ�� �������� 
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
	},'���������������');	
//��ӡ��������
$.validator.addMethod("parameternum", function(value, element) {
	return this.optional(element) || parameternum.test(value);
},'��ӡ������Ų���Ϊ���֣�');
	
$.validator.addMethod("parameterrefername", function(value, element) {
		return this.optional(element) || parameterrefername.test(value);
	},'�˴�������д���֣�');
//�˵�����
$.validator.addMethod("gongnid",function(value,element){
	return this.optional(element)||gongnid.test(value);
},"�����ڴ���д����");
$.validator.addMethod("zignid",function(value,element){
	return this.optional(element)||zignid.test(value);
},"�����ڴ���д����");
$.validator.addMethod("BAOBBS",function(value,element){
	return this.optional(element)||BAOBBS.test(value);
},"�����ڴ���д����");

