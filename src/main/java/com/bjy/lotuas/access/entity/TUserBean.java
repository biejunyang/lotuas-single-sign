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


/**
 * custom define spring security user
 * @author biejunyang
 *
 */
@Entity
@Table(name="t_user")
public class TUserBean extends BaseEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="user_id")
	private Integer userId;
	
	@Column(name="username", length=100, unique=true, nullable=false)
	private String username;
	
	@Column(name="password", length=100, nullable=false)
	private String password;
	
	@Column(name="email")
	private String email;
	
	@Column(name="real_name")
	private String realName;
	
	@Column(name="phone")
	private String phone;
	
	@Column(name="address")
	private String address;
	

	@ManyToMany(cascade=CascadeType.REFRESH,targetEntity=TRoleBean.class)
	@JoinTable(name="t_user_role",joinColumns=@JoinColumn(name="user_id"), inverseJoinColumns=@JoinColumn(name="role_id"))
	private List<TRoleBean> roles=new ArrayList<TRoleBean>();
	
	public TUserBean() {
		
		// TODO Auto-generated constructor stub
	}


	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<TRoleBean> getRoles() {
		return roles;
	}

	public void setRoles(List<TRoleBean> roles) {
		this.roles = roles;
	}
	
	
	
}
