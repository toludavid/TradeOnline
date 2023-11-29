package com.stanbic.redbox.fincale.online.rest.service.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerDetailDto {
    private String cif;
    private String accountNumber;
    private String clientName;
    private String marketSegmentCode;
    private String marketSegment;
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String globalCif;
    private String city;
    private String postalCode;
    private String countryCode;
    private String province;
}
