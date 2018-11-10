package com.framework.user.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统角色实体<br>
 * 
 * @version 1.0, 2015-04-27
 */
public class SysRoleEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//columns START
	//ID
	private String id;
	//角色名
	private String name;
	//角色编码
	private String code;
	//排序号
	private Integer sortNum;
	//备注
	private String remark;
	//创建人
	private String createUser;
	//创建时间
	private Date createTime;

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return this.id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}

	public Integer getSortNum() {
		return this.sortNum;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public Date getCreateTime() {
		return this.createTime;
	}

}
