package com.venediktVictoria.graphBuilder.exceptions;

import com.venediktVictoria.graphBuilder.tokens.GBToken;

public class GBParseException extends Exception {
	protected GBToken token_;
	public GBParseException() {
		token_ = null;
	}
	public GBParseException(GBToken token) {
		token_ = token;
	}
	public GBToken token() {
		return token_;
	}
	@Override
	public String getMessage() {
		return "Parse error: " + ((token_ != null) ? (" at " + String.valueOf(token_.pos()+1) + ": ") : "");
	}
}