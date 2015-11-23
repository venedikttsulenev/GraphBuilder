package com.venediktVictoria.graphBuilder;

import java.util.*;
import com.venediktVictoria.tokens.*;

public class GBFunction {
	private Vector<GBToken> postfix_;
	
//--------------------------------------------------------------------------------
	
	public GBFunction(String s) throws GBUnknownTokenException, GBExtraCloseBracketException, GBExtraOpenBracketException {
		Vector<GBToken>infix = new Vector<GBToken>(s.length());
		int pos = 0;
		GBToken token;
		while (pos < s.length()) {
			while (s.charAt(pos) == ' ' && pos < s.length())
				++pos;
			if (pos < s.length()) {
				token = new GBFunctionToken(s, pos);
				if (token.type() == GBTokenType.Undefined)
					token = new GBOperatorToken(s, pos);
				if (token.type() == GBTokenType.Undefined)
					token = new GBNumericToken(s, pos);
				if (token.type() == GBTokenType.Undefined)
					token = new GBVariableToken(s, pos);
				if (token.type() == GBTokenType.Undefined)
					token = new GBOpenBracketToken(s, pos);
				if (token.type() == GBTokenType.Undefined)
					token = new GBCloseBracketToken(s, pos);
				if (token.type() == GBTokenType.Undefined) //Unknown token
					throw new GBUnknownTokenException(pos);
				pos += token.length();
				infix.addElement(token);
			}
		}
		Stack<GBToken> opStack = new Stack<GBToken>();
		postfix_ = new Vector<GBToken>(infix.size());
		for (int i = 0; i < infix.size(); ++i) {
			token = infix.elementAt(i);
			switch (token.type()) {
			case Numeric:
			case Variable:
				postfix_.addElement(token);
				break;
			case Operator:
				//TO DO: check for missing arguments
				while (!opStack.empty() && (opStack.peek().type() == GBTokenType.Operator || opStack.peek().type() == GBTokenType.Function) && token.priority() <= opStack.peek().priority())
					postfix_.addElement(opStack.pop());
				opStack.push(token);
				break;
			case Function:
			case OpenBracket:
				opStack.push(token);
				break;
			case CloseBracket:
				try {
					while (opStack.peek().type() != GBTokenType.OpenBracket) 
						postfix_.addElement(opStack.pop());
				}
				catch (EmptyStackException e) {
					throw new GBExtraCloseBracketException();
				}
				opStack.pop(); // pop opening bracket out of stack
				break;
			default:
				break;
			}
		}
		while (!opStack.empty()) {
			token = opStack.pop();
			if (token.type() == GBTokenType.OpenBracket)
				throw new GBExtraOpenBracketException();
			else
				postfix_.addElement(token);
		}
	}
	public double value(double x) {
		//TO DO: add some checks and exceptions
		Stack<Double> calc = new Stack<Double>();
		for (int i = 0; i < postfix_.size(); ++i) {
			GBToken token = postfix_.elementAt(i);
			switch (token.type()) {
			case Variable:
				calc.push(Double.valueOf(x));
				break;
			case Numeric:
				GBNumericToken numToken = (GBNumericToken)token;
				calc.push(Double.valueOf(numToken.value()));
				break;
			case Function:
				GBFunctionToken funcToken = (GBFunctionToken)token;
				calc.push(Double.valueOf(funcToken.value(calc.pop().doubleValue())));
				break;
			case Operator:
				GBOperatorToken opToken = (GBOperatorToken)token;
				double b = calc.pop().doubleValue();
				double a = calc.pop().doubleValue();
				calc.push(Double.valueOf(opToken.value(a, b)));
				break;
			default:
				break;
			}
		}
		return calc.pop();
	}
}
