package com.stanbic.redbox.fincale.online.rest.service.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessage {
    private LocalDate date;
    private String responseCode;
    private String responseDescription;
    private String errorDetails;
}
