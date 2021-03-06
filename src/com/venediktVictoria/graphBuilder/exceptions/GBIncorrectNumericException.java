package com.venediktVictoria.graphBuilder.exceptions;

import com.venediktVictoria.graphBuilder.tokens.GBToken;

public class GBIncorrectNumericException extends GBParseException {
	public GBIncorrectNumericException(GBToken token) {
		super(token);
	}
	@Override
	public String getMessage() {
		return super.getMessage() + "incorrect numeric input";
	}
}
