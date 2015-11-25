package com.venediktVictoria.graphBuilder.tokens;

public class GBCloseBracketToken extends GBToken {
	public GBCloseBracketToken(String s, int pos) {
		switch (s.charAt(pos)) {
		case ')':
		case ']':
			type_ = GBTokenType.CloseBracket;
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
