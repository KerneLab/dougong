package org.kernelab.dougong.semi;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.kernelab.basis.Tools;
import org.kernelab.dougong.SQL;
import org.kernelab.dougong.core.Column;
import org.kernelab.dougong.core.Entity;
import org.kernelab.dougong.core.Provider;
import org.kernelab.dougong.core.ddl.AbsoluteKey;
import org.kernelab.dougong.core.ddl.ForeignKey;
import org.kernelab.dougong.core.ddl.Key;
import org.kernelab.dougong.core.ddl.PrimaryKey;
import org.kernelab.dougong.core.dml.Expression;
import org.kernelab.dougong.core.meta.AbsoluteKeyMeta;
import org.kernelab.dougong.core.meta.ForeignKeyMeta;
import org.kernelab.dougong.core.meta.PrimaryKeyMeta;
import org.kernelab.dougong.core.util.Utils;

public abstract class AbstractEntity extends AbstractView implements Entity
{
	public static ForeignKey findForeignKey(Entity entity, String foreignKey, Entity reference)
	{
		try
		{
			return (ForeignKey) entity.getClass().getDeclaredMethod(foreignKey, reference.getClass()).invoke(entity,
					reference);
		}
		catch (Exception e)
		{
			return null;
		}
	}

	public static Column getColumn(Entity entity, Field field)
			throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchFieldException
	{
		return Tools.access(entity, field);
	}

	public static List<Field> getColumnFields(Entity entity)
	{
		Map<String, Field> fields = new LinkedHashMap<String, Field>();

		for (Field field : entity.getClass().getDeclaredFields())
		{
			if (isColumn(field))
			{
				fields.put(field.getName(), field);
			}
		}

		for (Field field : entity.getClass().getFields())
		{
			if (!fields.containsKey(field.getName()) && isColumn(field))
			{
				fields.put(field.getName(), field);
			}
		}

		return new LinkedList<Field>(fields.values());
	}

	public static Set<Column> getColumns(Entity entity)
	{
		Set<Column> columns = new LinkedHashSet<Column>();

		for (Field field : getColumnFields(entity))
		{
			try
			{
				columns.add(getColumn(entity, field));
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		return columns;
	}

	public static Column[] getColumnsArray(Entity entity)
	{
		Set<Column> columns = getColumns(entity);
		return columns.toArray(new Column[columns.size()]);
	}

	public static boolean isColumn(Field field)
	{
		int mod = field.getModifiers();
		try
		{
			if (!Modifier.isStatic(mod) //
					&& !Modifier.isFinal(mod) //
					&& Tools.isSubClass(field.getType(), Column.class) //
					&& (Modifier.isPublic(mod)
							|| (Tools.accessor(field) != null && Tools.accessor(field, null) != null)))
			{
				return true;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	public static boolean isForeignKey(Method method)
	{
		int mod = method.getModifiers();
		try
		{
			if (Modifier.isPublic(mod) //
					&& !Modifier.isStatic(mod) //
					&& method.getAnnotation(ForeignKeyMeta.class) != null //
					&& method.getParameterTypes().length == 1 //
					&& Tools.isSubClass(method.getReturnType(), ForeignKey.class)) //
			{
				return true;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	public static boolean isForeignKey(Method method, Entity entity)
	{
		if (isForeignKey(method))
		{
			return method.getParameterTypes()[0].isInstance(entity);
		}
		else
		{
			return false;
		}
	}

	public AbsoluteKey absoluteKey()
	{
		Column column = null;

		for (Field field : this.getColumnFields())
		{
			if (field.getAnnotation(AbsoluteKeyMeta.class) != null)
			{
				column = this.getColumnByField(field);
				break;
			}
		}

		return column == null ? null : provider().provideAbsoluteKey(this, column);
	}

	protected ForeignKey foreignKey(Entity ref, Column... columns)
	{
		PrimaryKey pk = ref.primaryKey();

		if (pk == null)
		{
			return null;
		}
		else
		{
			return this.provider().provideForeignKey(pk, this, columns);
		}
	}

	public ForeignKey foreignKey(String name, Entity reference)
	{
		return findForeignKey(this, name, reference);
	}

	protected Column getColumnByField(Field field)
	{
		String name = Utils.getNameFromField(field);

		Column column = Tools.as(referItems().get(name), Column.class);

		if (column == null)
		{
			column = provider().provideColumn(this, name, field);
		}

		return column;
	}

	/**
	 * Get the default expressions for each columns in this entity.<br />
	 * If no default expression specified for some columns then these columns'
	 * parameter will be used as the default expression.<br />
	 * If the key parameter is given then the columns in the key will be
	 * excluded in the result. The column of AbsoluteKey will also be excluded.
	 * Which is useful while generating UPDATE columns' list and excluding these
	 * key columns.
	 * 
	 * @param key
	 * @return
	 */
	public Map<Column, Expression> getColumnDefaultExpressions(Key key)
	{
		Map<Column, Expression> meta = new LinkedHashMap<Column, Expression>();

		SQL sql = this.provider().provideSQL();

		Set<String> keys = new HashSet<String>();

		AbsoluteKey abs = null;
		if (key instanceof AbsoluteKey)
		{
			abs = (AbsoluteKey) key;
			key = null;
		}
		else
		{
			abs = this.absoluteKey();
		}

		if (abs != null)
		{
			keys.add(abs.columns()[0].name());
		}

		if (key != null)
		{
			for (Column k : key.columns())
			{
				keys.add(k.name());
			}
		}

		for (Field field : this.getColumnFields())
		{
			if (!keys.contains(Utils.getNameFromField(field)))
			{
				Expression value = Utils.getDataExpressionFromField(sql, field);

				if (value != null)
				{
					meta.put(this.getColumnByField(field), value);
				}
			}
		}

		return meta;
	}

	protected List<Field> getColumnFields()
	{
		return getColumnFields(this);
	}

	protected void initColumns()
	{
		Column col = null;

		for (Field field : this.getColumnFields())
		{
			try
			{
				if (getColumn(this, field) == null)
				{
					col = this.getColumnByField(field);
					Tools.access(this, field, col);
					items().add(col);
					referItems().put(col.name(), col);
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	public PrimaryKey primaryKey()
	{
		TreeMap<Integer, Column> keys = new TreeMap<Integer, Column>();

		for (Field field : this.getColumnFields())
		{
			PrimaryKeyMeta meta = field.getAnnotation(PrimaryKeyMeta.class);
			if (meta != null)
			{
				keys.put(meta.ordinal(), this.getColumnByField(field));
			}
		}

		if (keys.isEmpty())
		{
			return null;
		}
		else
		{
			return provider().providePrimaryKey(this, keys.values().toArray(new Column[keys.size()]));
		}
	}

	@Override
	public AbstractView provider(Provider provider)
	{
		super.provider(provider);
		this.initColumns();
		return this;
	}
}
