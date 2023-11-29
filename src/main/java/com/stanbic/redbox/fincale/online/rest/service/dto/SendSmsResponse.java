package com.stanbic.redbox.fincale.online.rest.service.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class SendSmsResponse {
    private String status;
    private String success;
    private String message;
    private String path;
    private String timestamp;
}
