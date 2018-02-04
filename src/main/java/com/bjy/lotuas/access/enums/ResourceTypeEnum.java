package com.bjy.lotuas.access.enums;

public enum ResourceTypeEnum
{
	MODULE("模块"), MENU("菜单"), BUTTON("按钮"), URL("冰箱"), FIELD("字段");
	private String desc;
	private ResourceTypeEnum(String desc)
	{
		this.desc=desc;
	}
	public String getDesc()
	{
		return desc;
	}
	
}
