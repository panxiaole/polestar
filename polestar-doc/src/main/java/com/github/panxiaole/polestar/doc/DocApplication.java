package com.github.panxiaole.polestar.doc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * 文档中心启动类
 * @author panxiaole
 * @date 2019-5-31
 */
@EnableZuulProxy
@EnableDiscoveryClient
@EnableEurekaClient
@SpringBootApplication
public class DocApplication {

	public static void main(String[] args) {
		SpringApplication.run(DocApplication.class, args);
	}

}
