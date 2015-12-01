package com.venediktVictoria.graphBuilder.exceptions;

import com.venediktVictoria.graphBuilder.tokens.GBToken;

public class GBExtraOpenBracketException extends GBParseException {
	public GBExtraOpenBracketException(GBToken token) {
		super(token);
	}
	@Override
	public String getMessage() {
		return super.getMessage() + "extra opening bracket in expression";
	}
}
