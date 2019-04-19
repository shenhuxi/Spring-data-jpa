package com.zpself.module.common;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.*;
import java.util.Map.Entry;


/**
 *
 * Create By qinzhw
 * 2018年4月12日下午3:37:26
 */
public class BaseController {
    public Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    public HttpServletRequest request;



}
