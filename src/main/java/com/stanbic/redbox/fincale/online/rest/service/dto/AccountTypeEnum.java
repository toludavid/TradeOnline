package com.stanbic.redbox.fincale.online.rest.service.dto;

public enum AccountTypeEnum {

	ILOC("ILOC"), LOGT("LOGT"), CASH("CASH");

	final String value;

	private AccountTypeEnum(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}

	public static AccountTypeEnum fromValue(String value) {
		for (AccountTypeEnum e : AccountTypeEnum.values()) {
			if (e.value.equals(value)) {
				return e;
			}
		}
		throw new IllegalArgumentException(value);
	}

}