package com.stanbic.redbox.fincale.online.rest.service.dto.finacletrade;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.stanbic.redbox.fincale.online.rest.service.dto.Address;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BeneficiaryDetails {

	@JsonProperty(value = "name", required = true, index = 1)
	@Schema(description = "Beneficiary name", example = "string", required = true)
	@NotBlank
	@Size(min = 0, max = 300)
	private String name;
	@JsonProperty(value = "address", required = true, index = 1)
	@Schema(description = "Beneficiary address details", example = "string", required = true)
	@NotBlank
	@Size(min = 0, max = 300)
	Address address;
	/*
	 * @JsonProperty(value = "name", required = true, index = 1)
	 * 
	 * @Schema(description = "Beneficiary name", example = "string", required =
	 * true)
	 * 
	 * @NotBlank
	 * 
	 * @Size(min = 0, max = 300) private BeneficiaryBankDetails
	 * beneficiaryBankDetails;
	 * 
	 * @JsonProperty(value = "name", required = true, index = 1)
	 * 
	 * @Schema(description = "Beneficiary name", example = "string", required =
	 * true)
	 * 
	 * @NotBlank
	 * 
	 * @Size(min = 0, max = 300) private ContactDetails contactDetails;
	 */
	@JsonProperty(value = "name", required = true, index = 1)
	@Schema(description = "Beneficiary name", example = "string", required = true)
	@NotBlank
	@Size(min = 0, max = 300)
	private List<String> authorisedRepresentatives;
}
