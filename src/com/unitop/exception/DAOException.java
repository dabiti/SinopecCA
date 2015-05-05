package com.unitop.exception;
/**
 * 
 * @author by qjk
 *
 */
public class DAOException extends Exception {

	private static final long serialVersionUID = -2185878761685182677L;
	public DAOException(String msg,Exception e) {
		super(msg,e);	
	}
	public DAOException(String msg){
		super(msg);
	}
	public DAOException() {
	}
}
