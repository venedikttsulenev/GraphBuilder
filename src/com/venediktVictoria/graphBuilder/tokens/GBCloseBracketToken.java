package com.venediktVictoria.graphBuilder.tokens;

public class GBCloseBracketToken extends GBToken {
	public GBCloseBracketToken(String s, int pos) {
		super(pos, GBTokenType.Undefined);
		switch (s.charAt(pos)) {
		case ')':
		case ']':
			type_ = GBTokenType.CloseBracket;
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
