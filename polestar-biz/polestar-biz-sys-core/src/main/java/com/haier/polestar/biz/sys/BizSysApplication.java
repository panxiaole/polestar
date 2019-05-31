package com.haier.polestar.biz.sys;

import com.haier.polestar.log.annotation.EnableOutputSystemLog;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 系统管理启动类
 *
 * @author panxiaole
 * @date 2019-05-07
 */
@EnableOutputSystemLog
@SpringBootApplication
public class BizSysApplication {

	public static void main(String[] args) {
		SpringApplication.run(BizSysApplication.class, args);
	}

}
