package com.venediktVictoria.tokens;

public class GBOpenBracketToken extends GBToken {
	public GBOpenBracketToken(String s, int pos) {
		switch (s.charAt(pos)) {
		case '(':
		case '[':
			type_ = GBTokenType.OpenBracket;
			break;
		default:
			type_ = GBTokenType.Undefined;
			break;
		}
	}
	public int length() {
		return 1;
	}
	public int priority() {
		return 0;
	}
}
