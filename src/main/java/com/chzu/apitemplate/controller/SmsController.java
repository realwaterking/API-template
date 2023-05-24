package com.chzu.apitemplate.controller;

import com.chzu.apitemplate.utils.SMSUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping(value = "/sms")
public class SmsController {

    @Resource
    private SMSUtils smsUtils;

    @PostMapping("/sendSms/")
    public void sendSms(@RequestParam String phone) {
         smsUtils.sendSms(phone);
    }

    // 如果用户请求了没有实现的路由，返回404错误
    @RequestMapping("*")
    @ResponseBody
    public ResponseEntity<String> fallbackHandler(HttpServletRequest request) {
        return new ResponseEntity<>("接口不存在：" + request.getRequestURI(), HttpStatus.NOT_FOUND);
    }
}
