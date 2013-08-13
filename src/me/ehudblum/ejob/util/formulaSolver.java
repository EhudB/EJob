package me.ehudblum.ejob.util;

import de.congrace.exp4j.Calculable;
import de.congrace.exp4j.ExpressionBuilder;
import de.congrace.exp4j.UnknownFunctionException;
import de.congrace.exp4j.UnparsableExpressionException;


public class formulaSolver
{
	public static double solveFormula(String formula, int level)
	{
		Calculable calc = null;
		try {
			calc = new ExpressionBuilder(formula)
			.withVariable("level", level)
			.build();
		} catch (UnknownFunctionException e) {
			e.printStackTrace();
		} catch (UnparsableExpressionException e) {
			e.printStackTrace();
		}
		double result = calc.calculate();
		result = Math.ceil(result);
		return result;
	}
}
