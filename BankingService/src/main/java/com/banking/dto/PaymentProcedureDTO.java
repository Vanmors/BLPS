package com.banking.dto;

import lombok.Data;

@Data
public class PaymentProcedureDTO {
    private String numberOfCard;
    private String status;
    private Integer amount;
}
