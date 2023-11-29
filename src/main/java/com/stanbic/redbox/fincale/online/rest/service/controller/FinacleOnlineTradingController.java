package com.stanbic.redbox.fincale.online.rest.service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.stanbic.redbox.fincale.online.rest.service.FinacleOnlineTradingService;
import com.stanbic.redbox.fincale.online.rest.service.dto.AccountDetailsResponse;
import com.stanbic.redbox.fincale.online.rest.service.dto.CustomerDetailsCustomResponse;
import com.stanbic.redbox.fincale.online.rest.service.dto.CustomerDetailsResponse;
import com.stanbic.redbox.fincale.online.rest.service.dto.AccountDetailsCustomResponse;
import com.stanbic.redbox.fincale.online.rest.service.dto.FinacleTradingResponse;
import com.stanbic.redbox.fincale.online.rest.service.dto.KycStatus;
import com.stanbic.redbox.fincale.online.rest.service.dto.SendSmsRequest;
import com.stanbic.redbox.fincale.online.rest.service.dto.SmsRequest;
import com.stanbic.redbox.fincale.online.rest.service.exception.ResourceNotFoundException;
import com.stanbic.redbox.fincale.online.rest.service.exception.TradeOnlineException;
import com.stanbic.redbox.fincale.online.rest.service.utils.UtilClass;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Trade Online", description = "Finacle Trade Online API")
//@RequestMapping("/uat/redbox/services/trade/online/core-bankings")
@RequestMapping("/")
public class FinacleOnlineTradingController {

	private final FinacleOnlineTradingService finacleOnlineTradingService;

	@Operation(summary = "Send SMS Message", description = "Sends a message to a supplied phone number", tags = {
			"send-sms" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = FinacleTradingResponse.class))),
			@ApiResponse(responseCode = "202", description = "Invalid Phone Number Supplied", content = @Content(schema = @Schema(implementation = FinacleTradingResponse.class))),
			@ApiResponse(responseCode = "400", description = "Invalid Phone Number Supplied", content = @Content(schema = @Schema(implementation = TradeOnlineException.class), mediaType = "application/json")),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = TradeOnlineException.class), mediaType = "application/json")),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "404", description = "Operation Not Found"),
			@ApiResponse(responseCode = "500", description = "Error", content = @Content(schema = @Schema(implementation = TradeOnlineException.class), mediaType = "application/json")) })
	@RequestMapping(value = "/send-sms", produces = { "application/json" }, method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> sendSms(@RequestBody SmsRequest smsRequest, HttpServletRequest req) {
		UtilClass.validateSmsRequest(smsRequest, req);
		SendSmsRequest sendSmsRequest = new SendSmsRequest();
		sendSmsRequest.setRecipients(smsRequest.getTo());
		sendSmsRequest.setMessage(smsRequest.getText());
		FinacleTradingResponse response = finacleOnlineTradingService.sendSms(sendSmsRequest);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Operation(summary = "Fetch Customer Detail", description = "Fetch Customer Details with CIF", tags = {
			"getCustomerDetail" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = CustomerDetailsResponse.class), mediaType = "application/json")),
			@ApiResponse(responseCode = "204", description = "No Content", content = @Content(schema = @Schema(implementation = CustomerDetailsCustomResponse.class), mediaType = "application/json")),
			@ApiResponse(responseCode = "400", description = "Invalid CIF ID Supplied", content = @Content(schema = @Schema(implementation = TradeOnlineException.class), mediaType = "application/json")),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = TradeOnlineException.class), mediaType = "application/json")),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "404", description = "Operation Not Found"),
			@ApiResponse(responseCode = "500", description = "Error", content = @Content(schema = @Schema(implementation = TradeOnlineException.class), mediaType = "application/json")) })
	@RequestMapping(value = "/getCustomerDetail/{cif}", produces = { "application/json" }, method = RequestMethod.GET)
	public ResponseEntity<?> getCustomerDetail(@PathVariable String cif, HttpServletRequest req) {
		UtilClass.validateRequest(cif, req);
		CustomerDetailsResponse response = finacleOnlineTradingService.getCustomerDetail(cif);
		if (response == null || (response instanceof List && ((List<?>) response).isEmpty())) {
			throw new ResourceNotFoundException("Customer details not found for CIF: " + cif);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Operation(summary = "Fetch Account Detail", description = "Fetch Account Details with CIF", tags = {
			"getAccountDetail" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successful Operation", content = @Content(schema = @Schema(implementation = AccountDetailsResponse.class), mediaType = "application/json")),
			@ApiResponse(responseCode = "204", description = "No Content", content = @Content(schema = @Schema(implementation = AccountDetailsCustomResponse.class), mediaType = "application/json")),
			@ApiResponse(responseCode = "400", description = "Invalid CIF ID Supplied", content = @Content(schema = @Schema(implementation = TradeOnlineException.class), mediaType = "application/json")),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = TradeOnlineException.class), mediaType = "application/json")),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "404", description = "Operation Not Found"),
			@ApiResponse(responseCode = "500", description = "Error", content = @Content(schema = @Schema(implementation = TradeOnlineException.class), mediaType = "application/json")) })
	@RequestMapping(value = "/getAccountDetail/{cif}", produces = { "application/json" }, method = RequestMethod.GET)
	@GetMapping("/getAccountDetail/{cif}")
	public ResponseEntity<?> getAccountDetail(@PathVariable String cif, HttpServletRequest req) {
		UtilClass.validateRequest(cif, req);
		AccountDetailsResponse response = finacleOnlineTradingService.getAccountDetails(cif);
		if (response == null || (response instanceof List && ((List<?>) response).isEmpty())) {
			throw new ResourceNotFoundException("Account details not found for CIF: " + cif);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Operation(summary = "Fetch Account KYC Status", description = "Fetch Account KYC Status with CIF", tags = {
			"getKycStatus" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = FinacleTradingResponse.class))),
			@ApiResponse(responseCode = "400", description = "Invalid CIF ID Supplied", content = @Content(schema = @Schema(implementation = FinacleTradingResponse.class))),
			@ApiResponse(responseCode = "404", description = "Operation Not Found", content = @Content(schema = @Schema(implementation = FinacleTradingResponse.class))),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "404", description = "Operation Not Found"),
			@ApiResponse(responseCode = "500", description = "Error", content = @Content(schema = @Schema(implementation = TradeOnlineException.class), mediaType = "application/json")) })
	@RequestMapping(value = "/getKycStatus/{cif}", produces = { "application/json" }, method = RequestMethod.GET)
	@GetMapping("/getKycStatus/{cif}")
	public ResponseEntity<?> getKycStatus(@PathVariable String cif, HttpServletRequest req) {
		UtilClass.validateRequest(cif, req);
		KycStatus kycStatus = new KycStatus();
		kycStatus.setKycStatus(finacleOnlineTradingService.getKycStatus(cif));
		return new ResponseEntity<>(kycStatus, HttpStatus.OK);
	}
	
	
	
	

}
