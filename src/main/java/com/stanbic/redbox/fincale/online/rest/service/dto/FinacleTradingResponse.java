package com.stanbic.redbox.fincale.online.rest.service.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FinacleTradingResponse {
	
	@JsonProperty(value="responseCode", required=true, index = 1)
	  @Schema(description = "response Code",
	      example = "string", required = true)
	  @NotBlank
	  @Size(min = 0, max = 3)
    private String responseCode;
	@JsonProperty(value="responseMessage", required=true, index = 2)
	  @Schema(description = "Response",
	      example = "string", required = true)
	  @NotBlank
	  @Size(min = 0, max = 40)
    private String responseMessage;
    private String message;
}
