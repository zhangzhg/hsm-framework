 /**
 * Description: 系统权限实体
 * Copyright:Copyright 2016 nfky.com. All rights reserved
 * @author:xk
 * @since:0.0.2
 * Create at:2016-06-14 上午 11:38:40
 *  
 * Modification History:
 * Date         Author      Version     Description
 * ------------------------------------------------------------------
 * 2016-06-14   xk       0.0.2     Initial
 */
package com.framework.user.model;

 import java.io.Serializable;
 import java.util.Date;

 /**
 * 系统权限实体<br>
 */
public class SysMenuEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	//父权限ID 每一层三位来表示如：100100100
	private String parentId;
	//权限类型 MENU:菜单 OPERATE:操作
	private String type;
	//权限编码
	private String code;
	//权限名称
	private String name;
	//连接地址
	private String url;
	//层级
	private Integer lv;
	//是否是叶子节点 1.是 0.不是
	private Boolean leaf;
	//排序号
	private Integer sortNum;
	//图标
	private String icon;
	//是否显示 1.显示 0.不显示
	private String display;
	//备注
	private String remark;
	//创建人
	private String createUser;
	//创建时间
	private Date createTime;

	 public String getId() {
		 return id;
	 }

	 public void setId(String id) {
		 this.id = id;
	 }

	 public String getParentId() {
		 return parentId;
	 }

	 public void setParentId(String parentId) {
		 this.parentId = parentId;
	 }

	 public static long getSerialVersionUID() {
		 return serialVersionUID;
	 }

	 public String getType() {
		 return type;
	 }

	 public void setType(String type) {
		 this.type = type;
	 }

	 public String getCode() {
		 return code;
	 }

	 public void setCode(String code) {
		 this.code = code;
	 }

	 public String getName() {
		 return name;
	 }

	 public void setName(String name) {
		 this.name = name;
	 }

	 public String getUrl() {
		 return url;
	 }

	 public void setUrl(String url) {
		 this.url = url;
	 }

	 public Integer getLv() {
		 return lv;
	 }

	 public void setLv(Integer lv) {
		 this.lv = lv;
	 }

	 public Boolean getLeaf() {
		 return leaf;
	 }

	 public void setLeaf(Boolean leaf) {
		 this.leaf = leaf;
	 }

	 public Integer getSortNum() {
		 return sortNum;
	 }

	 public void setSortNum(Integer sortNum) {
		 this.sortNum = sortNum;
	 }

	 public String getIcon() {
		 return icon;
	 }

	 public void setIcon(String icon) {
		 this.icon = icon;
	 }

	 public String getDisplay() {
		 return display;
	 }

	 public void setDisplay(String display) {
		 this.display = display;
	 }

	 public String getRemark() {
		 return remark;
	 }

	 public void setRemark(String remark) {
		 this.remark = remark;
	 }

	 public String getCreateUser() {
		 return createUser;
	 }

	 public void setCreateUser(String createUser) {
		 this.createUser = createUser;
	 }

	 public Date getCreateTime() {
		 return createTime;
	 }

	 public void setCreateTime(Date createTime) {
		 this.createTime = createTime;
	 }

 }
