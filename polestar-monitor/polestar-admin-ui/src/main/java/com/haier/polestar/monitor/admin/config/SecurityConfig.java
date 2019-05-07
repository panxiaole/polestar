package com.haier.polestar.monitor.admin.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 安全配置
 *
 * @author panxiaole
 * @date 2019-05-06
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.authorizeRequests()
				.antMatchers("/assets/**").permitAll()
				.antMatchers("/login").permitAll()
				.antMatchers("/logout").permitAll()
				.anyRequest().authenticated()
				.and().formLogin().loginPage("/login")
				.and().logout().logoutUrl("/logout")
				.and().httpBasic()
				.and().csrf().disable();
	}
}
