package com.stanbic.redbox.fincale.online.rest.service.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.stanbic.redbox.fincale.online.rest.service.entity.DSXCustomerKYCStatusResponse;

public interface DSXCustomerKYCStatusResponseRepository extends CrudRepository<DSXCustomerKYCStatusResponse, String> {

    @Query(value = "SELECT KYCCOMPLIANTSTATUS FROM custom.Data_Store_CIF_STATUS@red_link WHERE CIFNUMBER = :cif", nativeQuery = true)
    String getKycStatus(@Param("cif") String cif);

}