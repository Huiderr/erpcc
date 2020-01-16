package com.cwca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.PropertySource;

/**
 * @author wongs
 */
@SpringBootApplication
@EnableCaching
@PropertySource("classpath:config.properties")
public class ErpccApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(ErpccApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ErpccApplication.class);
    }
}
