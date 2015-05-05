package com.unitop.framework.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateModel {

	private String year;
	private String month;
	private String day;
	private int week;
	
	//ʱ�����
	public DateModel(String dateString){
		year = dateString.substring(0, 4);
		month =dateString.substring(5,7);
		day =dateString.substring(8,10);
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			 date = format.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		char index =(char) cal.get(Calendar.DAY_OF_WEEK);
		switch(index){
			case 1: week=7;break;//����
			case 2: week=1;break;//��һ
			case 3: week=2;break;//�ܶ�
			case 4: week=3;break;//����
			case 5: week=4;break;//����
			case 6: week=5;break;//����
			case 7: week=6;break;//����
		}
	}
	
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	
	public int getWeek() {
		return week;
	}

	public void setWeek(int week) {
		this.week = week;
	}
}
