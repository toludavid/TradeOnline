package com.stanbic.redbox.fincale.online.rest.service.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.Scopes;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.stanbic.redbox.fincale.online.rest.service.utils.UtilClass;
import com.stanbic.redbox.fincale.online.rest.service.utils.WebUtils;

@Configuration

@Slf4j
public class OpenApiConfig {
	/*
	 * @Bean public OpenAPI customOpenAPI() { return new OpenAPI() .components(new
	 * Components()) .info(new Info() .title("Book Application API")
	 * .description("This is a sample Spring Boot RESTful service using springdoc-openapi and OpenAPI 3."
	 * ) .termsOfService("terms") .contact(new Contact().email("@codersite.dev"))
	 * .license(new License().name("GNU")) .version("1.0") ); }
	 */

	//@Autowired
	//private WebUtils webUtils;
	@Value("${custom.security.oauth2.client.access-token-uri}")
	private String accessTokenUrl;

	@Value("${custom.security.oauth2.client.context-url}")
	private String contextURL;

	@Bean
	public OpenAPI customOpenAPI() {

		log.info(" accessTokenUrl {}", accessTokenUrl);
		log.info(" contextURL {}", contextURL);
		String newAccessTokenUrl = accessTokenUrl.replaceFirst("#serverIp#", UtilClass.getHostIP());
		log.info(" newAccessTokenUrl {}", newAccessTokenUrl);
		String newContextURL = contextURL.replaceFirst("#serverIp#", UtilClass.getHostIP());
		log.info(" newContextURL {}", newContextURL);

		log.info(" getClientIp ===>>> {}", UtilClass.getRemoteAddress());

		Server server = new Server();
		server.setUrl(newContextURL);
		List<Server> servers = new ArrayList<Server>();
		servers.add(server);
		return new OpenAPI()

				.servers(servers)
				.components(new Components().addSecuritySchemes("oAuth2", new SecurityScheme()
						.type(SecurityScheme.Type.OAUTH2).description("Oauth2 Client Credentials flow").flows(

								new OAuthFlows().clientCredentials(new OAuthFlow()
										// .authorizationUrl("http://172.20.208.1:8914" + "/oauth/authorize")
										// accessTokenUrl
										// .tokenUrl("http://localhost:8914" + "/oauth/token")
										.tokenUrl(newAccessTokenUrl)
										// .authorizationUrl("https://appsgw.stanbicibtc.com:4443" + "/oauth/authorize")
										// .tokenUrl("https://appsgw.stanbicibtc.com:4443" + "/oauth/token")

										.scopes(new Scopes().addString("tradeonline", "Grand access to trade online")))

						/**
						 * new OAuthFlows().authorizationCode(new OAuthFlow()
						 * .authorizationUrl("http://localhost:9000" + "/oauth/authorize")
						 * .tokenUrl("http://localhost:9000" + "/oauth/token") .scopes(new
						 * Scopes().addString("tradeonline", "Grand access to trade online")))
						 **/
						))).security(Arrays.asList(new SecurityRequirement().addList("oAuth2")))// .addList("spring_oauth")))

				.info(new Info().title("Finacle Trade Online APIs")
						.description("Redbox APIs to Perform Core Banking Operations via Trade Online Platform")
						.termsOfService("terms")
						.contact(new Contact().email("david.oladeji@stanbicibtc.com").name("Developer: David Oladeji"))
						// .license(new License().name("GNU"))
						.version("1.0"));
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {

			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/uat/redbox/services/trade/online/core-banking/**")
						.allowedOrigins("http://172.20.208.1:8914").allowedMethods("PUT", "POST", "GET")
						.allowedHeaders("*") // .exposedHeaders("*")
						.allowCredentials(false).maxAge(3600);
			}
		};
	}

	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin("http://192.168.0.187:8914");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		source.registerCorsConfiguration("/uat/redbox/services/trade/online/core-banking/**", config);
		FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<CorsFilter>(new CorsFilter(source));
		bean.setOrder(0);
		return bean;
	}

}
