package com.stanbic.redbox.fincale.online.rest.service.dto;

public enum MarketSegmentDescEnum {

	PBB("PBB"), CIB("CIB");

	final String value;

	private MarketSegmentDescEnum(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}

	public static MarketSegmentDescEnum fromValue(String value) {
		for (MarketSegmentDescEnum e : MarketSegmentDescEnum.values()) {
			if (e.value.equals(value)) {
				return e;
			}
		}
		throw new IllegalArgumentException(value);
	}

}