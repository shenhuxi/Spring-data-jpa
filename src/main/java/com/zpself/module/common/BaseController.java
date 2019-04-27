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
 * Create By qinzhw
 * 2018年4月12日下午3:37:26
 */
public class BaseController {
    public Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    public HttpServletRequest request;

    public Map<String, Object> getParameterMap() {
        Map<String, String[]> properties = request.getParameterMap();
        Map<String, Object> returnMap = new HashMap<>();
        Iterator<Map.Entry<String, String[]>> entries = properties.entrySet().iterator();
        Map.Entry<String, String[]> entry;
        String name = "";
        Object value = "";
        while (entries.hasNext()) {
            entry = entries.next();
            name = entry.getKey();
            Object valueObj = entry.getValue();
            if (null == valueObj) {
                value = "";
                continue; /*值为空时 忽略查询字段 add by chaihu*/
            } else if (valueObj instanceof String[]) {
                String valueStr = "";
                String[] values = (String[]) valueObj;
                for (int i = 0; i < values.length; i++) {
                    valueStr = values[i] + ",";
                }
                value = valueStr.substring(0, valueStr.length() - 1);
            } else {
                value = valueObj;
            }
            returnMap.put(name, value);
        }
        return returnMap;
    }

    /**
     * 封装分页信息,支持多条件排序
     * "sort":["type","id"],
     * "sortType":["DESC","ASC"],
     */
    public PageRequest buildPageRequest(Map<String, Object> searchParams) {
        String pageNum = (String) searchParams.get(CommonConstant.PAGE_PAGENUM);
        String pageSize = (String) searchParams.get(CommonConstant.PAGE_PAGESIZE);
        Object o = searchParams.get(CommonConstant.PAGE_SORTTYPE);
        Object o1 = searchParams.get(CommonConstant.PAGE_SORT);
        String[] sortType = null;
        String[] sort = null;

        if (o instanceof ArrayList) {
            ArrayList arrayList = (ArrayList) searchParams.get(CommonConstant.PAGE_SORTTYPE);
            sortType = new String[arrayList.size()];
            for (int i = 0; i < arrayList.size(); i++) {
                sortType[i] = arrayList.get(i).toString();
            }
        } else if (o instanceof String) {
            sortType = new String[]{(String) searchParams.get(CommonConstant.PAGE_SORTTYPE)};
        }
        if (o1 instanceof ArrayList) {
            ArrayList arrayList = (ArrayList) searchParams.get(CommonConstant.PAGE_SORT);
            sort = new String[arrayList.size()];
            for (int i = 0; i < arrayList.size(); i++) {
                sort[i] = arrayList.get(i).toString();
            }
        } else if (o1 instanceof String) {
            sort = new String[]{(String) searchParams.get(CommonConstant.PAGE_SORT)};
        }

        if (StringUtils.isBlank(pageNum)) {
            pageNum = CommonConstant.PAGE_1;
        }
        if (StringUtils.isBlank(pageSize)) {
            pageSize = CommonConstant.PAGE_10;
        }
        searchParams.remove(CommonConstant.PAGE_PAGENUM);
        searchParams.remove(CommonConstant.PAGE_PAGESIZE);
        searchParams.remove(CommonConstant.PAGE_SORTTYPE);
        searchParams.remove(CommonConstant.PAGE_SORT);
        return buildPageRequest(Integer.parseInt(pageNum), Integer.parseInt(pageSize), sort, sortType);
    }

    /**
     * 封装分页信息
     *
     * @param pageNumber
     * @param pagzSize
     * @param sort
     * @param sortType
     * @return
     */
    public PageRequest buildPageRequest(int pageNumber, int pagzSize, String[] sort, String[] sortType) {
        Sort s;
        if (sort != null && sort.length != 0) {
            int length = sort.length;
            List<Sort.Order> orders = new ArrayList<Sort.Order>(length);
            if (sortType == null || sortType.length == 0 || sortType.length != length) {
                sortType = new String[length];
                for (int i = 0; i < length; i++) {
                    sortType[i] = "ASC";
                }
            }
            for (int i = 0; i < length; i++) {
                if (StringUtils.isBlank(sort[i])) {
                    continue;
                }
                Sort.Order order = new Sort.Order(Sort.Direction.fromString(sortType[i]), sort[i]);
                orders.add(order);
            }
            s = new Sort(orders);
        } else {
            s = new Sort(new Sort.Order(Sort.Direction.ASC, "id"));
        }

        return new PageRequest(pageNumber - 1, pagzSize, s);
    }
}
