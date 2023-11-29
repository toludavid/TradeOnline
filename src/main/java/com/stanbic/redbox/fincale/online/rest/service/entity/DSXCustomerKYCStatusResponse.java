package com.stanbic.redbox.fincale.online.rest.service.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Data_Store_CIF_STATUS")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DSXCustomerKYCStatusResponse {
    @Id
    @Column(name = "CIFNUMBER" )
    private String cifId;
    @Column(name = "KYCCOMPLIANTSTATUS" )
    private String kycStatus;
    

}