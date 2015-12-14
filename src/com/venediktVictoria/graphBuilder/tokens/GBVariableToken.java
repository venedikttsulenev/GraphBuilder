package com.venediktVictoria.graphBuilder.tokens;

public class GBVariableToken extends GBToken {
	public GBVariableToken(String s, int pos) {
		super(pos, GBTokenType.Undefined);
		char c = s.charAt(pos);
		if (c == 'x' || c == 'X')
			type_ = GBTokenType.Variable;
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