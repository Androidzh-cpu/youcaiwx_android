package com.ucfo.youcai.entity.pay;

/**
 * Author: AND
 * Time: 2019-8-7.  下午 2:31
 * Package: com.ucfo.youcai.entity.pay
 * FileName: InvoiceInfoBean
 * Description:TODO 发票详情信息
 */
public class InvoiceInfoBean {
    private int invoiceType;
    private int invoiceForm;
    private String personName;
    private String companyName;
    private String companyNnumber;
    private String specialName;
    private String specialNumber;
    private String specialAddress;
    private String specialPhone;
    private String specialBank;
    private String specialBankNum;

    public String getPersonName() {
        return personName == null ? "" : personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getCompanyName() {
        return companyName == null ? "" : companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyNnumber() {
        return companyNnumber == null ? "" : companyNnumber;
    }

    public void setCompanyNnumber(String companyNnumber) {
        this.companyNnumber = companyNnumber;
    }

    public String getSpecialName() {
        return specialName == null ? "" : specialName;
    }

    public void setSpecialName(String specialName) {
        this.specialName = specialName;
    }

    public String getSpecialNumber() {
        return specialNumber == null ? "" : specialNumber;
    }

    public void setSpecialNumber(String specialNumber) {
        this.specialNumber = specialNumber;
    }

    public String getSpecialAddress() {
        return specialAddress == null ? "" : specialAddress;
    }

    public void setSpecialAddress(String specialAddress) {
        this.specialAddress = specialAddress;
    }

    public String getSpecialPhone() {
        return specialPhone == null ? "" : specialPhone;
    }

    public void setSpecialPhone(String specialPhone) {
        this.specialPhone = specialPhone;
    }

    public String getSpecialBank() {
        return specialBank == null ? "" : specialBank;
    }

    public void setSpecialBank(String specialBank) {
        this.specialBank = specialBank;
    }

    public String getSpecialBankNum() {
        return specialBankNum == null ? "" : specialBankNum;
    }

    public void setSpecialBankNum(String specialBankNum) {
        this.specialBankNum = specialBankNum;
    }

    public int getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(int invoiceType) {
        this.invoiceType = invoiceType;
    }

    public int getInvoiceForm() {
        return invoiceForm;
    }

    public void setInvoiceForm(int invoiceForm) {
        this.invoiceForm = invoiceForm;
    }
}
