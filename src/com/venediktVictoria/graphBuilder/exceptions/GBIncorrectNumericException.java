package com.venediktVictoria.graphBuilder.exceptions;

public class GBIncorrectNumericException extends Exception {
	private int pos_;
	public GBIncorrectNumericException(int pos) {
		 pos_ = pos;
	}
	@Override
	public String getMessage() {
		return "Incorrect numeric input at " + pos_ + "\n";
	}
}
