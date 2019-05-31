package com.github.panxiaole.polestar.swagger2.autoconfigure;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import com.github.panxiaole.polestar.swagger2.properties.Swagger2Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger2自动配置类
 *
 * @author panxiaole
 * @date 2019-05-29
 */
@EnableSwagger2
@EnableSwaggerBootstrapUI
@Import(Swagger2Properties.class)
@ConditionalOnProperty(name = "swagger.enable", havingValue = "true", matchIfMissing = true)
public class Swagger2AutoConfiguration {

	@Autowired
	private Swagger2Properties swagger2Properties;

	@Bean
	public Docket buildDocket() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(buildApiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage(swagger2Properties.getBasePackage()))
				.paths(PathSelectors.any())
				.build();
	}

	private ApiInfo buildApiInfo() {
		Swagger2Properties.Contact contact = swagger2Properties.getContact();
		return new ApiInfoBuilder()
				.title(swagger2Properties.getTitle())
				.description(swagger2Properties.getDescription())
				.termsOfServiceUrl(swagger2Properties.getTermsOfServiceUrl())
				.version(swagger2Properties.getVersion())
				.contact(new Contact(contact.getName(), contact.getUrl(), contact.getEmail()))
				.build();
	}

}
