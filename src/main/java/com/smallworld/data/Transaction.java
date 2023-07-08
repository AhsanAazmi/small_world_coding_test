package com.smallworld.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Transaction {

    private Integer mtn;
    private Double amount;
    private String senderFullName;
    private Integer senderAge;
    private String beneficiaryFullName;
    private Integer beneficiaryAge;
    private Integer issueId;
    private Boolean issueSolved;
    private String issueMessage;
}
