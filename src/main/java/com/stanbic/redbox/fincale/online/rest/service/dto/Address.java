package com.stanbic.redbox.fincale.online.rest.service.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Address{
	
	
	@JsonProperty(value = "addressLine1", required = true, index = 1)
	@Schema(description = "address Line1", example = "string", required = true)
	@NotBlank
	@Size(min = 0, max = 300)
    public String addressLine1;
	@JsonProperty(value = "addressLine2", required = true, index = 2)
	@Schema(description = "address Line2", example = "string", required = true)
	@NotBlank
	@Size(min = 0, max = 300)
    public String addressLine2;
	@JsonProperty(value = "addressLine3", required = true, index = 3)
	@Schema(description = "address Line3", example = "string", required = true)
	@NotBlank
	@Size(min = 0, max = 300)
    public String addressLine3;
	@JsonProperty(value = "city", required = true, index = 4)
	@Schema(description = "city", example = "string", required = true)
	@NotBlank
	@Size(min = 0, max = 30)
    public String city;
	@JsonProperty(value = "postalCode", required = true, index = 5)
	@Schema(description = "post Code", example = "string", required = true)
	@NotBlank
	@Size(min = 0, max = 10)
    public String postalCode;
	@JsonProperty(value = "countryCode", required = true, index = 6)
	@Schema(description = "country Code", example = "string", required = true)
	@NotBlank
	@Size(min = 0, max = 3)
    public String countryCode;
	@JsonProperty(value = "province", required = true, index = 7)
	@Schema(description = "province", example = "string", required = true)
	@NotBlank
	@Size(min = 0, max = 20)
    public String province;
}

