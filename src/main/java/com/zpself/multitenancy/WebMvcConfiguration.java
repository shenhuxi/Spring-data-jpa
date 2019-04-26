package com.zpself.multitenancy;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * springboot拦截器注入
 * @author wq
 * @date 2019-1-17 14:57:28
 */

@Configuration
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {
	@Override
	  public void addInterceptors(InterceptorRegistry registry) {
	    registry.addInterceptor(new DvdRentalMultiTenantInterceptor()).addPathPatterns("/**");
	  }
}
