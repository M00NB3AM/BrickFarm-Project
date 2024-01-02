package com.brickfarm.auth;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.brickfarm.dto.admin.kmh.AdminMessageDTO;
import com.brickfarm.dto.admin.kmh.AdminMessageReqeusetDTO;
import com.brickfarm.dto.admin.kmh.AdminMessageResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SendMessageService {

	@Value("${naver-cloud-sms.accessKey}")
	private String accessKey;

	@Value("${naver-cloud-sms.secretKey}")
	private String secretKey;

	@Value("${naver-cloud-sms.serviceId}")
	private String serviceId;

	@Value("${naver-cloud-sms.phone}")
	private String phone;

	public String makeSignature(Long time)
			throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {

		// System.out.println("sms보내기 1단계" + accessKey);

		String space = " ";
		String newLine = "\n";
		String method = "POST";
		String url = "/sms/v2/services/" + this.serviceId + "/messages";
		String timestamp = time.toString();
		String accessKey = this.accessKey;
		String secretKey = this.secretKey;

		String message = new StringBuilder().append(method).append(space).append(url).append(newLine).append(timestamp)
				.append(newLine).append(accessKey).toString();

		SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
		Mac mac = Mac.getInstance("HmacSHA256");
		mac.init(signingKey);

		byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
		String encodeBase64String = Base64.encodeBase64String(rawHmac);

		return encodeBase64String;
	}

	public AdminMessageResponseDTO sendSms(List<AdminMessageDTO> messageDto, String reverseTime) throws Exception {
		Long time = System.currentTimeMillis();
		// System.out.println(messageDto.toString());

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		headers.set("x-ncp-apigw-timestamp", time.toString());
		headers.set("x-ncp-iam-access-key", accessKey);
		headers.set("x-ncp-apigw-signature-v2", makeSignature(time));

		AdminMessageReqeusetDTO request = AdminMessageReqeusetDTO.builder()
//				.reserveTime(reverseTime)
				.type("SMS")
				.contentType("COMM")
				.countryCode("82")
				.from(phone)
				.content("Brick Farm에서 보내는 메시지입니다")
				.messages(messageDto)
				.build();
		
		ObjectMapper objectMapper = new ObjectMapper();
		String body = objectMapper.writeValueAsString(request);
		HttpEntity<String> httpBody = new HttpEntity<>(body, headers);

		// System.out.println(httpBody.toString());

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
		AdminMessageResponseDTO response = restTemplate.postForObject(
				new URI("https://sens.apigw.ntruss.com/sms/v2/services/" + serviceId + "/messages"), httpBody,
				AdminMessageResponseDTO.class);

		return response;
	}

}
