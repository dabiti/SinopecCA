package com.unitop.exception;

public class BusinessException extends Exception {

	private static final long serialVersionUID = -2185878761685182677L;

	public BusinessException() {
	}

	public BusinessException(String s) {
		super(s);
	}
	public BusinessException(Exception e) {
		super(e);
	}
}
