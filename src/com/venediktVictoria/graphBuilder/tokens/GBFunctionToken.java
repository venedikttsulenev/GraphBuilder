package com.venediktVictoria.graphBuilder.tokens;

public class GBFunctionToken extends GBToken {
	private enum GBFunctionType {
		exp, ln, lg, sin, cos, tan, tg, ctan, ctg, sqrt, cbrt, sinh, cosh, tanh
	}
	private static final String FUNC_TOKENS[][] = {
		{"exp", "Exp", "EXP"},
		{"ln", "Ln", "LN"},
		{"lg", "Lg", "LG"},
		{"sin", "Sin", "SIN"},
		{"cos", "Cos", "COS"},
		{"tan", "Tan", "TAN"},
		{"tg", "Tg", "TG"},
		{"ctan", "Ctan", "CTAN"},
		{"ctg", "Ctg", "CTG"},
		{"sqrt", "Sqrt", "SQRT"},
		{"cbrt", "Cbrt", "CBRT"},
		{"sinh", "Sinh", "SINH"},
		{"cosh", "Cosh", "COSH"},
		{"tanh", "Tanh", "TANH"}
	};
	GBFunctionType functionType_;
	
//-------------------------------------------------------
	
	public GBFunctionToken(String s, int pos) {
		pos_ = pos;
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
		case lg:
			return Math.log10(x);
		case sin:
			return Math.sin(x);
		case cos:
			return Math.cos(x);
		case tan:
		case tg:
			return Math.tan(x);
		case ctan:
		case ctg:
			return Math.pow(Math.tan(x), -1);
		case sqrt:
			return Math.sqrt(x);
		case cbrt:
			return Math.cbrt(x);
		case sinh:
			return Math.sinh(x);
		case cosh:
			return Math.cosh(x);
		case tanh:
			return Math.tanh(x);
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
