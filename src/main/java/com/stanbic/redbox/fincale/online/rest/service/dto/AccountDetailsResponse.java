package com.stanbic.redbox.fincale.online.rest.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDetailsResponse {
	
    public List<Account> accounts;
}
