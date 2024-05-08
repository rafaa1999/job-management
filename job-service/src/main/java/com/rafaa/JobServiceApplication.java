package com.rafaa;

import com.rafaa.multitenancy.context.TenantContextHolder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@ConfigurationPropertiesScan
public class JobServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(JobServiceApplication.class);
    }
}

@RestController
class AppController{
    @GetMapping("/tenant")
    public String tenant(){
        String tenantIdentifier = TenantContextHolder.getTenantIdentifier();
        System.out.println(tenantIdentifier);
        return tenantIdentifier;
    }

}