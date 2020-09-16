package com.manishtraders.model;


public class NewVoucher {
    private String date;
    private String voucher;
    private String voucherNo;
    private Double debit;
    private Double credit;
    private String particulars;

    public NewVoucher() {
    }

    public NewVoucher(String date, String particulars, String voucher, String voucherNo, Double debit, Double credit) {
        this.date = date;
        this.particulars = particulars;
        this.voucher = voucher;
        this.voucherNo = voucherNo;
        this.debit = debit;
        this.credit = credit;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getParticulars() {
        return this.particulars;
    }

    public void setParticulars(String particulars) {
        this.particulars = particulars;
    }

    public String getVoucher() {
        return this.voucher;
    }

    public void setVoucher(String voucher) {
        this.voucher = voucher;
    }

    public String getVoucherNo() {
        return this.voucherNo;
    }

    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
    }

    public Double getDebit() {
        return this.debit;
    }

    public void setDebit(Double debit) {
        this.debit = debit;
    }

    public Double getCredit() {
        return this.credit;
    }

    public void setCredit(Double credit) {
        this.credit = credit;
    }
}


