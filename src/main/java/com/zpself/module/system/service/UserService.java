package com.zpself.module.system.service;

import com.zpself.jpa.service.BaseService;
import com.zpself.module.system.entity.User;

import java.util.List;

/**
 * 描述:
 * 作者: qinzhw
 * 创建时间: 2018/8/14 14:43
 */
public interface UserService extends BaseService<User,Long> {


	User findByUserName(String teacherName);

    
}