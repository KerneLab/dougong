package org.kernelab.dougong.core.dml.opr;

import org.kernelab.dougong.core.dml.Expression;

public interface BinaryOperator
{
	public Result operate(Expression operand1, Expression operand2);
}
