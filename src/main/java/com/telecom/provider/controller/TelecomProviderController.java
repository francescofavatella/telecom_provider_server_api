package com.telecom.provider.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import com.telecom.provider.entity.PhoneNumber;
import com.telecom.provider.service.CustomerNotFoundException;
import com.telecom.provider.service.TelecomProviderService;

@RestController
public class TelecomProviderController {

	private static final String PHONENUMBERS_URL = "/phonenumbers";

	private final TelecomProviderService service;

	public TelecomProviderController(TelecomProviderService service) {
		super();
		this.service = service;
	}

	@RequestMapping(value = PHONENUMBERS_URL, method = RequestMethod.GET)
	public List<PhoneNumber> getPhonenumbers() {
		return service.getPhonenumbers();
	}

	@RequestMapping(value = PHONENUMBERS_URL, method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public void activatePhonenumber(@Valid @RequestBody PhoneNumber payload) {
		service.createPhonenumber(payload);
	}

	@RequestMapping(value = PHONENUMBERS_URL, method = RequestMethod.GET, params = "customerId")
	public List<PhoneNumber> getPhonenumbersByCustomerId(@Valid @RequestParam long customerId) {
		return service.getPhonenumbersByCustomerId(customerId);
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.NOT_FOUND)
	private void customerNotFoundHandler(CustomerNotFoundException ex) {
	}
}
