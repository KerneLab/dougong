package org.kernelab.dougong.demo;

import java.util.Collection;

import org.kernelab.dougong.core.meta.DataMeta;
import org.kernelab.dougong.core.meta.EntityMeta;
import org.kernelab.dougong.core.meta.ManyToOneMeta;
import org.kernelab.dougong.core.meta.OneToManyMeta;

@EntityMeta(entity = DEPT.class)
public class Department
{
	@ManyToOneMeta(model = Company.class, key = DEPT.FRN_DEPT)
	private Company				company;

	@DataMeta(alias = "compId", serialize = false)
	private String				compId;

	private String				compName;

	@DataMeta(alias = "deptId")
	private String				id;

	@DataMeta(alias = "deptName")
	private String				name;

	@OneToManyMeta(model = Staff.class, key = STAF.FRN_STAF, referred = false)
	private Collection<Staff>	staffs;

	public Company getCompany()
	{
		return company;
	}

	public String getCompId()
	{
		return this.getCompany() != null ? this.getCompany().getId() : this.compId;
	}

	public String getCompName()
	{
		return this.getCompany() != null ? this.getCompany().getName() : compName;
	}

	public String getId()
	{
		return id;
	}

	public String getName()
	{
		return name;
	}

	public Collection<Staff> getStaffs()
	{
		return staffs;
	}

	public void setCompany(Company company)
	{
		this.company = company;
	}

	public void setCompId(String compId)
	{
		this.compId = compId;
	}

	public void setCompName(String compName)
	{
		this.compName = compName;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setStaffs(Collection<Staff> staffs)
	{
		this.staffs = staffs;
	}

	public String toString()
	{
		String str = "Depart: id=" + this.getId() + ", name=" + this.getName() + ", compId=" + this.getCompId()
				+ ", compName=" + this.getCompName();

		if (this.getStaffs() != null)
		{
			str += "\n  [Staffs]";
			for (Staff s : this.getStaffs())
			{
				str += "\n    " + s.toString();
			}
		}

		return str;
	}
}
