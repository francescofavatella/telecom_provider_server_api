package com.telecom.provider;

import java.util.ArrayList;
import java.util.List;

import com.telecom.provider.entity.PhoneNumber;

public class UtilTest {
	public static final String PHONENUMBERS_URL = "/phonenumbers";
	
	public static List<PhoneNumber> mockPhoneNumbers(){
		List<PhoneNumber> phoneNumbers = new ArrayList<>();
    	phoneNumbers.add(new PhoneNumber("123456", 1));
    	return phoneNumbers;
	}
	
	public static List<PhoneNumber> mockEmptyPhoneNumbers(){
		List<PhoneNumber> phoneNumbers = new ArrayList<>();
		return phoneNumbers;
	}

}
