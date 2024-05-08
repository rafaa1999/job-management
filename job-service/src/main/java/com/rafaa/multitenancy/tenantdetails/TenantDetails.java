package com.rafaa.multitenancy.tenantdetails;

public record TenantDetails(
        String identifier,
        boolean enabled,
        String schema
) {}