package com.venediktVictoria.graphBuilder;

import java.util.*;

import com.venediktVictoria.graphBuilder.exceptions.*;
import com.venediktVictoria.graphBuilder.tokens.*;

public class GBFunction {
	private Vector<GBToken> postfix_;		//Stores reverse polish notation of the function expression
	
//--------------------------------------------------------------------------------
	
	public GBFunction(String s) throws GBUnknownTokenException, GBExtraCloseBracketException, GBExtraOpenBracketException,
		GBIncorrectNumericException, GBMissingArgumentException, GBMissingOperatorException, GBThereIsNothingToParseException
	{
		Vector<GBToken>infix = new Vector<GBToken>(2*s.length());
		int pos = 0;
		GBToken token;
		while (pos < s.length()) { 				//Translate string to an array of tokens
			while (pos < s.length() && s.charAt(pos) == ' ')
				++pos;
			if (pos < s.length()) {
				token = new GBFunctionToken(s, pos);
				if (token.type() == GBTokenType.Undefined)
					token = new GBOperatorToken(s, pos);
				if (token.type() == GBTokenType.Undefined) {
					token = new GBVariableToken(s, pos);
					if (token.type() == GBTokenType.Undefined)
						token = new GBOpenBracketToken(s, pos);
					if (token.type() == GBTokenType.Undefined)
						token = new GBCloseBracketToken(s, pos);
					if (token.type() == GBTokenType.Undefined)
						token = new GBNumericToken(s, pos);
					if (token.type() == GBTokenType.Undefined) { //Unknown token 
						token = new GBUndefinedToken(s, pos);
						throw new GBUnknownTokenException(token);
					}
				}
				else if (token.type() == GBTokenType.Operator) {
					if (!infix.isEmpty()) {
						switch (infix.lastElement().type()) {
						case Numeric:
						case Variable:
						case CloseBracket:
							break;
						default:
							token = new GBUnaryOperatorToken((GBOperatorToken) token);
						}
					}
					else
						token = new GBUnaryOperatorToken((GBOperatorToken) token);
				}
				pos += token.length();
				infix.addElement(token);
			}
		}
		
		if (infix.size() == 0) 					//Check if expression is empty
			throw new GBThereIsNothingToParseException();

		switch (infix.firstElement().type()) { 	//Check if there are missing arguments/operators
		case Operator:
			GBOperatorToken opToken = (GBOperatorToken) infix.firstElement();
			switch (opToken.operatorType()) {
			case Plus :
			case Minus:
				break;
			default:
				throw new GBMissingArgumentException(infix.firstElement());
			}
			break;
		case CloseBracket:
			throw new GBMissingOperatorException(infix.firstElement());
		default:
			break;
		}	
		
		for (int i = 0; i < infix.size()-1; ++i) {
			token = infix.elementAt(i);
			switch (token.type()) {
			case Function:
			case Operator:
			case OpenBracket:
				switch (infix.elementAt(i+1).type()) {
				case CloseBracket:
					throw new GBMissingArgumentException(token);
				case Operator:
					GBOperatorToken opToken = (GBOperatorToken) infix.elementAt(i+1);
					switch (opToken.operatorType()) {
					case Plus:
					case Minus:
						break;
					default:
						throw new GBMissingArgumentException(token);
					}
					break;
				default:
					break;
				}
				break;
			case Numeric:
			case Variable:
			case CloseBracket:
				switch(infix.elementAt(i+1).type()) {
				case Numeric:
				case Variable:
				case Function:
				case OpenBracket:
					throw new GBMissingOperatorException(token);
				default:
					break;
				}
				break;
			default:
				break;
			}
		}
		
		switch (infix.lastElement().type()) {
		case Operator:
		case Function:
		case OpenBracket:
			throw new GBMissingArgumentException(infix.lastElement());
		default:
			break;
		}
		
		Stack<GBToken> opStack = new Stack<GBToken>();
		postfix_ = new Vector<GBToken>(infix.size());
		for (int i = 0; i < infix.size(); ++i) {  		//Translate to reverse polish notation
			token = infix.elementAt(i);
			switch (token.type()) {
			case Numeric:
			case Variable:
				postfix_.addElement(token);
				break;
			case Operator:
				while (!opStack.empty() && (opStack.peek().type() == GBTokenType.Operator || opStack.peek().type() == GBTokenType.Function) && token.priority() <= opStack.peek().priority())
					postfix_.addElement(opStack.pop());
				opStack.push(token);
				break;
			case UnaryOperator:
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
					throw new GBExtraCloseBracketException(token);
				}
				opStack.pop(); // pop opening bracket out of stack
				break;
			default:
				break;
			}
		}
		infix.clear();
		while (!opStack.empty()) { 
			token = opStack.pop();
			if (token.type() == GBTokenType.OpenBracket)
				throw new GBExtraOpenBracketException(token);
			else
				postfix_.addElement(token);
		}
	}
	
	//--------------------------------------------------------------------------------
		
	public double value(double x) {		
		/*
		 * Returns value of function at point x
		 */
		Stack<Double> calc = new Stack<Double>();
		GBToken token = null;
		for (int i = 0; i < postfix_.size(); ++i) {
			token = postfix_.elementAt(i);
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
			case UnaryOperator:
				GBUnaryOperatorToken unOpToken = (GBUnaryOperatorToken)token;
				calc.push(Double.valueOf(unOpToken.value(calc.pop().doubleValue())));
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
	private double bisect(double a, double b, double eps, int maxIter) {
		int i = 0;
		double va = value(a);
		double vb = value(b);
		double d = (a + b) / 2;
		double vd = value(d);
		if (va*vb < 0) {
			while (Math.abs(vd) > eps && i < maxIter) {
				d = (a + b) / 2;
				vd = value(d);
				if (va * vd < 0) {
					b = d;
					vb = vd;
				}
				else {
					a = d;
					va = vd;
				}
				++i;
			}
		}
		else
			d = a - 1;
		return d;
	}
	public boolean isSuspiciousForHavingVerticalAsymptoteIn(double a, double b) {
		/*
		 * Returns true if rate (Yb - Ya)/(b - a) is greater than SUSPICIOUS_RATE_KOEFF by absolute value
		 */
		final double SUSPICIOUS_RATE_KOEFF = 10;
		boolean ans = false;
		double va = value(a);
		double vb = value(b);
		if (Math.signum(va)*Math.signum(vb) > 0)
			ans = false;
		else 
			ans = Math.abs((va - vb) / (a-b)) > SUSPICIOUS_RATE_KOEFF;
		return ans;
	}
	public boolean hasVerticalAsymptoteIn(double a, double b) {
		final int MAX_ITERATIONS = 500;
		final double EPS = 0.0001;
		boolean has = false;
		double va = value(a);	
		double vb = value(b);
		if (va*vb > 0)
			return false;
		try {
			double d = bisect(a, b, EPS, MAX_ITERATIONS);
			double vd = value(d);
			if (Math.abs(vd) > EPS || !Double.isFinite(vd))
				has = true;
		}
		catch(ArithmeticException e) {
			has = true;
		}
		return has;
	}
	
}
