package com.rafaa.multitenancy.tenantdetails;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "multitenancy")
public record TenantDetailsProperties(List<TenantDetails> tenants) { }