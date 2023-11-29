package com.stanbic.redbox.fincale.online.rest.service.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class FinacleTradingException extends RuntimeException {
	private String responseCode;
	private String responseDescription;
	private String errorDetails;
	public FinacleTradingException() {
		super();
		this.errorDetails = this.getMessage();
		this.responseDescription = this.getMessage();
	}
	public FinacleTradingException(String message) {
		super(message);
		this.errorDetails = this.getMessage();
		this.responseDescription = this.getMessage();
	}
	public FinacleTradingException(String responseCode, String responseDescription) {
		super(responseDescription);
		this.responseCode = responseCode;
		this.responseDescription = responseDescription;
	}
}
