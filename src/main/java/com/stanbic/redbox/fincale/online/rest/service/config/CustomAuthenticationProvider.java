package com.stanbic.redbox.fincale.online.rest.service.config;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


//@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return false;
	}
/**
	@Autowired
	UserDetailsService userDetailsService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		final String username = (authentication.getPrincipal() == null) ? "NONE_PROVIDED" : authentication.getName();
		if (StringUtils.isEmpty(username)) {
			throw new BadCredentialsException("invalid login details");
		}
		// get user details using Spring security user details service
		UserDetails user = null;
		try {
			user = userDetailsService.loadUserByUsername(username);

		} catch (UsernameNotFoundException exception) {
			throw new BadCredentialsException("invalid login details");
		}

		return createSuccessfulAuthentication(authentication, user);
	}

	private Authentication createSuccessfulAuthentication(Authentication authentication, UserDetails user) {
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getUsername(),
				authentication.getCredentials(), user.getAuthorities());
		token.setDetails(authentication.getDetails());
		return token;
	}

	
	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
       // return authentication.equals(ExternalServiceAuthenticationToken.class);
		return false;
	}
	
	**/

}
