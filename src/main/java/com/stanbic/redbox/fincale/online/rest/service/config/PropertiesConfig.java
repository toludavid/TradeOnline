package com.stanbic.redbox.fincale.online.rest.service.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties()
@Configuration
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropertiesConfig {

	/*
	 * @Value("${token.tokenName}") private String tokenName;
	 * 
	 * @Value("${token.pwrd}") private String password;
	 */


    @Value("${token.tokenName.admin}")
    private String tokenNameAdmin;
    @Value("${token.password.admin}")
    private String passwordAdmin;
    
    @Value("${custom.security.oauth2.client.access-token-uri}")
    private String accessTokenUrl ;
    
    @Value("${trade.engine.rest.service.use.proxy}")
    private String useProxy;
    @Value("${trade.engine.rest.service.proxy.host}")
    private String proxyHost;
    @Value("${trade.engine.rest.service.proxy.port}")
    private String proxyPort;
}
