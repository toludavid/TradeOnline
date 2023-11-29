package com.stanbic.redbox.fincale.online.rest.service.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Account{
	
	@JsonProperty(value="accountNumber", required=true, index = 1)
	  @Schema(description = "Account Number",
	      example = "string", required = true)
	  @NotBlank
	  @Size(min = 0, max = 10)
    public String accountNumber;
	
	@JsonProperty(value="accountStatus", required=true, index = 2)
	  @Schema(description = "Account Status",
	      example = "ACTIVE", required = true)
	  @NotBlank
	  @Size(min = 0, max = 10)
    public AccountStatusEnum accountStatus;
	@JsonProperty(value="currency", required=true, index = 3)
	  @Schema(description = "Currency Code",
	      example = "NGN", required = true)
	  @NotBlank
	  @Size(min = 0, max = 10)
    public String currency;
	@JsonProperty(value="accountType", required=true, index = 4)
	  @Schema(description = "Account Type",
	      example = "ILOC", required = true)
	  @NotBlank
	  @Size(min = 0, max = 10)
    public AccountTypeEnum accountType;
}

