package com.rafaa.multitenancy.context.resolver;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class HttpHeaderTenantResolver implements TenantResolver<HttpServletRequest> {

//    private static final String TENANT_HEADER = "X-TenantId";
    private static final String TENANT_PARAMETER = "tenantId";

    @Override
    @Nullable
    public String resolveTenantIdentifier(HttpServletRequest request) {
        return request.getParameter(TENANT_PARAMETER);
//        return request.getHeader(TENANT_HEADER);
    }

}