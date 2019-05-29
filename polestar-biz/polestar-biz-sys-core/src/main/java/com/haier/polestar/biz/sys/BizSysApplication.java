package com.haier.polestar.biz.sys;

import com.haier.polestar.log.annotation.EnableOutputSystemLog;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;

/**
 * @author panxiaole
 * @date 2019-05-07
 */
@EnableCaching
@EnableSwagger2
@EnableSwaggerBootstrapUI
@EnableOutputSystemLog
@EnableConfigurationProperties
@SpringBootApplication
public class BizSysApplication {

	public static void main(String[] args) {
		SpringApplication.run(BizSysApplication.class, args);
	}

}
