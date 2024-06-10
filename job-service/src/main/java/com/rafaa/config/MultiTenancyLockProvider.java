package com.rafaa.config;

import com.rafaa.multitenancy.context.TenantContextHolder;
import com.rafaa.multitenancy.tenantdetails.TenantDetails;
import com.rafaa.multitenancy.tenantdetails.TenantDetailsService;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.core.LockConfiguration;
import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.core.SimpleLock;
import net.javacrumbs.shedlock.provider.jdbctemplate.JdbcTemplateLockProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
public class MultiTenancyLockProvider implements LockProvider {

    private final ConcurrentHashMap<String, LockProvider> providers = new ConcurrentHashMap<>();

    @Autowired
    public TenantDetailsService tenantDetailsService;

    @Override
    public Optional<SimpleLock> lock(LockConfiguration lockConfiguration) {

        String tenantName;
        List<TenantDetails> allTenants = tenantDetailsService.loadAllTenants()
                .stream()
                .collect(Collectors.toList());

        if(allTenants.isEmpty()){
           return Optional.empty();
        }

        String currentTenant = TenantContextHolder.getTenantIdentifier();
        tenantName = currentTenant == null ? allTenants.get(0).identifier() : currentTenant;
        log.debug("Tenant used for ShedLock lock operation is {}", tenantName);
//        return Optional.empty();
        return providers.computeIfAbsent(tenantName, this::createLockProvider).lock(lockConfiguration);

    }

    private LockProvider createLockProvider(String tenantName) {
        return new JdbcTemplateLockProvider(getDataSource(tenantName));
    }

    private DataSource getDataSource(String tenantName) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/shedlock?currentSchema=" + tenantName);
        dataSource.setUsername(System.getenv().getOrDefault("db_username", "postgres"));
        dataSource.setPassword(System.getenv().getOrDefault("db_password", "1999"));
        dataSource.setDriverClassName("org.postgresql.Driver");
        return dataSource;
    }

}