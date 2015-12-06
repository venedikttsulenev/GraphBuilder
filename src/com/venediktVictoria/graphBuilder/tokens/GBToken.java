package com.venediktVictoria.graphBuilder.tokens;

abstract public class GBToken {
	protected GBTokenType type_;
	protected int pos_;
	public GBTokenType type() {
		return type_;
	}
	public int pos() {
		return pos_;
	}
	abstract public int length();
	abstract public int priority();
}