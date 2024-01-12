package com.albaneroassignment.customerservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Date;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    @NotBlank
    @Size(min = 2, max = 50)
    private String name;
    @Email
    private String email;
    @Pattern(regexp = "\\d{10}")
    private String phone;
    @NotBlank
    private String address;
    @NotBlank
    private String companyName;
    @NotBlank
    private String industryType;
    @NotBlank
    private String customerStatus;
    @NotBlank
    private String accountManager;

    @Temporal(TemporalType.TIMESTAMP)
    private Date audit;

    public Long getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getIndustryType() {
        return industryType;
    }

    public Customer(Long customerId, String name, String email, String phone, String address, String companyName, String industryType, String customerStatus, String accountManager, Date audit) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.companyName = companyName;
        this.industryType = industryType;
        this.customerStatus = customerStatus;
        this.accountManager = accountManager;
        this.audit = audit;
    }

    public Customer() {
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setIndustryType(String industryType) {
        this.industryType = industryType;
    }

    public void setCustomerStatus(String customerStatus) {
        this.customerStatus = customerStatus;
    }

    public void setAccountManager(String accountManager) {
        this.accountManager = accountManager;
    }

    public void setAudit(Date audit) {
        this.audit = audit;
    }

    public String getCustomerStatus() {
        return customerStatus;
    }

    public String getAccountManager() {
        return accountManager;
    }

    public Date getAudit() {
        return audit;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", companyName='" + companyName + '\'' +
                ", industryType='" + industryType + '\'' +
                ", customerStatus='" + customerStatus + '\'' +
                ", accountManager='" + accountManager + '\'' +
                ", audit=" + audit +
                '}';
    }
}
