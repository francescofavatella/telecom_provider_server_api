package com.telecom.provider;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.telecom.provider.controller.TelecomProviderController;
import com.telecom.provider.entity.PhoneNumber;
import com.telecom.provider.service.CustomerNotFoundException;
import com.telecom.provider.service.TelecomProviderService;

@RunWith(SpringRunner.class)
@WebMvcTest(TelecomProviderController.class)
public class TelecomProviderControllerTests {

	private final static String PHONENUMBER_PARAM_OK = "{\"number\":\"112233\", \"customerId\":\"1\"}";
	private final static String PHONENUMBER_PARAM_KO = "{\"number_WRONG\":\"112233\", \"customerId\":\"1\"}";

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TelecomProviderService service;

	@Test
	public void getAllPhonenumbers() throws Exception {
		List<PhoneNumber> phoneNumbers = UtilTest.mockPhoneNumbers();
		given(service.getPhonenumbers()).willReturn(phoneNumbers);
		this.mockMvc.perform(get(UtilTest.PHONENUMBERS_URL)).andExpect(status().isOk())
				.andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$", hasSize(1)));
		verify(service, times(1)).getPhonenumbers();
	}

	@Test
	public void activatePhonenumber_shouldBeOK() throws Exception {
		doNothing().when(service).createPhonenumber(any());
		this.mockMvc.perform(
				post(UtilTest.PHONENUMBERS_URL).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(PHONENUMBER_PARAM_OK))
				.andExpect(status().isCreated());
		verify(service, times(1)).createPhonenumber(any());
	}

	@Test
	public void activatePhonenumber_withWrongParamShouldReturnBadRequest() throws Exception {
		doNothing().when(service).createPhonenumber(any());
		this.mockMvc.perform(
				post(UtilTest.PHONENUMBERS_URL).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(PHONENUMBER_PARAM_KO))
				.andExpect(status().isBadRequest());
		verify(service, times(0)).createPhonenumber(any());
	}

	@Test
	public void getAllPhonenumbersOfACustomerId() throws Exception {
		List<PhoneNumber> phoneNumbers = UtilTest.mockPhoneNumbers();
		final long customerId = 1;
		given(service.getPhonenumbersByCustomerId(customerId)).willReturn(phoneNumbers);
		this.mockMvc.perform(get(UtilTest.PHONENUMBERS_URL).param("customerId", String.valueOf(customerId)))
				.andExpect(status().isOk())
				.andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$", hasSize(1)));
		verify(service, times(1)).getPhonenumbersByCustomerId(customerId);
	}

	@Test
	public void getAllPhonenumbersOfACustomerId_customerNotFound() throws Exception {
		final long customerId = 1;
		given(service.getPhonenumbersByCustomerId(anyLong())).willThrow(new CustomerNotFoundException());
		this.mockMvc.perform(get(UtilTest.PHONENUMBERS_URL).param("customerId", String.valueOf(customerId)))
				.andExpect(status().isNotFound());
		verify(service, times(1)).getPhonenumbersByCustomerId(customerId);
	}
}
