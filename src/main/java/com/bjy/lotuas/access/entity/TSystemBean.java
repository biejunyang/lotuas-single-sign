package com.bjy.lotuas.access.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.bjy.lotuas.common.entity.BaseEntity;

@Entity
@Table(name="t_system")
public class TSystemBean extends BaseEntity{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="system_id")
	private Integer systemId;//
	
	@Column(name="system_name", nullable=false, length=50)
	private String systemName;
	

	@Column(name="system_url", nullable=false, length=100)
	private String systemUrl;


	@Column(name="system_icon")
	private String systemIcon;

	
	public Integer getSystemId() {
		return systemId;
	}


	public void setSystemId(Integer systemId) {
		this.systemId = systemId;
	}


	public String getSystemName() {
		return systemName;
	}


	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}


	public String getSystemUrl() {
		return systemUrl;
	}


	public void setSystemUrl(String systemUrl) {
		this.systemUrl = systemUrl;
	}


	public String getSystemIcon() {
		return systemIcon;
	}


	public void setSystemIcon(String systemIcon) {
		this.systemIcon = systemIcon;
	}


	
}
