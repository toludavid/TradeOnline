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
public class CustomerDetailsResponse {

	@JsonProperty(value = "cif", required = true, index = 1)
	@Schema(description = "Customer Identifier", example = "string", required = true)
	@NotBlank
	@Size(min = 0, max = 10)
	public String cif;
	@JsonProperty(value = "clientName", required = true, index = 2)
	@Schema(description = "Client Name", example = "string", required = true)
	@NotBlank
	@Size(min = 0, max = 100)
	public String clientName;
	@JsonProperty(value = "marketSegmentCode", required = true, index = 3)
	@Schema(description = "market Segment Code", example = "string", required = true)
	@NotBlank
	@Size(min = 0, max = 10)
	public String marketSegmentCode;
	@JsonProperty(value = "marketSegmentDescription", required = true, index = 4)
	@Schema(description = "Market Segment Description", example = "PBB", required = true)
	@NotBlank
	@Size(min = 0, max = 10)
	public MarketSegmentDescEnum marketSegmentDescription;
	@JsonProperty(value = "globalCif", required = true, index = 5)
	@Schema(description = "global Cif", example = "string", required = true)
	@NotBlank
	@Size(min = 0, max = 10)
	public String globalCif;

	@JsonProperty(value = "address", required = true, index = 6)
	@Schema(description = "Address Deatils", required = true)

	public Address address;
}
