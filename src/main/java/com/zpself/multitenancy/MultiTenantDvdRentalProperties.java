package com.zpself.multitenancy;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "spring.datasource.multitenancy.dvdrental")
public class MultiTenantDvdRentalProperties {

	private List<DataSourceProps> dataSourcesProps;

	public static class DataSourceProps extends org.springframework.boot.autoconfigure.jdbc.DataSourceProperties {

		private String tenantId;

		public String getTenantId() {
			return tenantId;
		}

		public void setTenantId(String tenantId) {
			this.tenantId = tenantId;
		}
	}

	public List<DataSourceProps> getDataSourcesProps() {
		return dataSourcesProps;
	}

	public void setDataSourcesProps(List<DataSourceProps> dataSourcesProps) {
		this.dataSourcesProps = dataSourcesProps;
	}
}
