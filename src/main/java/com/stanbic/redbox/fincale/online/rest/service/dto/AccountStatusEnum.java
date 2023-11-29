package com.stanbic.redbox.fincale.online.rest.service.dto;

public enum AccountStatusEnum {

	ACTIVE("ACTIVE"), 
	INACTIVE("ACTIVE");

	final String value;

	private AccountStatusEnum(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}

	public static AccountStatusEnum fromValue(String value) {
		for (AccountStatusEnum e : AccountStatusEnum.values()) {
			if (e.value.equals(value)) {
				return e;
			}
		}
		throw new IllegalArgumentException(value);
	}

}