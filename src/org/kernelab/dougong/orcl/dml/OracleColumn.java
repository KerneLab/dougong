package org.kernelab.dougong.orcl.dml;

import org.kernelab.dougong.core.View;
import org.kernelab.dougong.semi.dml.AbstractColumn;

public class OracleColumn extends AbstractColumn implements OracleSortable
{
	private byte nulls = OracleSortable.NULLS_NORMAL;

	public OracleColumn(View view, String name)
	{
		super(view, name);
	}

	public byte getNullsPosition()
	{
		return nulls;
	}

	public OracleSortable nullsFirst()
	{
		this.nulls = OracleSortable.NULLS_FIRST;
		return this;
	}

	public OracleSortable nullsLast()
	{
		this.nulls = OracleSortable.NULLS_LAST;
		return this;
	}

	@Override
	protected OracleColumn replicate()
	{
		return new OracleColumn(view(), name());
	}

	public StringBuilder toString(StringBuilder buffer)
	{
		String alias = this.view().provider().provideAliasLabel(view().alias());
		if (alias != null && !isUsingByJoin())
		{
			buffer.append(alias);
			buffer.append('.');
		}
		this.view().provider().provideOutputNameText(buffer, name());
		return buffer;
	}
}
