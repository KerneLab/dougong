package org.kernelab.dougong.semi.dml;

import org.kernelab.dougong.SQL;
import org.kernelab.dougong.core.dml.StringItem;

public abstract class AbstractStringItem extends AbstractSingleItem implements StringItem
{
	protected String	item;

	private boolean		order;

	@Override
	public String alias()
	{
		return null;
	}

	@Override
	public AbstractStringItem alias(String alias)
	{
		return this;
	}

	public AbstractStringItem ascend()
	{
		return ascend(true);
	}

	public AbstractStringItem ascend(boolean ascend)
	{
		this.order = ascend;
		return this;
	}

	public boolean ascending()
	{
		return order;
	}

	public AbstractStringItem descend()
	{
		return ascend(false);
	}

	public String getString()
	{
		return item;
	}

	protected AbstractStringItem replicate()
	{
		return this;
	}

	public AbstractStringItem setString(String item)
	{
		this.item = item;
		return this;
	}

	@Override
	public String toString()
	{
		return this.getString() == null ? SQL.NULL : this.getString();
	}

	public StringBuilder toString(StringBuilder buffer)
	{
		return buffer.append(this.toString());
	}

	public StringBuilder toStringAliased(StringBuilder buffer)
	{
		return toString(buffer);
	}
}
