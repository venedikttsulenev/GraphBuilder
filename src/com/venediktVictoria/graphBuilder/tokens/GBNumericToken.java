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
		super(pos, GBTokenType.Undefined);
		for (int i = 0; i < CONST_TOKENS.length && type_ == GBTokenType.Undefined; ++i)
			for (int j = 0; j < CONST_TOKENS[i].length && type_ == GBTokenType.Undefined; ++j) 
				if (pos + CONST_TOKENS[i][j].length() <= s.length() && CONST_TOKENS[i][j].equals(s.substring(pos, pos + CONST_TOKENS[i][j].length()))) {
					type_ = GBTokenType.Numeric;
					value_ = CONST_VALUES[i];
					length_ = CONST_TOKENS[i][j].length();
				}
		if (type_ == GBTokenType.Undefined && isNumeric(s.charAt(pos))) {
			int len = 1;
			while (pos + len < s.length() && isNumeric(s.charAt(pos + len)))
				++len;
			length_ = len;
			try {
				value_ = Double.parseDouble(s.substring(pos, pos + len));
				type_ = GBTokenType.Numeric;
			}
			catch (NumberFormatException e) {
				throw new GBIncorrectNumericException(this);
			}
		}
	}
	public GBNumericToken(double v) {
		super(0, GBTokenType.Numeric);
		value_ = v;
		length_ = String.valueOf(v).length();
	}
	public double value() {
		return value_;
	}
	@Override
	public int length() {
		return length_;
	}
	@Override
	public int priority() {
		return 0;
	}
}
