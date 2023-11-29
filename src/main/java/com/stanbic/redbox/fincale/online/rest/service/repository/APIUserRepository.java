package com.stanbic.redbox.fincale.online.rest.service.repository;

import org.springframework.data.repository.CrudRepository;

import com.stanbic.redbox.fincale.online.rest.service.entity.APIUser;

public interface APIUserRepository extends CrudRepository<APIUser, Long>{
	
	APIUser findByEmail(String email);

	APIUser findByClientId(String clientId);

}
