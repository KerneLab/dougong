package org.kernelab.dougong.demo;

import org.kernelab.dougong.core.meta.PrimaryKeyMeta;
import org.kernelab.dougong.core.ddl.ForeignKey;
import org.kernelab.dougong.core.meta.ForeignKeyMeta;
import org.kernelab.dougong.core.Column;
import org.kernelab.dougong.core.meta.MemberMeta;
import org.kernelab.dougong.core.meta.NameMeta;
import org.kernelab.dougong.core.meta.DataMeta;
import org.kernelab.dougong.semi.AbstractTable;

@MemberMeta()
public class STAF extends AbstractTable
{
	@ForeignKeyMeta
	public static final String	FRN_STAF	= "FRN_STAF";

	@ForeignKeyMeta
	public static final String	FK_STAF_JOB	= "FK_STAF_JOB";

	@NameMeta(name = "COMP_ID")
	@DataMeta(alias = "compId")
	@PrimaryKeyMeta(ordinal = 1)
	public Column				COMP_ID;

	@NameMeta(name = "DEPT_ID")
	@DataMeta(alias = "deptId")
	@PrimaryKeyMeta(ordinal = 2)
	public Column				DEPT_ID;

	@NameMeta(name = "STAF_ID")
	@DataMeta(alias = "stafId")
	@PrimaryKeyMeta(ordinal = 3)
	public Column				STAF_ID;

	@NameMeta(name = "STAF_NAME")
	@DataMeta(alias = "stafName")
	public Column				STAF_NAME;

	@NameMeta(name = "STAF_SALARY")
	@DataMeta(alias = "stafSalary")
	public Column				STAF_SALARY;

	@NameMeta(name = "STAF_JOB")
	@DataMeta(alias = "stafJob")
	public Column				STAF_JOB;

	@ForeignKeyMeta
	public ForeignKey FRN_STAF(DEPT ref)
	{
		return foreignKey(ref, COMP_ID, DEPT_ID);
	}

	@ForeignKeyMeta
	public ForeignKey FK_STAF_JOB(JOBS ref)
	{
		return foreignKey(ref, STAF_JOB);
	}
}
