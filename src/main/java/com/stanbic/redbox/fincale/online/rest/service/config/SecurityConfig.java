package com.stanbic.redbox.fincale.online.rest.service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

//@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	/*
	 * @Override protected void configure(HttpSecurity http) throws Exception {//
	 * http.authorizeRequests().antMatchers("/v3/api-docs/**", "/swagger-ui/**",
	 * "/swagger-ui.html").permitAll() //.antMatchers(HttpMethod.GET, "/user/info",
	 * "/api/foos/**").hasAuthority("SCOPE_read") .antMatchers(HttpMethod.POST,
	 * "/api/foos").hasAuthority("SCOPE_tradeonline").anyRequest().authenticated()
	 * .and().oauth2ResourceServer().jwt(); }
	 */
	/*
	 * @Autowired private UserDetailsService userDetailsService;
	 * 
	 * @Autowired private PasswordEncoder userPasswordEncoder;
	 */

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
/**
	@Bean
	public CustomAuthenticationProvider authProvider() {
		CustomAuthenticationProvider authenticationProvider = new CustomAuthenticationProvider();
		return authenticationProvider;
	}
	@Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authProvider());
    }
	**/
	
	
	/*
	 * @Override protected void configure(AuthenticationManagerBuilder auth) throws
	 * Exception { auth.userDetailsService(userDetailsService).passwordEncoder(
	 * userPasswordEncoder); }
	 */
	/**
	@Override
	@Order(Ordered.HIGHEST_PRECEDENCE)
	protected void configure(HttpSecurity http) throws Exception {
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().csrf().disable()
				.authorizeRequests()
				// .antMatchers("/resources/**").permitAll()
				// .antMatchers("/about").permitAll()
				.antMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
				.antMatchers("/oauth/token").permitAll().antMatchers(HttpMethod.POST, "/finacle/trade/onlines")
				.hasAuthority("SCOPE_tradeonline").anyRequest().authenticated().and().oauth2ResourceServer().jwt();
		
		//  .and().httpBasic().and().formLogin(). loginPage("/login")
		//  .loginProcessingUrl("/loginSecure").defaultSuccessUrl("/index",
		//  true).permitAll();
		 
	}
	**/
	@Override
	@Order(Ordered.HIGHEST_PRECEDENCE)
	protected void configure(HttpSecurity http) throws Exception {
		http.cors(Customizer.withDefaults()).sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().csrf().disable()
				.authorizeRequests()
				// .antMatchers("/resources/**").permitAll()
				// .antMatchers("/about").permitAll()
				
				.antMatchers("/uat/redbox/services/trade/online/core-banking/v3/api-docs/**", "/uat/redbox/services/trade/online/core-banking/swagger-ui/**", "/uat/redbox/services/trade/online/core-banking/swagger-ui.html")
				.permitAll()
				.antMatchers("/uat/redbox/services/trade/online/core-banking/oauth/token").permitAll().antMatchers(HttpMethod.POST, "/uat/redbox/services/trade/online/core-banking")
				.hasAuthority("SCOPE_tradeonline").anyRequest().authenticated().and().oauth2ResourceServer().jwt(Customizer.withDefaults())
				;
		
		//  .and().httpBasic().and().formLogin(). loginPage("/login")
		//  .loginProcessingUrl("/loginSecure").defaultSuccessUrl("/index",
		//  true).permitAll();
		 
	}
	
	
	
}
