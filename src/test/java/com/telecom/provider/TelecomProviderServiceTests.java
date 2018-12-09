package com.telecom.provider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.telecom.provider.entity.PhoneNumber;
import com.telecom.provider.repository.TelecomProviderRepository;
import com.telecom.provider.service.CustomerNotFoundException;
import com.telecom.provider.service.TelecomProviderService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class TelecomProviderServiceTests {

	private TelecomProviderService service;

	@Mock
	private TelecomProviderRepository telecomProviderRepository;

	@Before
	public void init() {
		service = new TelecomProviderService(telecomProviderRepository);
	}

	@Test
	public void getPhonenumbers_shouldFindPhoneNumbers() {
		List<PhoneNumber> expectedResult = UtilTest.mockPhoneNumbers();
		given(telecomProviderRepository.findAll()).willReturn(expectedResult);
		List<PhoneNumber> phonenumbers = service.getPhonenumbers();

		assertThat(phonenumbers.size()).isEqualTo(1);
		assertThat(phonenumbers.get(0).getNumber()).isEqualTo("123456");
		assertThat(phonenumbers.get(0).getCustomerId()).isEqualTo(1);
		verify(telecomProviderRepository, times(1)).findAll();
	}

	@Test
	public void activatePhonenumber_shouldSaveThePhoneNumber() {
		PhoneNumber phoneNumber = UtilTest.mockPhoneNumbers().get(0);
		service.createPhonenumber(phoneNumber);

		verify(telecomProviderRepository, times(1)).save(phoneNumber);
	}

	@Test
	public void getPhonenumbersByCustomerId_shouldFindPhoneNumbers() {
		final long customerId = 1;
		List<PhoneNumber> expectedResult = UtilTest.mockPhoneNumbers();
		given(telecomProviderRepository.findPhoneNumbersByCustomerId(customerId)).willReturn(expectedResult);
		List<PhoneNumber> phonenumbers = service.getPhonenumbersByCustomerId(customerId);

		assertThat(phonenumbers.size()).isEqualTo(1);
		assertThat(phonenumbers.get(0).getNumber()).isEqualTo("123456");
		assertThat(phonenumbers.get(0).getCustomerId()).isEqualTo(1);
		verify(telecomProviderRepository, times(1)).findPhoneNumbersByCustomerId(customerId);
	}

	@Test(expected = CustomerNotFoundException.class)
	public void getPhonenumbersByCustomerId_whenPhoneNumbersAreNotFound() throws Exception {
		final long customerId = 1;
		given(telecomProviderRepository.findPhoneNumbersByCustomerId(customerId)).willReturn(UtilTest.mockEmptyPhoneNumbers());
		service.getPhonenumbersByCustomerId(customerId);
	}
}
