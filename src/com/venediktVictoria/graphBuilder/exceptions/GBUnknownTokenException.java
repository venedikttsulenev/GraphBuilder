package com.venediktVictoria.graphBuilder.exceptions;

import com.venediktVictoria.graphBuilder.tokens.GBToken;

public class GBUnknownTokenException extends GBParseException {
	public GBUnknownTokenException(GBToken token) {
		super(token);
	}
	@Override
	public String getMessage() {
		return super.getMessage() + "unknown token";
	}
}