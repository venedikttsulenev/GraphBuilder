package com.venediktVictoria.graphBuilder.exceptions;

public class GBUnknownTokenException extends Exception {
	private int pos_;
	public GBUnknownTokenException(int pos) {
		pos_ = pos;
	}
	@Override
	public String getMessage() {
		return "Unknown token at " + String.valueOf(pos_) + "\n";
	}
}