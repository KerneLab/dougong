package org.kernelab.dougong.orcl.dml;

import org.kernelab.dougong.core.dml.cond.ComparisonCondition;
import org.kernelab.dougong.core.dml.cond.MembershipCondition;
import org.kernelab.dougong.semi.dml.AbstractItems;

public class OracleItems extends AbstractItems
{
	@Override
	protected ComparisonCondition provideComparisonCondition()
	{
		return this.provider().provideComparisonCondition();
	}

	@Override
	protected MembershipCondition provideMembershipCondition()
	{
		return this.provider().provideMembershipCondition();
	}
}
