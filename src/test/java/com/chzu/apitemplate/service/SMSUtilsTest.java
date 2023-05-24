package com.chzu.apitemplate.service;

import com.chzu.apitemplate.utils.SMSUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SMSUtilsTest {

    @Autowired
    private SMSUtils smsUtils;

    @Test
    public void testSendSms() throws Exception {
        String phoneNumber = "17775497320";
        smsUtils.sendSms(phoneNumber);
    }

}
