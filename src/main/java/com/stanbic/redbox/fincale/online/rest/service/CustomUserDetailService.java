package com.stanbic.redbox.fincale.online.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.stanbic.redbox.fincale.online.rest.service.entity.APIUser;
import com.stanbic.redbox.fincale.online.rest.service.repository.APIUserRepository;

public class CustomUserDetailService implements UserDetailsService{

	 @Autowired
	    APIUserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String clientId) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		final APIUser customer = userRepository.findByClientId(clientId);
        if (customer == null) {
            throw new UsernameNotFoundException(clientId);
        }
        UserDetails user = User.withUsername(customer.getClientId())
                            .password(customer.getClientSecret())
                            .accountExpired(customer.isUserEnabled())
                            .authorities("TRADEONLINE").build();
        return user;	}

}
