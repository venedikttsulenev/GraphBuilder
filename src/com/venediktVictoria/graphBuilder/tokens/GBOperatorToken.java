package com.venediktVictoria.graphBuilder.tokens;

public class GBOperatorToken extends GBToken {
	private enum GBOperatorType {
		Plus, Minus, Multiply, Divide, Power
	} 
	GBOperatorType operatorType_;

//-------------------------------------------------------

	public GBOperatorToken(String s, int pos) {
		pos_ = pos;
		type_ = GBTokenType.Operator;
		switch (s.charAt(pos)) {
		case '+':
			operatorType_ = GBOperatorType.Plus;
			break;
		case '-':
			operatorType_ = GBOperatorType.Minus;
			break;
		case '*':
			operatorType_ = GBOperatorType.Multiply;
			break;
		case '/':
			operatorType_ = GBOperatorType.Divide;
			break;
		case '^':
			operatorType_ = GBOperatorType.Power;
			break;
		default:
			type_ = GBTokenType.Undefined;
			break;	
		}
	}
	public double value(double x, double y) {
		switch (operatorType_) {
		case Plus:
			return x + y;
		case Minus:
			return x - y;
		case Multiply:
			return  x * y;
		case Divide:
			return x / y;
		case Power:
			return Math.pow(x, y);
		default:
			return 0;
		}
	}
	public int length() {
		return 1;
	}
	public int priority() {
		switch (operatorType_) {
		case Plus:
		case Minus:
			return 1;
		case Multiply:
		case Divide:
			return 2;
		case Power:
			return 3;
		default:
			return 0;
		}
	}
}