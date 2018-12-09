package com.telecom.provider.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.telecom.provider.entity.PhoneNumber;

public interface TelecomProviderRepository extends JpaRepository<PhoneNumber, Long> {
	List<PhoneNumber> findPhoneNumbersByCustomerId(long customerId);
}
