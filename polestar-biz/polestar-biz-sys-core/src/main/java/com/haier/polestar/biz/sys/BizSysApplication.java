package com.haier.polestar.biz.sys;

import com.haier.polestar.starter.log.annotation.EnableOutputSystemLog;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @desc
 * @author  panxiaole
 * @Date    2019-05-07 11:34
 */
@EnableOutputSystemLog
@SpringBootApplication
public class BizSysApplication {

	public static void main(String[] args) {
		SpringApplication.run(BizSysApplication.class, args);
	}

}
