package org.kernelab.dougong.semi.dml.opr;

import org.kernelab.dougong.core.Providable;
import org.kernelab.dougong.core.Provider;

public class AbstractOperator implements Providable
{
	private Provider	provider;

	public Provider provider()
	{
		return provider;
	}

	public AbstractOperator provider(Provider provider)
	{
		this.provider = provider;
		return this;
	}
}
