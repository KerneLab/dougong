package org.kernelab.dougong.semi.ddl;

import java.util.Map;

import org.kernelab.dougong.core.Column;
import org.kernelab.dougong.core.Entity;
import org.kernelab.dougong.core.ddl.AbsoluteKey;
import org.kernelab.dougong.core.dml.Condition;

public class AbstractAbsoluteKey extends AbstractKey implements AbsoluteKey
{
	public AbstractAbsoluteKey(Entity entity, Column... columns)
	{
		super(entity, columns);
	}

	public <T> Map<Column, Object> mapValues(T object)
	{
		return mapObjectValuesOfColumns(object, this.columns());
	}

	public Condition queryCondition()
	{
		return queryCondition(columns());
	}
}