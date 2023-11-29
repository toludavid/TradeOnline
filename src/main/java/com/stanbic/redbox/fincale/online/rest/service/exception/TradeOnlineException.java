package com.stanbic.redbox.fincale.online.rest.service.exception;


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
public class TradeOnlineException {

	/**
	 * 
	 */

	@JsonProperty(value="responseCode", required=true, index = 1)
	  @Schema(description = "response Code",
	      example = "HTTP Response Code For Specific Operation Call", required = true)
	  @NotBlank
	  @Size(min = 0, max = 3)
	private String responseCode;
	@JsonProperty(value="responseMessage", required=true, index = 2)
	  @Schema(description = "Response Description",
	      example = "HTTP Response Message For Specific Operation Call", required = true)
	  @NotBlank
	  @Size(min = 0, max = 40)
	private String responseDescription;
	
	@JsonProperty(value = "result", required = true, index = 3)
	@Schema(description = "result", example = "Payload Type specific to api operation", required = true)
	@NotBlank
	@Size(min = 0, max = 40)
	private Object result;
	 
 
	
}
