package com.zpself.multitenancy;

import org.hibernate.MultiTenancyStrategy;
import org.hibernate.cfg.Environment;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 
 * @author wq
 *
 */
@Configuration
@EnableConfigurationProperties({ MultiTenantDvdRentalProperties.class, JpaProperties.class })
public class MultiTenantJpaConfiguration {

	private JpaProperties jpaProperties;

	private MultiTenantDvdRentalProperties multiTenantDvdRentalProperties;
	
	private Map<String, DataSource> result;


	// 构造器注入参数
	@Autowired
	public MultiTenantJpaConfiguration(JpaProperties jpaProperties,
                                       MultiTenantDvdRentalProperties multiTenantDvdRentalProperties) {
		this.jpaProperties = jpaProperties;
		this.multiTenantDvdRentalProperties = multiTenantDvdRentalProperties;
	}

	@Bean(name = "dataSourcesDvdRental")
	public Map<String, DataSource> dataSourcesDvdRental() {
		this.result = new HashMap<>();
		for (MultiTenantDvdRentalProperties.DataSourceProps dsProperties : this.multiTenantDvdRentalProperties.getDataSourcesProps()) {
			DataSourceBuilder factory = DataSourceBuilder.create().url(dsProperties.getUrl())
					.username(dsProperties.getUsername()).password(dsProperties.getPassword())
					.driverClassName(dsProperties.getDriverClassName());
			this.result.put(dsProperties.getTenantId(), factory.build());
		}
		return this.result;
	}
	
	@Bean
	public MultiTenantConnectionProvider multiTenantConnectionProvider() {
		return new DvdRentalDataSourceMultiTenantConnectionProviderImpl();
	}

	@Bean
	public CurrentTenantIdentifierResolver currentTenantIdentifierResolver() {
		return new TenantDvdRentalIdentifierResolverImpl();
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(
			MultiTenantConnectionProvider multiTenantConnectionProvider,
			CurrentTenantIdentifierResolver currentTenantIdentifierResolver) {

		Map<String, Object> hibernateProps = new LinkedHashMap<>();
		hibernateProps.putAll(this.jpaProperties.getProperties());
		hibernateProps.put(Environment.MULTI_TENANT, MultiTenancyStrategy.DATABASE);
		hibernateProps.put(Environment.MULTI_TENANT_CONNECTION_PROVIDER, multiTenantConnectionProvider);
		hibernateProps.put(Environment.MULTI_TENANT_IDENTIFIER_RESOLVER, currentTenantIdentifierResolver);
		hibernateProps.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		hibernateProps.put("hibernate.id.new_generator_mappings","false");
		hibernateProps.put("hibernate.format_sql", "true");
		hibernateProps.put("hibernate.max_fetch_depth", "1");
		hibernateProps.put("hibernate.generate_statistics", "false");
		hibernateProps.put("hibernate.use_sql_comments", "true");
		hibernateProps.put("connection.autoReconnect", "true");//处理连接超时
		hibernateProps.put("connection.autoReconnectForPools", "true");//处理连接超时
		hibernateProps.put("connection.is-connection-validation-required", "true");//处理连接超时

		LocalContainerEntityManagerFactoryBean result = new LocalContainerEntityManagerFactoryBean();
		//加载所有需要扫描的实体所在包
		String[] packages = new String[] {"com.zpself.module.system.entity"};
		result.setPackagesToScan(packages);
		result.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		result.setJpaPropertyMap(hibernateProps);
		return result;
	}

	/*@Bean
	public EntityManagerFactory entityManagerFactory(LocalContainerEntityManagerFactoryBean entityManagerFactoryBean) {
		//this.entityManagerFactory = entityManagerFactoryBean.getObject();
		return entityManagerFactoryBean.getObject();
	}*/
	
	@Bean
	public DataSource dataSource(Map<String, DataSource> dataSourcesDvdRental) {
		return dataSourcesDvdRental.get(new TenantDvdRentalIdentifierResolverImpl().resolveCurrentTenantIdentifier());
	}

	@Bean(name = "transactionManager")
	public PlatformTransactionManager txManager(LocalContainerEntityManagerFactoryBean entityManagerFactoryBean) {
		DataSource dataSource = this.result.get(new TenantDvdRentalIdentifierResolverImpl().resolveCurrentTenantIdentifier());
		//EntityManagerFactory emf = this.entityManagerFactory;
		JpaTransactionManager jpaTM = new JpaTransactionManager();
		jpaTM.setDataSource(dataSource);
		jpaTM.setEntityManagerFactory(entityManagerFactoryBean.getObject());
		jpaTM.setRollbackOnCommitFailure(true);
		return jpaTM;
	}

}
