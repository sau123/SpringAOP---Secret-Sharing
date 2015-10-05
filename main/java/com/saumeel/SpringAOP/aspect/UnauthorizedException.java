package com.saumeel.SpringAOP.aspect;
/*
 * Custom Exception : When an authorized call is made, an exception of this class is thrown.
 */

public class UnauthorizedException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public UnauthorizedException() {
	}

	public UnauthorizedException(String arg0) {
		super(arg0);
		System.out.println(arg0);
	}
}
