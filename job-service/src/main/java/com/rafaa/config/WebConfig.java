package com.rafaa.config;

import com.rafaa.job.config.AutowiringSpringBeanJobFactory;
import com.rafaa.job.service.JobsListener;
import com.rafaa.job.service.TriggerListner;
import com.rafaa.multitenancy.web.TenantInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

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

//    @Autowired
//    DataSource dataSource;
//
//    @Autowired
//    private ApplicationContext applicationContext;
//
//    @Autowired
//    private TriggerListner triggerListner;
//
//    @Autowired
//    private JobsListener jobsListener;
//
//    /**
//     * create scheduler
//     */
//    @Bean
//    public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
//
//        SchedulerFactoryBean factory = new SchedulerFactoryBean();
//        factory.setOverwriteExistingJobs(true);
//        factory.setDataSource(dataSource);
//        factory.setQuartzProperties(quartzProperties());
//
//        // Register listeners to get notification on Trigger misfire etc
//        factory.setGlobalTriggerListeners(triggerListner);
//        factory.setGlobalJobListeners(jobsListener);
//
//        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
//        jobFactory.setApplicationContext(applicationContext);
//        factory.setJobFactory(jobFactory);
//
//        return factory;
//    }
//
//    /**
//     * Configure quartz using properties file
//     */
//    @Bean
//    public Properties quartzProperties() throws IOException {
//        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
//        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
//        propertiesFactoryBean.afterPropertiesSet();
//        return propertiesFactoryBean.getObject();
//    }

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
