package com.telecom.provider;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.telecom.provider.entity.PhoneNumber;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TelecomProviderIntegrationTest {
	@Autowired
	private TestRestTemplate restTemplate;

	@Before
	public void initDb() {
		// CustomerId 1 has One PhoneNumber
		HttpEntity<PhoneNumber> request1 = new HttpEntity<>(new PhoneNumber("1111", 1));
		restTemplate.postForObject(UtilTest.PHONENUMBERS_URL, request1, PhoneNumber.class);

		// CustomerId 2 has Two PhoneNumbers
		HttpEntity<PhoneNumber> request2 = new HttpEntity<>(new PhoneNumber("2222", 2));
		restTemplate.postForObject(UtilTest.PHONENUMBERS_URL, request2, PhoneNumber.class);

		HttpEntity<PhoneNumber> request3 = new HttpEntity<>(new PhoneNumber("3333", 2));
		restTemplate.postForObject(UtilTest.PHONENUMBERS_URL, request3, PhoneNumber.class);
	}

	@Test
	public void getAllPhonenumbers_theTelecomProviderHasThreePhoneNumbers() throws Exception {
		ResponseEntity<List> response = restTemplate.getForEntity(UtilTest.PHONENUMBERS_URL, List.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody().size()).isEqualTo(3);
	}

	@Test
	public void getAllPhonenumbersByCustomerId_customerId1HasOnePhoneNumber() throws Exception {
		Map<String, String> params = new HashMap<>();
		params.put("customerId", "1");
		ResponseEntity<List> response = restTemplate
				.getForEntity(UtilTest.PHONENUMBERS_URL + "?customerId={customerId}", List.class, params);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody().size()).isEqualTo(1);
	}

	@Test
	public void getAllPhonenumbersByCustomerId_customerId2HasTwoPhoneNumbers() throws Exception {
		Map<String, String> params = new HashMap<>();
		params.put("customerId", "2");
		ResponseEntity<List> response = restTemplate
				.getForEntity(UtilTest.PHONENUMBERS_URL + "?customerId={customerId}", List.class, params);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody().size()).isEqualTo(2);
	}

	@Test
	public void getAllPhonenumbersByCustomerId_customerId3HasNoPhoneNumbers() throws Exception {
		Map<String, String> params = new HashMap<>();
		params.put("customerId", "3");
		ResponseEntity<List> response = restTemplate
				.getForEntity(UtilTest.PHONENUMBERS_URL + "?customerId={customerId}", List.class, params);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(response.getBody()).isNull();
	}
}
