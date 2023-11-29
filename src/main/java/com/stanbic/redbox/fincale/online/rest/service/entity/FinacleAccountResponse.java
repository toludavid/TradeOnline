package com.stanbic.redbox.fincale.online.rest.service.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.stanbic.redbox.fincale.online.rest.service.dto.AccountStatusEnum;
import com.stanbic.redbox.fincale.online.rest.service.dto.AccountTypeEnum;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "RBX_T_TRADE_ONLINE_ACCT_DETAIL")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FinacleAccountResponse {
	
	
	
    @Id
    @Column(name = "ACCOUNT_NUMBER" )
    private String accountNumber;
	/*
	 * @Column(name = "CIF_ID" ) private String cifId;
	 */
    @Column(name = "ACCOUNT_STATUS" )
    private String accountStatus;
	/*
	 * @Column(name = "ACCT_CLS_FLG" ) private String accountClosureFlag;
	 */
    @Column(name = "CURRENCY")
    private String currency;
    @Column(name ="ACCOUNT_TYPE")
    private String accountType ;

}
