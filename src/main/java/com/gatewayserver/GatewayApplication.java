package com.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
@EnableEurekaClient
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

	@Bean
	public RouteLocator myRoutes(RouteLocatorBuilder builder){
		return builder.routes()
				.route(p->p
						.path("/rabinbank/accounts/**")
						.filters(f->f.rewritePath("/rabinbank/accounts/(?<segment>.*)","/${segment}")
									.addResponseHeader("X-Response-Time",new Date().toString()))
						.uri("lb://ACCOUNTS"))
				.route(p->p
				.path("/rabinbank/loans/**")
				.filters(f->f.rewritePath("/rabinbank/loans/(?<segment>.*)","/${segment}")
						.addResponseHeader("X-Response-Time",new Date().toString()))
				.uri("lb://LOANS"))
				.route(p->p
						.path("/rabinbank/cards/**")
						.filters(f->f.rewritePath("/rabinbank/cards/(?<segment>.*)","/${segment}")
								.addResponseHeader("X-Response-Time",new Date().toString()))
						.uri("lb://CARDS")).build();
	}

}
