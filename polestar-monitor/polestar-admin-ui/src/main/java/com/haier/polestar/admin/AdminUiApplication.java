package com.haier.polestar.admin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * admin ui 启动类
 *
 * @author panxiaole
 * @date 2019-05-06
 */
@EnableAdminServer
@SpringBootApplication
public class AdminUiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminUiApplication.class, args);
	}

}
