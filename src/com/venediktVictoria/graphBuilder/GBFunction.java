package com.venediktVictoria.graphBuilder;

import java.util.*;

import com.venediktVictoria.graphBuilder.exceptions.*;
import com.venediktVictoria.graphBuilder.tokens.*;

public class GBFunction {
	private Vector<GBToken> postfix_;
	
//--------------------------------------------------------------------------------
	
	public GBFunction(String s) throws GBUnknownTokenException, GBExtraCloseBracketException, GBExtraOpenBracketException,
		GBIncorrectNumericException, GBMissingArgumentException, GBMissingOperatorException
	{
		Vector<GBToken>infix = new Vector<GBToken>(s.length());
		int pos = 0;
		GBToken token;
		while (pos < s.length()) { //Переводим строку в массив токенов
			while (pos < s.length() && s.charAt(pos) == ' ')
				++pos;
			if (pos < s.length()) {
				token = new GBFunctionToken(s, pos);
				if (token.type() == GBTokenType.Undefined)
					token = new GBOperatorToken(s, pos);
				if (token.type() == GBTokenType.Undefined)
					token = new GBVariableToken(s, pos);
				if (token.type() == GBTokenType.Undefined)
					token = new GBOpenBracketToken(s, pos);
				if (token.type() == GBTokenType.Undefined)
					token = new GBCloseBracketToken(s, pos);
				if (token.type() == GBTokenType.Undefined)
					token = new GBNumericToken(s, pos);
				if (token.type() == GBTokenType.Undefined) //Unknown token
					throw new GBUnknownTokenException(token);
				pos += token.length();
				infix.addElement(token);
			}
		}
		//check for missing arguments
		//check for missing operators
		switch (infix.firstElement().type()) {
		case Operator:
			throw new GBMissingArgumentException(infix.firstElement());
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
				case Operator:
					throw new GBMissingArgumentException(token);
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
		for (int i = 0; i < infix.size(); ++i) {  //Перевод в обратную польскую нотацию
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
	public double value(double x) {		//Вычисляет значение функции в точке
		//TODO: add some checks and exceptions
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
	}/*
	public double bisect(double a, double b, double eps) {
		double va = value(a);
		double vb = value(b);
		double d = (a + b) / 2;
		double vd = value(d);
		if (va*vb < 0) {
			while (Math.abs(vd) > eps) {
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
			}
		}
		else
			d = a - 1;
		return d;
	}*/
}
