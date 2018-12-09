package com.telecom.provider;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.telecom.provider.entity.PhoneNumber;
import com.telecom.provider.repository.TelecomProviderRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TelecomProviderRepositoryTests {

	@Autowired
	private TelecomProviderRepository repository;

	@Autowired
	private TestEntityManager entityManager;

	@Test
	public void findAll_returnPhonenumbers() {
		List<PhoneNumber> expectedPhonenumbers = UtilTest.mockPhoneNumbers();
		for (PhoneNumber phonenumber : expectedPhonenumbers) {
			entityManager.persistAndFlush(phonenumber);
		}

		List<PhoneNumber> phonenumbers = repository.findAll();
		assertThat(phonenumbers.size()).isEqualTo(expectedPhonenumbers.size());
	}
	
	@Test
	public void findPhoneNumbersByCustomerId_returnPhonenumbers() {
		long customerId = 1;
		List<PhoneNumber> expectedPhonenumbers = UtilTest.mockPhoneNumbers();
		for (PhoneNumber phonenumber : expectedPhonenumbers) {
			entityManager.persistAndFlush(phonenumber);
		}
		
		List<PhoneNumber> phonenumbers = repository.findPhoneNumbersByCustomerId(customerId);
		assertThat(phonenumbers.size()).isEqualTo(expectedPhonenumbers.size());
	}
	
	@Test
	public void findPhoneNumbersByCustomerId_whenThereArePhonenumbersForCustomerId() {
		long customerId = 2;
		List<PhoneNumber> expectedPhonenumbers = UtilTest.mockPhoneNumbers();
		for (PhoneNumber phonenumber : expectedPhonenumbers) {
			entityManager.persistAndFlush(phonenumber);
		}
		
		List<PhoneNumber> phonenumbers = repository.findPhoneNumbersByCustomerId(customerId);
		assertThat(phonenumbers.size()).isEqualTo(0);
	}
}
