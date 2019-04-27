package com.zpself.multitenancy.service.impl;

import com.zpself.multitenancy.MultiTenantDvdRentalProperties;
import com.zpself.multitenancy.service.DataSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DataSourceServiceImpl implements DataSourceService {
	
	private MultiTenantDvdRentalProperties multiTenantDvdRentalProperties;
	
	@Autowired
	public DataSourceServiceImpl(
			MultiTenantDvdRentalProperties multiTenantDvdRentalProperties
			) {
		this.multiTenantDvdRentalProperties = multiTenantDvdRentalProperties;
	}

	@Override
	public List<Map<String, Object>> findDataSource() {
		List<Map<String, Object>> data = new ArrayList<>();
		Map<String, Object> dataMap = null;
		List<MultiTenantDvdRentalProperties.DataSourceProps> dataSources = multiTenantDvdRentalProperties.getDataSourcesProps();
		for(MultiTenantDvdRentalProperties.DataSourceProps datasource : dataSources) {
			dataMap = new HashMap<>();
			dataMap.put("name", datasource.getName());
			dataMap.put("tenantId", datasource.getTenantId());
			data.add(dataMap);
		}
		return data;
	}

}
