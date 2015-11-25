package com.venediktVictoria.graphBuilder.exceptions;

public class GBExtraOpenBracketException extends Exception {
	@Override
	public String getMessage() {
		return "Extra opening bracket in expression";
	}
}
