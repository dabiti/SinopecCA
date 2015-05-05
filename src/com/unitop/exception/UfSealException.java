package com.unitop.exception;

public class UfSealException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 构造方法
	 */
	
	public UfSealException(String message){
		super(message);
	}
	
	public UfSealException(Throwable cause){
		super(cause);
	}
	
	public UfSealException(String message,Throwable cause){
		super(message, cause);
	}

}
