package org.kernelab.dougong.orcl;

import org.kernelab.basis.Tools;
import org.kernelab.dougong.core.Column;
import org.kernelab.dougong.core.Expression;
import org.kernelab.dougong.core.Table;
import org.kernelab.dougong.core.Utils;
import org.kernelab.dougong.core.View;
import org.kernelab.dougong.core.dml.Function;
import org.kernelab.dougong.core.dml.Join;
import org.kernelab.dougong.orcl.dml.OracleColumn;
import org.kernelab.dougong.orcl.dml.OracleDelete;
import org.kernelab.dougong.orcl.dml.OracleJoin;
import org.kernelab.dougong.orcl.dml.OracleListItem;
import org.kernelab.dougong.orcl.dml.OracleSelect;
import org.kernelab.dougong.orcl.dml.OracleStringItem;
import org.kernelab.dougong.orcl.dml.OracleUpdate;
import org.kernelab.dougong.orcl.dml.cond.OracleComparisonCondition;
import org.kernelab.dougong.orcl.dml.cond.OracleLikeCondition;
import org.kernelab.dougong.orcl.dml.cond.OracleMembershipCondition;
import org.kernelab.dougong.orcl.dml.cond.OracleNullCondition;
import org.kernelab.dougong.orcl.dml.cond.OracleRangeCondition;
import org.kernelab.dougong.semi.AbstractProvider;

public class OracleProvider extends AbstractProvider
{
	public static void main(String[] args)
	{
		// SQL q = new SQL(new OracleProvider());
	}

	public String provideAliasLabel(String alias)
	{
		return Tools.notNullOrWhite(alias) ? "\"" + alias + "\"" : null;
	}

	public Column provideColumn(View view, String name)
	{
		return new OracleColumn(view, name);
	}

	public OracleComparisonCondition provideComparisonCondition()
	{
		return new OracleComparisonCondition();
	}

	public OracleDelete provideDelete()
	{
		return new OracleDelete().provider(this);
	}

	public String provideFunctionText(Function function)
	{
		StringBuilder buffer = new StringBuilder();

		if (function.usingSchema())
		{
			buffer.append(Utils.getSchemaFromPackage(function.getClass()));
			buffer.append('.');
		}

		buffer.append(Utils.getNameFromClass(function.getClass()));

		Expression[] args = function.args();

		if (args != null && args.length > 0)
		{
			buffer.append('(');

			boolean first = true;

			for (Expression expr : args)
			{
				if (first)
				{
					first = false;
				}
				else
				{
					buffer.append(',');
				}
				Utils.text(buffer, expr);
			}

			buffer.append(')');
		}

		return buffer.toString();
	}

	public Join provideJoin()
	{
		return new OracleJoin();
	}

	public OracleLikeCondition provideLikeCondition()
	{
		return new OracleLikeCondition();
	}

	public OracleListItem provideListItem()
	{
		return new OracleListItem(this);
	}

	public OracleMembershipCondition provideMembershipCondition()
	{
		return new OracleMembershipCondition();
	}

	public OracleNullCondition provideNullCondition()
	{
		return new OracleNullCondition();
	}

	public OracleRangeCondition provideRangeCondition()
	{
		return new OracleRangeCondition();
	}

	public OracleSelect provideSelect()
	{
		return new OracleSelect().provider(this);
	}

	public OracleStringItem provideStringItem(String item)
	{
		return (OracleStringItem) new OracleStringItem(this).setString(item);
	}

	public String provideTableName(Table table)
	{
		StringBuilder buffer = new StringBuilder(40);
		if (table.usingSchema())
		{
			buffer.append(Utils.getSchemaFromPackage(table.getClass()));
			buffer.append('.');
		}
		buffer.append(Utils.getNameFromClass(table.getClass()));
		return buffer.toString();
	}

	public OracleUpdate provideUpdate()
	{
		return new OracleUpdate().provider(this);
	}
}
