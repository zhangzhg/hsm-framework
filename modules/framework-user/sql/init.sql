CREATE TABLE sys_user (
	id varchar(32) not null  PRIMARY KEY,
	account varchar(50) not null,
	password varchar(50) not null,
	token varchar(32) null,
	name varchar(100) not null,
	status varchar(1) not null,
	wechat_no varchar(100) null,
	qq varchar(20) null,
	phone varchar(15) null
);

CREATE TABLE sys_role (
	id varchar(32) not null PRIMARY KEY,
	name varchar(50) not null,
	code varchar(50) not null,
	sort_num varchar(3) null,
	remark varchar(100) null,
	create_user varchar(32) null,
	create_time timestamp null
);

CREATE TABLE sys_menu (
	id varchar(32) not null PRIMARY KEY,
	parent_id varchar(32) not null,
	type varchar(10) not null,--menu or oper
	code varchar(50) null,
	name varchar(100) null,
  url varchar(200) null,
  lv varchar(2) null,
  leaf varchar(1) null,
  sort_num varchar(3) null,
  icon varchar(100) null,
  display varchar(1) null,
  remark varchar(100) null,
	create_user varchar(32) null,
	create_time timestamp null
);

CREATE TABLE sys_user_role (
	id varchar(32) not null PRIMARY KEY,
	user_id varchar(32) not null,
	role_id varchar(32) not null
);

CREATE TABLE sys_menu_role (
	id varchar(32) not null PRIMARY KEY,
	menu_id varchar(32) not null,
	role_id varchar(32) not null
);
