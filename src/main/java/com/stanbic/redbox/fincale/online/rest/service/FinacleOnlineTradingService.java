package com.stanbic.redbox.fincale.online.rest.service;

import com.google.gson.Gson;
import com.stanbic.redbox.fincale.online.rest.service.dto.*;
import com.stanbic.redbox.fincale.online.rest.service.entity.FinacleAccountResponse;
import com.stanbic.redbox.fincale.online.rest.service.entity.FinacleDtdHtdTxnDataResponse;
import com.stanbic.redbox.fincale.online.rest.service.repository.DSXCustomerKYCStatusResponseRepository;
import com.stanbic.redbox.fincale.online.rest.service.repository.FinacleAccountRepository;
import com.stanbic.redbox.fincale.online.rest.service.repository.FinacleDataRepository;
import com.stanbic.redbox.fincale.online.rest.service.utils.UtilClass;
import com.stanbic.redbox.fincale.online.rest.service.utils.HttpClientRequestProcessor;
import com.stanbic.redbox.fincale.online.rest.service.utils.ObjectMapperUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FinacleOnlineTradingService implements IFinacleOnlineTradingService {
    @Value("${application.http.callout.use.proxy}")
    private boolean useProxy;
    @Value("${application.http.callout.proxy.host}")
    private String proxyHost;
    @Value("${sms_authorization}")
    private String authorization;
    @Value("${sms_module_id}")
    private String moduleId;
    @Value("${application.http.callout.proxy.port}")
    private int proxyPort;
    @Value("${application.http.callout.finacle.is.secured.protocol}")
    private boolean redboxRequestManagerIsSecuredProtocol;
    @Value("${application.http.callout.finacle.supported.protocol.list}")
    private String redboxRequestManagerSupportedProtocolList;
    @Value("${application.http.callout.proxy.authentication.flag}")
    private boolean proxyAuthenticationFlag;
    @Value("${application.http.callout.proxy.username}")
    private String proxyUsername;
    @Value("${application.http.callout.proxy.password}")
    private String proxyPassword;
    @Value("${application.http.callout.finacle.client.authentication.is.required}")
    private boolean redboxRequestManagerClientAuthenticationIsRequired;
    @Value("${application.http.callout.finacle.destination.server.username}")
    private String redboxRequestManagerDestinationServerUsername;

    @Value("${application.http.callout.finacle.destination.server.address}")
    private String redboxRequestManagerDestinationServerAddress;

    @Value("${application.http.callout.finacle.destination.server.password}")
    private String redboxRequestManagerDestinationServerPassword;

    @Value("${application.http.callout.finacle.destination.server.port}")
    private int redboxRequestManagerDestinationServerPort;
    @Value("${application.http.callout.finacle.http.method}")
    private String redboxRequestManagerHttpMethod;
    @Value("${application.http.callout.encoding}")
    private String encoding;
    @Value("${application.http.callout.get.http.response.status.line.from.http.response.string.flag}")
    private boolean getHttpResponseStatusLineFromHttpResponseStringFlag;
    @Value("${application.http.callout.finacle.socket.timeout}")
    private int redboxRequestManagerSocketTimeout;
    @Value("${application.http.callout.finacle.connection.timeout}")
    private int redboxRequestManagerConnectionTimeout;
    @Value("${application.http.callout.finacle.connection.request.timeout}")
    private int redboxRequestManagerConnectionRequestTimeout;
    @Value("${application.http.callout.get.http.response.header.from.http.response.string.flag}")
    private boolean getHttpResponseHeaderFromHttpResponseStringFlag;
    @Value("${application.string.list.item.separator}")
    private String stringListItemSeparator;
    @Value("${sms.url}")
    private String smsUrl;
    private final FinacleDataRepository finacleDataRepository;
    private final FinacleAccountRepository finacleAccountRepository;
    private final DSXCustomerKYCStatusResponseRepository dsxCustomerKYCStatusResponseRepository;

     
    
    
    @Override
    public FinacleTradingResponse sendSms(SendSmsRequest sendSmsRequest) {
        HashMap<String, Object> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization","basic "+authorization);
        headers.put("ModuleId",moduleId);
        String sendSmsString = UtilClass.writeObjectAsJsonString(sendSmsRequest);
        SendSmsResponse sendSmsResponse = new SendSmsResponse();
        FinacleTradingResponse finacleTradingResponse = new FinacleTradingResponse();
        try {

            String httpCallResponse = sendHttpRequest(headers, sendSmsString, smsUrl);
            if (!httpCallResponse.isEmpty()) {
                String responseBody = validateFromHttpClientRequestProcessor(httpCallResponse);
                Gson gson = new Gson();
                sendSmsResponse = gson.fromJson(responseBody, SendSmsResponse.class);
            }
            String responseCode = sendSmsResponse.getStatus();
            if ("200".equals(responseCode)) {
            	finacleTradingResponse.setResponseCode(responseCode);
            	finacleTradingResponse.setResponseMessage(HttpStatus.OK.getReasonPhrase());
                finacleTradingResponse.setMessage(sendSmsResponse.getMessage());
            } else {
                log.error("Sms was not successfully sent");
                finacleTradingResponse.setResponseCode(responseCode);
            	//finacleTradingResponse.setResponseMessage("");
                finacleTradingResponse.setMessage(sendSmsResponse.getMessage()); 
            }
        } catch (Exception exception){
            log.error("An Error occur while trying to send sms : "+exception.getMessage());
            throw exception;
        }
        return  finacleTradingResponse;

    }

    @Override
    public CustomerDetailsResponse getCustomerDetail(String cif) {
        CustomerDetailsResponse customerDetailsResponse = new CustomerDetailsResponse();
        Address address = new Address();
        FinacleTradingResponse finacleTradingResponse = new FinacleTradingResponse();
        CustomerDetailDto customerDetailDto = new CustomerDetailDto();
        List<FinacleDtdHtdTxnDataResponse> finacleDtdHtdTxnDataResponsee = finacleDataRepository.getFinacleDtdHtdTxnDataResponse(cif);
        if(finacleDtdHtdTxnDataResponsee != null && finacleDtdHtdTxnDataResponsee.size() > 0){
            FinacleDtdHtdTxnDataResponse finacleDtdHtdTxnDataResponse = finacleDtdHtdTxnDataResponsee.get(finacleDtdHtdTxnDataResponsee.size()-1);
            customerDetailsResponse.setCif(finacleDtdHtdTxnDataResponse.getCif());
            customerDetailsResponse.setClientName(finacleDtdHtdTxnDataResponse.getClientName());
            customerDetailsResponse.setGlobalCif(finacleDtdHtdTxnDataResponse.getGlobalCif());
            //customerDetailsResponse.setMarketSegment(finacleDtdHtdTxnDataResponse.getMarketSegment());
            customerDetailsResponse.setMarketSegmentCode(finacleDtdHtdTxnDataResponse.getMarketSegmentCode());
            
            customerDetailsResponse.setMarketSegmentDescription(MarketSegmentDescEnum.fromValue(finacleDtdHtdTxnDataResponse.getMarketSegment()));
            address.setAddressLine1(finacleDtdHtdTxnDataResponse.getAddressLine1());
            address.setAddressLine3(finacleDtdHtdTxnDataResponse.getAddressLine2());
            address.setAddressLine3(finacleDtdHtdTxnDataResponse.getAddressLine3());
            address.setCity(finacleDtdHtdTxnDataResponse.getCity());
            address.setPostalCode(finacleDtdHtdTxnDataResponse.getPostalCode());
            address.setCountryCode(finacleDtdHtdTxnDataResponse.getCountryCode());
            address.setProvince(finacleDtdHtdTxnDataResponse.getProvince());
            customerDetailsResponse.setAddress(address);
            log.info("database response is not null {}",customerDetailDto);
            return customerDetailsResponse;
            //BeanUtils.copyProperties(finacleDtdHtdTxnDataResponse,customerDetailDto); //redundant not needed
        }else{
            finacleTradingResponse.setResponseMessage("No record exist for Cif: "+cif);
            return null;
        }
    }

    @Override
    public AccountDetailsResponse getAccountDetails(String cif) {
        AccountDetailsResponse accountDetailsResponse = new AccountDetailsResponse();
        List<FinacleAccountResponse> finacleAccountResponse = finacleAccountRepository.getFinacleAccountResponse(cif);
        log.info("database finacleAccountResponse {}",Arrays.asList(finacleAccountResponse));

        if(finacleAccountResponse != null && finacleAccountResponse.size() > 0){
        	List<Account> accounts =   ObjectMapperUtils.mapAll(finacleAccountResponse, Account.class);
            log.info("account response is not null {}",accounts);

            accountDetailsResponse.setAccounts(accounts);
            return accountDetailsResponse;
        }else{
            return null;
        }
    }

    @Override
    public boolean getKycStatus(String cif) {
        if (dsxCustomerKYCStatusResponseRepository.getKycStatus(cif) == null)
            return false;
        return ("Y".equals(dsxCustomerKYCStatusResponseRepository.getKycStatus(cif)));
    }

    public String sendHttpRequest(HashMap<String, Object> headers, String requestString, String requestUrl) {
        return HttpClientRequestProcessor.sendHttpRequest(useProxy, proxyHost, proxyPort,
                redboxRequestManagerIsSecuredProtocol,
                redboxRequestManagerSupportedProtocolList.split(stringListItemSeparator), proxyAuthenticationFlag,
                proxyUsername, proxyPassword, redboxRequestManagerClientAuthenticationIsRequired,
                redboxRequestManagerDestinationServerUsername, redboxRequestManagerDestinationServerPassword,
                redboxRequestManagerDestinationServerAddress, redboxRequestManagerDestinationServerPort,
                redboxRequestManagerHttpMethod, headers, requestUrl, encoding, requestString,
                getHttpResponseStatusLineFromHttpResponseStringFlag, redboxRequestManagerSocketTimeout,
                redboxRequestManagerConnectionTimeout, redboxRequestManagerConnectionRequestTimeout,
                getHttpResponseHeaderFromHttpResponseStringFlag);
    }

    public String validateFromHttpClientRequestProcessor(String httpCallResponse) {
        //String httpStatusCode = HttpClientRequestProcessor.getHttpResponseStatusCodeFromStatusLine(HttpClientRequestProcessor.getHttpResponseStatusLineFromHttpResponseString(httpCallResponse));
        String responseBody = HttpClientRequestProcessor.getHttpResponseBodyFromHttpResponseString(httpCallResponse);
        return responseBody;
    }

    
    
    
    
}
