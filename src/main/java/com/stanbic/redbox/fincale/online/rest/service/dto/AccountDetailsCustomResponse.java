package com.stanbic.redbox.fincale.online.rest.service.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountDetailsCustomResponse {
	
	
	@JsonProperty(value = "responseCode", required = true, index = 1)
	@Schema(description = "response Code", example = "HTTP Response Code For Specific Operation Call", required = true)
	@NotBlank
	@Size(min = 0, max = 3)
	private String responseCode;
	@JsonProperty(value = "responseMessage", required = true, index = 2)
	@Schema(description = "Operation Response", example = "HTTP Response Message For Specific Operation Call", required = true)
	@NotBlank
	@Size(min = 0, max = 40)
	private String responseMessage;
	
	@JsonProperty(value = "result", required = true, index = 3)
	@Schema(description = "Payload Type specific to api operation", example = "Payload Type specific to api operation", required = true)
	@NotBlank
	@Size(min = 0, max = 40)
	private AccountDetailsResponse result;

	@JsonProperty(value = "messages", required = true, index = 4)
	@Schema(description = "No Content", example = "No Content", required = true)
	@NotBlank
	@Size(min = 0, max = 40)
	private Object messages;
	
}
