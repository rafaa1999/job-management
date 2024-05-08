package com.rafaa.multitenancy.context.resolver;

import org.springframework.lang.Nullable;

@FunctionalInterface
public interface TenantResolver<T> {

    @Nullable
    String resolveTenantIdentifier(T source);

}