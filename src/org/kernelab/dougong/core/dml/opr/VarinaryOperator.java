package org.kernelab.dougong.core.dml.opr;

import org.kernelab.dougong.core.dml.Expression;

public interface VarinaryOperator
{
	public Result operate(Expression... operands);
}
