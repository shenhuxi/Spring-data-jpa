package com.zpself.multitenancy;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TenantDvdRentalIdentifierResolverImpl implements CurrentTenantIdentifierResolver {

	private static String DEFAULT_TENANT_ID = "default";
    protected final Logger logger = LoggerFactory.getLogger(getClass());
	  
	    @Override
	    public String resolveCurrentTenantIdentifier() {
	      String currentTenantId = DvdRentalTenantContext.getTenantId() == null ? DEFAULT_TENANT_ID : DvdRentalTenantContext.getTenantId();
	      logger.info("currentTenantId==" + currentTenantId);
	      return currentTenantId;
	   }

		@Override
		public boolean validateExistingCurrentSessions() {
			return true;
		}
}
