package com.zpself.multitenancy.controller;

import com.zpself.multitenancy.service.DataSourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = {"多租户数据源接口"})
@RestController
@RequestMapping(value = "/api")
public class MultitenancyController {
	
	private final DataSourceService dataSourceService;
	
	@Autowired
	public MultitenancyController( DataSourceService dataSourceService ) {
		this.dataSourceService = dataSourceService;
	}
	
	@ApiOperation(value = "获取数据源")
    @GetMapping(value = "/datasources")
//    @SystemLog(dataType = "多租户测试接口管理", operationType = OperationType.RETRIEVE, parameters = {0})
	public List<Map<String, Object>> findDataSource() throws Exception {
		List<Map<String, Object>> data = new ArrayList<>();
		Map<String, Object> map_defult  =new HashMap<>();
		map_defult.put("name","默认");
		map_defult.put("tenantId","default");

		Map<String, Object> map_01  =new HashMap<>();
		map_01.put("name","广州");
		map_01.put("tenantId","510000");
		data.add(map_defult);
		data.add(map_01);
		/*List<Map<String, Object>> list = dataSourceService.findDataSource();
		Map<String, Object>[] arr = new HashMap[list.size()];
		list.toArray(arr);*/
		return data;
	}
}
