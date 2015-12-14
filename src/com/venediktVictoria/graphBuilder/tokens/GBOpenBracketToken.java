package com.venediktVictoria.graphBuilder.tokens;

public class GBOpenBracketToken extends GBToken {
	public GBOpenBracketToken(String s, int pos) {
		super (pos, GBTokenType.Undefined);
		switch (s.charAt(pos)) {
		case '(':
		case '[':
			type_ = GBTokenType.OpenBracket;
			break;
		default:
			break;
		}
	}
	@Override
	public int length() {
		return 1;
	}
	@Override
	public int priority() {
		return 0;
	}
}
