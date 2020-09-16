package com.manishtraders.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CashDiscount {
    private String creditNo;
    private double creditAmt;
    private String creditDate;
    private double creditRemains;
    private String creditParticulars;
    private String debitNo;
    private double debitAmt;
    private String debitDate;
    private double debitRemains;
    private String debitParticulars;
    private long dayCount;
    private double cdPercent;
    private double cdAmount;
    private double cdApplicableAmount;
}


