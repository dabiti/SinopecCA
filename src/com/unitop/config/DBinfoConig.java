package com.unitop.config;

public class DBinfoConig {
	
	private static String DB_TYPE = "oracle";
	
	static 
	{
		DB_TYPE = SystemConfig.getInstance().getValue("db_type");
	}
	
	//��ȡ��ǰ���ݿ�����
	public static String getDBType()
	{
		if(DB_TYPE.toLowerCase().indexOf("oracle")!=-1)
		{
			DB_TYPE = "oracle";
		}else{
			DB_TYPE = "db2";
		}
		return DB_TYPE;
	}
}
