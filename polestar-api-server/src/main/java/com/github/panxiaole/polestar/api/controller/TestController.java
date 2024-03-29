package com.github.panxiaole.polestar.api.controller;

import com.github.panxiaole.polestar.log.annotation.SystemLog;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author panxiaole
 * @date 2019-05-01
 */
@RestController
public class TestController {

	@GetMapping("/hello")
	@SystemLog
	public String hello() {
		return "hello";
	}
}
