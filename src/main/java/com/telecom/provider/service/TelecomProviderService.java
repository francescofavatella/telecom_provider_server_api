package com.telecom.provider.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.telecom.provider.entity.PhoneNumber;
import com.telecom.provider.repository.TelecomProviderRepository;

@Service
public class TelecomProviderService {

	private final TelecomProviderRepository telecomProviderRepository;
	
	public TelecomProviderService(TelecomProviderRepository telecomProviderRepository) {
		this.telecomProviderRepository = telecomProviderRepository;
	}

	public List<PhoneNumber> getPhonenumbers() {
		return telecomProviderRepository.findAll();
	}

	public void createPhonenumber(PhoneNumber phonenumber) {
		telecomProviderRepository.save(phonenumber);
	}

	public List<PhoneNumber> getPhonenumbersByCustomerId(long customerId) {
		List<PhoneNumber> phonenumbers = telecomProviderRepository.findPhoneNumbersByCustomerId(customerId);
		if(phonenumbers == null || phonenumbers.isEmpty()) {
			throw new CustomerNotFoundException();
		}
		return phonenumbers;
	}
}
