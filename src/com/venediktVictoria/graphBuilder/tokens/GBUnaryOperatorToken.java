package com.venediktVictoria.graphBuilder.tokens;

public class GBUnaryOperatorToken extends GBToken {
	public enum GBUnaryOperatorType {Plus, Minus}; 
	private GBUnaryOperatorType unaryOperatorType_;
	public GBUnaryOperatorToken(GBOperatorToken op) {
		super(op.pos(), GBTokenType.UnaryOperator);
		switch (op.operatorType()) {
		case Plus:
			unaryOperatorType_ = GBUnaryOperatorType.Plus;
			break;
		case Minus:
			unaryOperatorType_ = GBUnaryOperatorType.Minus;
			break;
		default:
			type_ = GBTokenType.Undefined;
		}
	}
	public double value(double x) {
		switch (unaryOperatorType_) {
		case Plus:
			return x;
		case Minus:
			return -x;
		default:
			return 0;
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
