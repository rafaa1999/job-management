package com.rafaa.config;

import com.rafaa.multitenancy.web.TenantInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final TenantInterceptor tenantInterceptor;

    public WebConfig(TenantInterceptor tenantInterceptor) {
        this.tenantInterceptor = tenantInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tenantInterceptor);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // Map CORS configuration for all paths
                .allowedOrigins("http://localhost:4200")  // Allow requests from this origin
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // Allowed HTTP methods
                .allowedHeaders("Content-Type", "X-TenantId");  // Allowed headers
    }

}

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**") // Apply CORS to all endpoints
//                .allowedOrigins("http://localhost:4200") // Allow requests from Angular application
//                .allowedMethods("GET", "POST", "PUT", "DELETE")
//                .allowedHeaders("*") // Allow all headers, including X-TenantId
//                .exposedHeaders("X-TenantId") // Expose X-TenantId header to the client
//                .allowCredentials(true); // Allow credentials (e.g., cookies, authorization headers)
//    }
