package org.kernelab.dougong.semi.dml.opr;

import org.kernelab.dougong.core.dml.opr.Result;
import org.kernelab.dougong.core.util.Utils;
import org.kernelab.dougong.semi.dml.AbstractItem;

public abstract class AbstractResult extends AbstractItem implements Result
{
	@Override
	protected abstract AbstractResult replicate();

	public StringBuilder toStringExpressed(StringBuilder buffer)
	{
		return toString(buffer);
	}

	public StringBuilder toStringSelected(StringBuilder buffer)
	{
		return Utils.outputAlias(this.provider(), this.toStringExpressed(buffer), this);
	}
}
