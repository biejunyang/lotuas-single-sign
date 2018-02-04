package com.bjy.lotuas.common.easyui;

import java.util.List;
import java.util.Map;

public class EasyuiTreeNode implements java.io.Serializable, Comparable<Object>
{

	private static final long serialVersionUID = -1011484918096504327L;
	private Integer id;
	private String text;// 树节点名称
	private String iconCls = "icon-blank";// 前面的小图标样式
	private Boolean checked = false;// 是否勾选状态
	private Map<String, Object> attributes;// 其他参数
	private String state = "open";// 是否展开(open,closed)
	private List<EasyuiTreeNode> children;// 子节点
	
	private Boolean selected = false;// 是否选中(combobox,select等)
	private Integer parentId;
	private Integer order;
	private String childNodeText;
	private String level;
	private String parentLevel;//父节点level
	
	private String code;// 树节点编码

	public EasyuiTreeNode()
	{
		super();
	}

	public EasyuiTreeNode(Integer id, String text){
		this.id=id;
		this.text=text;
	}
	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	public String getIconCls()
	{
		return iconCls;
	}

	public void setIconCls(String iconCls)
	{
		this.iconCls = iconCls;
	}

	public Boolean getChecked()
	{
		return checked;
	}

	public void setChecked(Boolean checked)
	{
		this.checked = checked;
	}

	public Map<String, Object> getAttributes()
	{
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes)
	{
		this.attributes = attributes;
	}

	public String getState()
	{
		return state;
	}

	public void setState(String state)
	{
		this.state = state;
	}

	public List<EasyuiTreeNode> getChildren()
	{
		return this.children;
	}

	public void setChildren(List<EasyuiTreeNode> children)
	{
		this.children = children;
	}

	public Integer getParentId()
	{
		return parentId;
	}

	public void setParentId(Integer parentId)
	{
		this.parentId = parentId;
	}

	public Integer getOrder()
	{
		return order;
	}

	public void setOrder(Integer order)
	{
		this.order = order;
	}

	public Boolean getSelected()
	{
		return selected;
	}

	public void setSelected(Boolean selected)
	{
		this.selected = selected;
	}

	public String getChildNodeText()
	{
		return childNodeText;
	}

	public void setChildNodeText(String childNodeText)
	{
		this.childNodeText = childNodeText;
	}

	@Override
	public int compareTo(Object o)
	{
		EasyuiTreeNode src = (EasyuiTreeNode) o;
		return this.getOrder().compareTo(src.getOrder());
	}

	public String getLevel()
	{
		return level;
	}

	public void setLevel(String level)
	{
		this.level = level;
	}

	public String getParentLevel()
	{
		return parentLevel;
	}

	public void setParentLevel(String parentLevel)
	{
		this.parentLevel = parentLevel;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	
}
