package com.github.panxiaole.polestar.doc.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * swagger资源配置
 * @author panxiaole
 * @date 2019-05-31
 */
@Slf4j
@Primary
@Component
public class SwaggerResourceConfig implements SwaggerResourcesProvider {


	@Autowired
	RouteLocator routeLocator;

	@Override
	public List<SwaggerResource> get() {
		//获取所有router
		List<SwaggerResource> resources = new ArrayList<>();
		List<Route> routes = routeLocator.getRoutes();
		log.info("Route Size:{}", routes.size());
		for (Route route : routes) {
			resources.add(swaggerResource(route.getId(), route.getFullPath().replace("**", "v2/api-docs")));
		}
		return resources;
	}

	private SwaggerResource swaggerResource(String name, String location) {
		log.info("name:{},location:{}", name, location);
		SwaggerResource swaggerResource = new SwaggerResource();
		swaggerResource.setName(name);
		swaggerResource.setLocation(location);
		swaggerResource.setSwaggerVersion("2.0");
		return swaggerResource;
	}
}
