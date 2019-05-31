package com.github.panxiaole.polestar.api;

import com.github.panxiaole.polestar.log.annotation.EnableOutputSystemLog;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * api汇总服务启动类
 *
 * @author panxiaole
 * @date 2019-05-08
 */
@EnableEurekaClient
@EnableDiscoveryClient
@EnableFeignClients("com.github.panxiaole.polestar.**.client")
@ComponentScan({
		"com.github.panxiaole.polestar.api",
		"com.github.panxiaole.polestar.biz.**.fallback"
})
@EnableHystrix
@SpringBootApplication
@EnableOutputSystemLog
public class ApiServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiServerApplication.class, args);
	}

}
