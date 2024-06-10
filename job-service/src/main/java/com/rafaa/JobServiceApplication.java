package com.rafaa;

import com.rafaa.facility.repository.FacilityRepository;
import com.rafaa.multitenancy.context.TenantContextHolder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;


@SpringBootApplication
@ConfigurationPropertiesScan
public class JobServiceApplication {
    public static UUID id;
    public static String tenant;
    public static UUID facilityId;
    public static FacilityRepository facilityRepository;
    public static void main(String[] args) {
        SpringApplication.run(JobServiceApplication.class);
    }
}

@RestController
class AppController{

    @GetMapping("/tenant")
    public ResponseEntity<Tenant> tenant(@RequestHeader("X-TenantId") String tenantId){
        TenantContextHolder.setTenantIdentifier(tenantId);
        String tenantIdentifier = TenantContextHolder.getTenantIdentifier();
        System.out.println(tenantIdentifier);
        Tenant tenant = new Tenant(tenantIdentifier);
        return ResponseEntity.ok(tenant);
    }

}

record Tenant(String tenantIdentifier){}