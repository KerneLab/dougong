package org.kernelab.dougong.semi.dml;

import org.kernelab.dougong.core.Column;
import org.kernelab.dougong.core.View;
import org.kernelab.dougong.core.dml.cond.ComparisonCondition;
import org.kernelab.dougong.core.dml.cond.LikeCondition;
import org.kernelab.dougong.core.dml.cond.MembershipCondition;
import org.kernelab.dougong.core.dml.cond.NullCondition;
import org.kernelab.dougong.core.dml.cond.RangeCondition;

public abstract class AbstractColumn extends AbstractSingleItem implements Column
{
	private View	view;

	private String	name;

	private String	alias;

	private boolean	order;

	public AbstractColumn(View view, String name)
	{
		this.view = view;
		this.name = name;
		this.alias = null;
		this.order = true;
	}

	@Override
	public String alias()
	{
		return alias;
	}

	/**
	 * Return this Column with the given alias name.
	 * 
	 * @param alias
	 * @return
	 */
	@Override
	public AbstractColumn alias(String alias)
	{
		this.alias = alias;
		return this;
	}

	public AbstractColumn as(String alias)
	{
		AbstractColumn column = this.copy();
		column.alias(alias);
		return column;
	}

	public AbstractColumn ascend()
	{
		order = true;
		return this;
	}

	public boolean ascending()
	{
		return order;
	}

	protected abstract AbstractColumn copy();

	public AbstractColumn descend()
	{
		order = false;
		return this;
	}

	public String name()
	{
		return name;
	}

	@Override
	protected ComparisonCondition provideComparisonCondition()
	{
		return view.provider().provideComparisonCondition();
	}

	@Override
	protected LikeCondition provideLikeCondition()
	{
		return view.provider().provideLikeCondition();
	}

	@Override
	protected MembershipCondition provideMembershipCondition()
	{
		return view.provider().provideMembershipCondition();
	}

	@Override
	protected NullCondition provideNullCondition()
	{
		return view.provider().provideNullCondition();
	}

	@Override
	protected RangeCondition provideRangeCondition()
	{
		return view.provider().provideRangeCondition();
	}

	protected abstract AbstractColumn replicate();

	public View view()
	{
		return view;
	}
}
