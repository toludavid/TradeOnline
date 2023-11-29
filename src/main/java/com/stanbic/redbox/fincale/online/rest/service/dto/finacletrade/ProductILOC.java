package com.stanbic.redbox.fincale.online.rest.service.dto.finacletrade;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductILOC {
	
	@JsonProperty(value = "beneficiaryDetails", required = true, index = 1)
	@Schema(description = "Beneficiary details", example = "string", required = true)
	@NotBlank
	@Size(min = 0, max = 300)
	private BeneficiaryDetails beneficiaryDetails;
	@JsonProperty(value = "applicant", required = true, index = 1)
	@Schema(description = "Beneficiary name", example = "string", required = true)
	@NotBlank
	@Size(min = 0, max = 300)
	private Applicant applicant;
	@JsonProperty(value = "typeDetailsLOGT", required = true, index = 1)
	@Schema(description = "Beneficiary name", example = "string", required = true)
	@NotBlank
	@Size(min = 0, max = 300)
	private TypeDetailsILOC typeDetailsILOC;
	@JsonProperty(value = "amountsAndPaymentsLOGT", required = true, index = 1)
	@Schema(description = "Beneficiary name", example = "string", required = true)
	@NotBlank
	@Size(min = 0, max = 300)
	private AmountsAndPaymentsILOC amountsAndPaymentsILOC;
	@JsonProperty(value = "name", required = true, index = 1)
	@Schema(description = "Beneficiary name", example = "string", required = true)
	@NotBlank
	@Size(min = 0, max = 300)
	private ShipmentAndExpirationDetails shipmentAndExpirationDetails;
	@JsonProperty(value = "name", required = true, index = 1)
	@Schema(description = "Beneficiary name", example = "string", required = true)
	@NotBlank
	@Size(min = 0, max = 300)
	private DocumentDetails documentDetails;
	
}
