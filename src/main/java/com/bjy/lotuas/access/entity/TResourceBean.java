package com.bjy.lotuas.access.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bjy.lotuas.common.entity.BaseEntity;

@Entity
@Table(name="t_resource")
public class TResourceBean extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="resource_id")
	private Integer resourceId;
	
	@Column(name="resource_code", length=100, nullable=false)
	private String resourceCode;
	
	@Column(name="resource_name", length=100, nullable=false)
	private String resourceName;
	
	@Column(name="resource_type_id", nullable=false)
	private Integer resourceTypeId;
	
	@Column(name="resource_icon", length=100)
	private String resourceIcon;//资源图标样式
	
	@Column(name="resource_url", length=255)
	private String resourceUrl;//资源地址
	
	@Column(name="resource_order")
	private Integer resourceOrder;
	
	@Column(name="resource_level", nullable=false)
	private String resourceLevel;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="parent_id")
	private TResourceBean parentResource;

	
	@ManyToMany(cascade=CascadeType.REFRESH, mappedBy="resources")
	private List<TRoleBean> roles=new ArrayList<TRoleBean>();
	
	public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	public String getResourceCode() {
		return resourceCode;
	}

	public void setResourceCode(String resourceCode) {
		this.resourceCode = resourceCode;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public Integer getResourceTypeId() {
		return resourceTypeId;
	}

	public void setResourceTypeId(Integer resourceTypeId) {
		this.resourceTypeId = resourceTypeId;
	}

	public String getResourceIcon() {
		return resourceIcon;
	}

	public void setResourceIcon(String resourceIcon) {
		this.resourceIcon = resourceIcon;
	}

	public String getResourceUrl() {
		return resourceUrl;
	}

	public void setResourceUrl(String resourceUrl) {
		this.resourceUrl = resourceUrl;
	}

	public Integer getResourceOrder() {
		return resourceOrder;
	}

	public void setResourceOrder(Integer resourceOrder) {
		this.resourceOrder = resourceOrder;
	}

	public String getResourceLevel() {
		return resourceLevel;
	}

	public void setResourceLevel(String resourceLevel) {
		this.resourceLevel = resourceLevel;
	}

	public TResourceBean getParentResource() {
		return parentResource;
	}

	public void setParentResource(TResourceBean parentResource) {
		this.parentResource = parentResource;
	}

	public List<TRoleBean> getRoles() {
		return roles;
	}

	public void setRoles(List<TRoleBean> roles) {
		this.roles = roles;
	}

	
	
}
