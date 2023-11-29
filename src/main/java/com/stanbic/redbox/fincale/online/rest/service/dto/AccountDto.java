package com.stanbic.redbox.fincale.online.rest.service.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class AccountDto {
    private String accountNumber;
    private String accountStatus;
    private String currency;
    private String accountType;
}
