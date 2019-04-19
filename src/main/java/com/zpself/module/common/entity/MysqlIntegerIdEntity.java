package com.zpself.module.common.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

/**
 * 描述: Mysql数据库的主键生成定义:系统生成自增长整数型数据作为主键
 * 作者: qinzhw
 * 创建时间: 2018/7/16 15:38
 */
@MappedSuperclass
public class MysqlIntegerIdEntity extends IdEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@ApiModelProperty("ID")
	@Column(columnDefinition = "int comment 'ID主键'")
	protected Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
