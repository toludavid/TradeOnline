package com.stanbic.redbox.fincale.online.rest.service.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.stanbic.redbox.fincale.online.rest.service.dto.MarketSegmentDescEnum;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "RBX_T_TRADE_ONLINE_CUST_DETAIL")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FinacleDtdHtdTxnDataResponse {
    @Id
    @Column(name = "FORACID" )
    private String accountNumber;
    @Column(name = "CIF" )
    private String cif;
    @Column(name = "CLIENT_NAME")
    private String clientName;
    @Column(name ="MARKET_SEGMENT_CODE")
    private String marketSegmentCode;
    @Column(name = "MARKET_SEGMENT")
    private String marketSegment;
    @Column(name = "ADDRESS_LINE1")
    private String addressLine1;
    @Column(name = "ADDRESS_LINE2")
    private String addressLine2;
    @Column(name = "ADDRESS_LINE3")
    private String addressLine3;
    @Column(name = "GLOBAL_CIF")
    private String globalCif;
    @Column(name = "CITY")
    private String city;
    @Column(name = "POST_CODE")
    private String postalCode;
    @Column(name = "COUNTRY_CODE")
    private String countryCode;
    @Column(name = "PROVINCE")
    private String province;

}
