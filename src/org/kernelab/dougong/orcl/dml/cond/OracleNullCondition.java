package org.kernelab.dougong.orcl.dml.cond;

import org.kernelab.dougong.core.dml.Items;
import org.kernelab.dougong.core.dml.cond.LogicalCondition;
import org.kernelab.dougong.semi.dml.cond.AbstractNullCondition;

public class OracleNullCondition extends AbstractNullCondition
{
	@Override
	protected LogicalCondition provideLogicalCondition()
	{
		return new OracleLogicalCondition();
	}

	public StringBuilder toString(StringBuilder buffer)
	{
		if (this.expr instanceof Items)
		{
			buffer.append('(');
		}
		this.expr.toStringExpressed(buffer);
		if (this.expr instanceof Items)
		{
			buffer.append(')');
		}
		buffer.append(" IS");
		if (this.not)
		{
			buffer.append(" NOT");
		}
		return buffer.append(" NULL");
	}
}
