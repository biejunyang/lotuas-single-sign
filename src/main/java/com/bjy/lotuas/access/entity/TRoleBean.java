package com.bjy.lotuas.access.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	
	
	@ManyToMany(mappedBy="roles")
	private List<TUserBean> users=new ArrayList<TUserBean>();


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

	
	
}
