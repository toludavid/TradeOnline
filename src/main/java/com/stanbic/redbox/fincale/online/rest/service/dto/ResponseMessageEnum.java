package com.stanbic.redbox.fincale.online.rest.service.dto;

public enum ResponseMessageEnum {

	SUCCESS("SUCCESSFUL"), 
	FAILURE("FAILED"), 
	UNKNOWN("UNKNOWN"), 
	NOT_FOUND("The request resource was not found"),
	PROCESSING_ERROR("An error occured while processing your request, please review request payload and retry"),
	FINACLE_ERROR("Core-Banking Malfunctioned"),
	DATABASE_CONNECTION_ERROR("Unable To Established Database Connection Required For Transaction"),
	INAVLID_REQUEST("Request Payload or Header Is Invalid. Please Review Using Response Description"),
	DUPLICATE_REQUEST("Duplicate request received"), 
	CREATED("Created Successfully"), 
	UPDATE("Updated successfully");

	final String value;

	private ResponseMessageEnum(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}

	public static ResponseMessageEnum fromValue(String value) {
		for (ResponseMessageEnum e : ResponseMessageEnum.values()) {
			if (e.value.equals(value)) {
				return e;
			}
		}
		throw new IllegalArgumentException(value);
	}

}