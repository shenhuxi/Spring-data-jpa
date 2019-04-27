package com.zpself.multitenancy.service;

import java.util.List;
import java.util.Map;

public interface DataSourceService {
	
	List<Map<String, Object>> findDataSource();
}
