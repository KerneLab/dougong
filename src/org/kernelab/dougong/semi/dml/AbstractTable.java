package org.kernelab.dougong.semi.dml;

import org.kernelab.dougong.core.Provider;
import org.kernelab.dougong.core.Table;
import org.kernelab.dougong.core.Utils;

public abstract class AbstractTable extends AbstractView implements Table
{
	private String	schema	= null;

	private String	name;

	public AbstractTable()
	{
		super();
	}

	@Override
	public AbstractTable alias(String alias)
	{
		super.alias(alias);
		return this;
	}

	@SuppressWarnings("unchecked")
	public <T extends Table> T as(String alias)
	{
		AbstractTable table = this.replicate();
		if (table != null)
		{
			table.alias(alias);
		}
		return (T) table;
	}

	protected void initTable()
	{
		this.schema(Utils.getSchemaFromMember(this));
		this.name(Utils.getNameFromNamed(this));
	}

	public String name()
	{
		return name;
	}

	protected AbstractTable name(String name)
	{
		this.name = name;
		return this;
	}

	@Override
	public AbstractTable provider(Provider provider)
	{
		super.provider(provider);
		this.initTable();
		return this;
	}

	protected AbstractTable replicate()
	{
		AbstractTable table = null;
		try
		{
			table = this.getClass().newInstance() //
					.schema(this.schema()) //
					.name(this.name()) //
					.provider(this.provider());
		}
		catch (Exception e)
		{
		}
		return table;
	}

	public String schema()
	{
		return schema;
	}

	protected AbstractTable schema(String schema)
	{
		this.schema = schema;
		return this;
	}

	public StringBuilder toString(StringBuilder buffer)
	{
		return buffer.append(provider().provideTableName(this));
	}

	public StringBuilder toStringAliased(StringBuilder buffer)
	{
		return buffer.append(provider().provideTableNameAliased(this));
	}
}
