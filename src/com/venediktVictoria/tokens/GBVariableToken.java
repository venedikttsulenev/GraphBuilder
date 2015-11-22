package com.venediktVictoria.tokens;

public class GBVariableToken extends GBToken {
	public GBVariableToken(String s, int pos) {
		type_ = GBTokenType.Undefined;
		char c = s.charAt(pos);
		if (c == 'x' || c == 'X')
			type_ = GBTokenType.Variable;
	}
	public int length() {
		return 1;
	}
	public int priority() {
		return 0;
	}
}