package com.stanbic.redbox.fincale.online.rest.service.dto.finacletrade;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TradeOnlineSubmitLCRequest {
	private String applicationNumber;
	private String channelReference;
	private ProductILOC productILOC;
}
