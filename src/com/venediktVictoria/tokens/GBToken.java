package com.venediktVictoria.tokens;

abstract public class GBToken {
	protected GBTokenType type_;
	public GBTokenType type() {
		return type_;
	}
	abstract public int length();
	abstract public int priority();
}
