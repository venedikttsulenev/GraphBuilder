package com.venediktVictoria.graphBuilder.exceptions;

import com.venediktVictoria.graphBuilder.tokens.GBToken;

public class GBExtraCloseBracketException extends GBParseException {
	public GBExtraCloseBracketException(GBToken token) {
		super(token);
	}
	@Override
	public String getMessage() {
		return super.getMessage() + "extra closing bracket in expression";
	}
}