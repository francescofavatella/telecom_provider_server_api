package com.telecom.provider.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class PhoneNumber {

    @Id
    @GeneratedValue
    private Long id;
    
    @NotNull
	private String number;
    
    @NotNull
	private long customerId;

	public PhoneNumber() {
	}
	public PhoneNumber(String number, long customerId) {
		this.number = number;
		this.customerId = customerId;
	}
	public String getNumber() {
		return number;
	}
	
	public long getCustomerId() {
		return customerId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}	
}