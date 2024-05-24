package com.rafaa.multitenancy.exceptions;

import com.rafaa.multitenancy.context.TenantContextHolder;

public class TenantResolutionException extends IllegalStateException {

    public TenantResolutionException() {
        super("Error when trying to resolve the current tenant");
    }

    public TenantResolutionException(String message) {
        super(message);
        System.out.println("this is the tenant from the front end : " + TenantContextHolder.getTenantIdentifier());
    }

}