package org.kernelab.dougong.orcl.dml;

import org.kernelab.dougong.core.Provider;
import org.kernelab.dougong.core.dml.Expression;
import org.kernelab.dougong.orcl.OracleProvider;
import org.kernelab.dougong.semi.dml.AbstractDelete;

public class OracleDelete extends AbstractDelete
{
	private Expression[] returning;

	@Override
	public OracleDelete provider(Provider provider)
	{
		super.provider(provider);
		return this;
	}

	public Expression[] returning()
	{
		return returning;
	}

	public OracleDelete returning(Expression... exprs)
	{
		this.returning = exprs != null && exprs.length > 0 ? exprs : null;
		return this;
	}

	@Override
	public StringBuilder toString(StringBuilder buffer)
	{
		super.toString(buffer);

		if (returning() != null)
		{
			this.provider().to(OracleProvider.class).provideOutputReturningClause(buffer, returning());
		}

		return buffer;
	}
}
