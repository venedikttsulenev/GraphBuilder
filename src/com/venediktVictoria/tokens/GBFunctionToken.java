package com.venediktVictoria.tokens;

public class GBFunctionToken extends GBToken {
	private enum GBFunctionType {
		exp, ln, sin, cos, tan, ctan //, ...
	}
	static final String FUNC_TOKENS[][] = {
		{"exp", "Exp", "EXP"},
		{"ln", "Ln", "LN"},
		{"sin", "Sin", "SIN"},
		{"cos", "Cos", "COS"},
		{"tan", "Tan", "TAN", "tg", "Tg", "TG"},
		{"ctan", "Ctan", "CTAN", "ctg", "Ctg", "CTG"}
		//...
	};
	GBFunctionType functionType_;
	
//-------------------------------------------------------
	
	public GBFunctionToken(String s, int pos) {
		type_ = GBTokenType.Undefined;
		for (int i = 0; i < FUNC_TOKENS.length && type_ == GBTokenType.Undefined; ++i)
			for (int j = 0; j < FUNC_TOKENS[i].length && type_ == GBTokenType.Undefined; ++j)  {
				if (pos + FUNC_TOKENS[i][j].length() <= s.length() && FUNC_TOKENS[i][j].equals(s.substring(pos, pos + FUNC_TOKENS[i][j].length()))) {
					type_ = GBTokenType.Function;
					functionType_ = GBFunctionType.values()[i];
				}
			}
	}
	public double value(double x) {
		switch(functionType_) {
		case exp:
			return Math.exp(x);
		case ln:
			return Math.log(x);
		case sin:
			return Math.sin(x);
		case cos:
			return Math.cos(x);
		case tan:
			return Math.tan(x);
		case ctan:
			return Math.pow(Math.tan(x), -1);
		//...
		default:
			return x;
		}
	}
	public int length() {
		return FUNC_TOKENS[functionType_.ordinal()][0].length();
	}
	public int priority() {
		return 1;
	}
}
