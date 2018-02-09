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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.bjy.lotuas.common.entity.BaseEntity;

@Entity
@Table(name="t_role")
public class TRoleBean extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="role_id")
	private Integer roleId;
	
	
	@Column(name="role_name", nullable=false, length=100)
	private String roleName;
	
	@Column(name="role_desc")
	private String roleDesc;
	
	
	@ManyToMany(mappedBy="roles", cascade=CascadeType.REFRESH)
	private List<TUserBean> users=new ArrayList<TUserBean>();

	@ManyToMany(cascade=CascadeType.REFRESH,targetEntity=TResourceBean.class)
	@JoinTable(name="t_role_resource",joinColumns=@JoinColumn(name="role_id"), inverseJoinColumns=@JoinColumn(name="resource_id"))
	private List<TResourceBean> resources=new ArrayList<TResourceBean>();
	

	public Integer getRoleId() {
		return roleId;
	}


	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}


	public String getRoleName() {
		return roleName;
	}


	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}


	public String getRoleDesc() {
		return roleDesc;
	}


	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}


	public List<TUserBean> getUsers() {
		return users;
	}


	public void setUsers(List<TUserBean> users) {
		this.users = users;
	}


	public List<TResourceBean> getResources() {
		return resources;
	}


	public void setResources(List<TResourceBean> resources) {
		this.resources = resources;
	}

	
	
}
