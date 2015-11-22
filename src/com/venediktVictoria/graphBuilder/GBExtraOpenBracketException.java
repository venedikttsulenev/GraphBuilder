package com.venediktVictoria.graphBuilder;

public class GBExtraOpenBracketException extends Exception {
	@Override
	public String getMessage() {
		return "Extra opening bracket in expression";
	}
}
