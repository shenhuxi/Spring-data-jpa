package com.zpself.module.common.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * 描述: Mysql数据库的主键生成定义:系统自动生成32位不同的字符序列
 * 作者: qinzhw
 * 创建时间: 2018/7/16 15:42
 */
@MappedSuperclass
public class MysqlUUIdEntity extends IdEntity {

	@Id
	@Column(length = 32, nullable = true,columnDefinition = "varchar(32) comment 'ID主键'")
	@GenericGenerator(name = "sys_uuid", strategy = "uuid")
	@GeneratedValue(generator = "sys_uuid")

	protected String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}