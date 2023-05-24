package com.chzu.apitemplate.utils;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class SMSUtils {

    @Autowired
    private RedisTemplate redisTemplate;

    public void sendSms(String phoneNumber) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI5tPzBcaD3QAkkNQ4d9rg", "RcMmZK1ZkwCgwE7p3fWHuGJIUSlQaP");
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("PhoneNumbers", phoneNumber);
        request.putQueryParameter("SignName", "小白杨");
        request.putQueryParameter("TemplateCode", "SMS_273765650");
        String code = String.valueOf(Math.round(Math.random() * 899999 + 100000));
        request.putQueryParameter("TemplateParam", "{\"code\":\"" + code + "\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println("短信发送成功" + code);

            redisTemplate.opsForValue().set(phoneNumber, code, 5, TimeUnit.MINUTES);
        } catch (ClientException e) {
            throw new RuntimeException("短信发送失败");
        }

    }


    public String getLastSmsCode(String phoneNumber) {
        return null;
    }
}
