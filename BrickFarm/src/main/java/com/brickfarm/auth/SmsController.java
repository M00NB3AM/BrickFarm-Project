package com.brickfarm.auth;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.brickfarm.vo.user.ysh.message.MessageDTO;
import com.brickfarm.vo.user.ysh.message.SmsResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

@Controller
public class SmsController {
	@Inject
	private SmsService smsService;

//  public ResponseEntity<SmsResponse> test(@RequestParam("userPhone") String userPhone, @RequestParam("userPhone") String content) throws NoSuchAlgorithmException, URISyntaxException, UnsupportedEncodingException, InvalidKeyException, JsonProcessingException {
	@PostMapping("/user/sms")
	public SmsResponseDTO sendSms(@RequestBody MessageDTO messageDTO) throws JsonProcessingException, InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException, URISyntaxException{
		SmsResponseDTO responseDto = smsService.sendSms(messageDTO);
		// System.out.println("sms 번호는 : " + responseDto);
		return responseDto;
	}
}
