package com.venediktVictoria.graphBuilder.exceptions;

import com.venediktVictoria.graphBuilder.tokens.GBToken;

public class GBMissingArgumentException extends GBParseException {
	public GBMissingArgumentException(GBToken token) {
		super(token);
	}
	@Override
	public String getMessage() {
		return super.getMessage() + "missng argument";
	}
}
