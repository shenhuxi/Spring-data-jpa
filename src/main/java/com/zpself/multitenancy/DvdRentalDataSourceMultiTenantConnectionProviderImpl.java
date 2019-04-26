package com.zpself.multitenancy;

import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Map;

public class DvdRentalDataSourceMultiTenantConnectionProviderImpl extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl {

	private static final long serialVersionUID = -1019866243937732505L;
	
	@Resource(name = "dataSourcesDvdRental")
	private Map<String, DataSource> dataSourcesDvdRental;

	@Override
	protected DataSource selectAnyDataSource() {
		return this.dataSourcesDvdRental.values().iterator().next();
	}

	@Override
	protected DataSource selectDataSource(String tenantIdentifier) {
		return this.dataSourcesDvdRental.get(tenantIdentifier);
	}
}
