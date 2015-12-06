package com.venediktVictoria.graphBuilder.tokens;

public class GBUndefinedToken extends GBToken {
	private int length_;
	public GBUndefinedToken(String s, int pos) {
		type_ = GBTokenType.Undefined;
		pos_ = pos;
		length_ = 0;
		while (pos_ + length_ < s.length() && s.charAt(pos_+length_) != ' ')
			++length_;
	}
	public int length() {
		return length_;
	}
	public int priority() {
		return -1;
	}
}
