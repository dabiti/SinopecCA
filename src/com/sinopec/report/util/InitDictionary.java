package com.sinopec.report.util;

import java.util.Map;

import com.sinopec.report.model.ReportDictionary;

public class InitDictionary {
	
	private static Map dictionaryMap = null;

	// ¹¹Ôìº¯Êý
	public  InitDictionary(Map dictionaryMap) throws Exception {
		if (dictionaryMap == null) {
			throw new Exception("dictionaryMap is null!");
		}
		InitDictionary.dictionaryMap = dictionaryMap;
	}

	public void putInitDictionary(String id, ReportDictionary reportDictionary) {
		InitDictionary.dictionaryMap.put(id, reportDictionary);
	}

	public static  ReportDictionary getInitDictionary(String id) {
		return (ReportDictionary) InitDictionary.dictionaryMap.get(id);
	}
}
