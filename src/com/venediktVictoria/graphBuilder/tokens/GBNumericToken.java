package com.venediktVictoria.graphBuilder.tokens;

import com.venediktVictoria.graphBuilder.exceptions.GBIncorrectNumericException;

public class GBNumericToken extends GBToken {
	static final String CONST_TOKENS[][] = {
		{"pi", "Pi", "PI"},
		{"e", "E"}
	};
	static final double CONST_VALUES[] = {
		Math.PI, Math.E
	};
	private double value_;
	private int length_;

//-------------------------------------------------------

	private static boolean isNumeric(char c) {
		return (c >= '0' && c <= '9') || c == '.';
	}
	public GBNumericToken(String s, int pos) throws GBIncorrectNumericException {
		type_ = GBTokenType.Undefined;
		for (int i = 0; i < CONST_TOKENS.length && type_ == GBTokenType.Undefined; ++i)
			for (int j = 0; j < CONST_TOKENS[i].length && type_ == GBTokenType.Undefined; ++j) 
				if (pos + CONST_TOKENS[i][j].length() <= s.length() && CONST_TOKENS[i][j].equals(s.substring(pos, pos + CONST_TOKENS[i][j].length()))) {
					type_ = GBTokenType.Numeric;
					value_ = CONST_VALUES[i];
					length_ = CONST_TOKENS[i][j].length();
				}
		if (type_ == GBTokenType.Undefined) {
			int len = 0;
			while (pos + len < s.length() && isNumeric(s.charAt(pos + len)))
				++len;
			try {
				value_ = Double.parseDouble(s.substring(pos, pos + len));
				type_ = GBTokenType.Numeric;
				length_ = len;
			}
			catch (NumberFormatException e) {
				throw new GBIncorrectNumericException(pos);
			}
		}
	}
	public GBNumericToken(double v) {
		type_ = GBTokenType.Numeric;
		value_ = v;
	}
	public double value() {
		return value_;
	}
	public int length() {
		return length_;
	}
	public int priority() {
		return 0;
	}
}
