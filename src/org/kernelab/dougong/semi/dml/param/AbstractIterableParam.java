package org.kernelab.dougong.semi.dml.param;

import org.kernelab.basis.Tools;
import org.kernelab.dougong.core.dml.param.IterableParam;

public class AbstractIterableParam extends AbstractParam<Iterable<?>> implements IterableParam
{
	public AbstractIterableParam(String name, Iterable<?> value)
	{
		super(name, value);
	}

	@Override
	public boolean given()
	{
		return super.given() && !Tools.isEmpty(this.value());
	}

	@Override
	protected AbstractParam<Iterable<?>> replicate()
	{
		return this.provider().provideProvider(new AbstractIterableParam(name(), value()));
	}
}
