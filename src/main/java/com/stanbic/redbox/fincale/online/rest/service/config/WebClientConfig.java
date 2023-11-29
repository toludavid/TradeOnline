package com.stanbic.redbox.fincale.online.rest.service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.ProxyProvider;

@Configuration
public class WebClientConfig {
    @Autowired
    PropertiesConfig formNXPServiceProperties;

	/*
	 * @Bean public Builder webClient(){ if
	 * ("N".equals(formNXPServiceProperties.getUseProxy())) { return
	 * WebClient.builder(); } ReactorClientHttpConnector connector = new
	 * ReactorClientHttpConnector(httpClient()); return
	 * WebClient.builder().clientConnector(connector); }
	 * 
	 * private HttpClient httpClient(){ return HttpClient.create()
	 * .tcpConfiguration(tcpClient -> tcpClient.proxy(typeSpec ->
	 * typeSpec.type(ProxyProvider.Proxy.HTTP).host(formNXPServiceProperties.
	 * getProxyHost())
	 * .port(Integer.parseInt(formNXPServiceProperties.getProxyPort())))); }
	 */
    
    
    @Bean
    public Builder webClient(){
        if ("N".equals(formNXPServiceProperties.getUseProxy())) {
            return WebClient.builder();
        }
     
        ReactorClientHttpConnector connector = new ReactorClientHttpConnector(httpClient());
        return WebClient.builder().clientConnector(connector);
    }

    private HttpClient httpClient(){
        return HttpClient.create()
                .tcpConfiguration(tcpClient ->
                        tcpClient.proxy(typeSpec ->
                                typeSpec.type(ProxyProvider.Proxy.HTTP).host(formNXPServiceProperties.getProxyHost())
                                        .port(Integer.parseInt(formNXPServiceProperties.getProxyPort()))));
    }
}
