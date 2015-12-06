package com.venediktVictoria.graphBuilder.exceptions;

public class GBThereIsNothingToParseException extends GBParseException {
	public GBThereIsNothingToParseException() {
		super(null);
	}
	@Override
	public String getMessage() {
		return super.getMessage() + "there is nothing to parse";
	}
}
