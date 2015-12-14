package com.venediktVictoria.graphBuilder.tokens;

public class GBUndefinedToken extends GBToken {
	private int length_;
	public GBUndefinedToken(String s, int pos) {
		super(pos, GBTokenType.Undefined);
		length_ = 0;
		while (pos_ + length_ < s.length() && s.charAt(pos_+length_) != ' ')
			++length_;
	}
	@Override
	public int length() {
		return length_;
	}
	@Override
	public int priority() {
		return -1;
	}
}
