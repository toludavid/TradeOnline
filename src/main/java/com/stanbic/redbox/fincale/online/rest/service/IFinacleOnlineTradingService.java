package com.stanbic.redbox.fincale.online.rest.service;


import com.stanbic.redbox.fincale.online.rest.service.dto.AccountDetailsResponse;
import com.stanbic.redbox.fincale.online.rest.service.dto.FinacleTradingResponse;
import com.stanbic.redbox.fincale.online.rest.service.dto.SendSmsRequest;

public interface IFinacleOnlineTradingService {
    FinacleTradingResponse sendSms(SendSmsRequest sendSmsRequest);

    Object getCustomerDetail(String accountNumber);

    Object getAccountDetails(String accountNumber);
   
    boolean getKycStatus(String accountNumber);

}
