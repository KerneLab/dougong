package org.kernelab.dougong.core.dml.cond;

import org.kernelab.dougong.core.dml.Select;

public interface ExistsCondition extends ComposableCondition, NegatableCondition, UnaryCondition
{
	public ExistsCondition exists(Select select);
}
