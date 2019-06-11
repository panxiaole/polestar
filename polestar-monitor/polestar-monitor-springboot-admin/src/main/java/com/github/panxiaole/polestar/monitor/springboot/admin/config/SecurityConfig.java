package com.github.panxiaole.polestar.monitor.springboot.admin.config;

import de.codecentric.boot.admin.server.config.AdminServerProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

/**
 * 安全配置
 *
 * @author panxiaole
 * @date 2019-05-06
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final String adminContextPath;

	public SecurityConfig(AdminServerProperties adminServerProperties) {
		this.adminContextPath = adminServerProperties.getContextPath();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
		successHandler.setTargetUrlParameter("redirectTo");
		successHandler.setDefaultTargetUrl(adminContextPath + "/");
		http
				.authorizeRequests()
				.antMatchers("/assets/**").permitAll()
				.antMatchers("/login").permitAll()
				.antMatchers("/logout").permitAll()
				.anyRequest().authenticated()
				.and().formLogin().loginPage("/login")
				.and().logout().logoutUrl("/logout")
				.and().httpBasic()
				.and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
				.ignoringAntMatchers(
						adminContextPath + "/instances",
						adminContextPath + "/actuator/**"
				);
	}
}
