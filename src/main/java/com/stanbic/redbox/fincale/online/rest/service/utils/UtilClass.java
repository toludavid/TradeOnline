package com.stanbic.redbox.fincale.online.rest.service.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stanbic.redbox.fincale.online.rest.service.config.PropertiesConfig;
import com.stanbic.redbox.fincale.online.rest.service.dto.SmsRequest;
import com.stanbic.redbox.fincale.online.rest.service.dto.TokenResponse;
import com.stanbic.redbox.fincale.online.rest.service.dto.User;
import com.stanbic.redbox.fincale.online.rest.service.exception.AuthorizationException;
import com.stanbic.redbox.fincale.online.rest.service.exception.InvalidInputException;

import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

@Component
@Slf4j
public class UtilClass {

	public static String writeObjectAsJsonString(Object object) {
		ObjectMapper mapper = new ObjectMapper();

		String jsonString = "";
		try {
			mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
			jsonString = mapper.writeValueAsString(object);

		} catch (JsonProcessingException e) {
			return null;
		}
		return jsonString;
	}

	public static boolean isValidPhoneNumber(String phonenumber) {
		// Regex to check valid phonenumber
		String regex = "^[+]{1}(?:[0-9\\-\\(\\)\\/\\.]\\s?){6,15}[0-9]{1}$";
		// Compile the ReGex
		Pattern p = Pattern.compile(regex);

		// If the phonenumber
		// is empty return false
		if (phonenumber == null) {
			return false;
		}

		
		// Pattern class contains matcher()
		// method to find matching between
		// given phone number using regex
		Matcher m = p.matcher(phonenumber);


		// Return if the phonenumber
		// matched the ReGex
		if ( m.matches() && phonenumber.length()==14) {
			return true;
		}
				 	
		
		return false;
	}

	public static boolean isValidCif(String cif) {
		return (cif != null && cif.length() == 9);
	}

	public static void validateRequest(String cif, HttpServletRequest req) {
		String authorizationHeader = req.getHeader("Authorization");
		log.info("authorization header {}", authorizationHeader);
		if (authorizationHeader == null)
			throw new AuthorizationException("Authorization is required to access this resource");
		if (!isValidCif(cif))
			throw new InvalidInputException("9 digits CIF Id Should be supplied");
	}

	public static void validateSmsRequest(SmsRequest smsRequest, HttpServletRequest req) {
		String authorizationHeader = req.getHeader("Authorization");
		log.info("authorization header {}", authorizationHeader);
		if (authorizationHeader == null)
			throw new AuthorizationException("Authorization is required to access this resource");
		if (!isValidPhoneNumber((smsRequest.getTo())))
			throw new InvalidInputException("Invalid Phone Number Supplied");
	}

	@Autowired
	private PropertiesConfig propertiesConfig;

	JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();

	/*
	 * // @PostMapping("/generate-token") public ResponseEntity<?>
	 * generateToken(@RequestBody User user) { // For simplicity, we are not
	 * performing any real authentication here. // You should validate the username
	 * and password against your user database. Not // present For Now if
	 * ((propertiesConfig.getTokenName().equals(user.getClientId()) &&
	 * propertiesConfig.getPassword().equals(user.getClientSecret())) ||
	 * (propertiesConfig.getTokenNameAdmin().equals(user.getClientId()) &&
	 * propertiesConfig.getPasswordAdmin().equals(user.getClientSecret()))) { //
	 * String token = jwtTokenUtil.generateToken(user.getClientId()); String token =
	 * "123452"; TokenResponse tokenResponse = new TokenResponse();
	 * tokenResponse.setToken(token); tokenResponse.setTokenType("Bearer");
	 * tokenResponse.setExpiresIn(TimeManipulation.getFutureTimeInMillis(1800000));
	 * 
	 * return ResponseEntity.ok(tokenResponse); } else { throw new
	 * AuthorizationException("Invalid credentials");
	 * 
	 * } }
	 */

	public static String getHostIP() {
		InetAddress ip;
		try {
			ip = InetAddress.getLocalHost();
			return ip.getHostAddress();
		} catch (UnknownHostException e) {
			log.debug("Error retrieving IP address processing node...");
		}
		return "";
	}

	public static String getRemoteAddress() {
	    RequestAttributes attribs = RequestContextHolder.getRequestAttributes();
	    if (attribs instanceof NativeWebRequest) {
	        HttpServletRequest request = (HttpServletRequest) ((NativeWebRequest) attribs).getNativeRequest();
	        return request.getRemoteAddr();
	    }
	    return null;
	}
	


	public static void main(String[] args) {

		WebUtils webUtilss = new WebUtils();
		// Test Case 1:
		String str1 = "+2349136812895";
		System.out.println(isValidPhoneNumber(str1));

		// Test Case 2:
		String str2 = "+91 9136812895";
		System.out.println(isValidPhoneNumber(str2));

		// Test Case 3:
		String str3 = "+123 123456";
		System.out.println(isValidPhoneNumber(str3));

		// Test Case 4:
		String str4 = "654294563";
		System.out.println(isValidPhoneNumber(str4));

		System.out.println(getHostIP());

		System.out.println("webUtilss == " + getRemoteAddress());

	}
}