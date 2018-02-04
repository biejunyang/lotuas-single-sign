package com.bjy.lotuas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 *Spring applicaiton 启动类
 */
@SpringBootApplication
@ServletComponentScan
public class MainApplication
{
	
//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//		return builder.sources(MainApplication.class);
//	}
//	
//	OpenEntityManagerInViewInterceptor

    public static void main( String[] args )
    {
        SpringApplication.run(MainApplication.class, args);
    }
    
}
