package com.brickfarm.auth;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.brickfarm.vo.user.ysh.message.MessageDTO;
import com.brickfarm.vo.user.ysh.message.SmsRequestDTO;
import com.brickfarm.vo.user.ysh.message.SmsResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@Transactional
public class SmsService {

	private final String smsConfirmNum = createSmsKey();

	@Value("${naver-cloud-sms.accessKey}")
	private String serviceId;
	@Value("${naver-cloud-sms.secretKey}")
	private String accessKey;
	@Value("${naver-cloud-sms.serviceId}")
	private String secretKey;
	@Value("${naver-cloud-sms.phone}")
	private String phone;

	public SmsResponseDTO sendSms(MessageDTO messageDTO) throws JsonProcessingException, UnsupportedEncodingException,
			NoSuchAlgorithmException, InvalidKeyException, URISyntaxException {
		Long time = System.currentTimeMillis();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		headers.set("x-ncp-apigw-timestamp", time.toString());
		headers.set("x-ncp-iam-access-key", this.accessKey);
		String sig = makeSignature(time); // 암호화
		headers.set("x-ncp-apigw-signature-v2", sig);

		List<MessageDTO> messages = new ArrayList<>();
		messages.add(messageDTO);

		SmsRequestDTO request = SmsRequestDTO.builder().type("SMS").contentType("COMM").countryCode("82")
				.from(this.phone).content("[세환이가 보내는 인증번호] : [" + smsConfirmNum + "]를 입력해 주세요.").messages(messages)
				.build();

		// 바디를 JSON 형식으로 반환.
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonBody = objectMapper.writeValueAsString(request);

		// json바디오 헤더를 조립.
		HttpEntity<String> httpBody = new HttpEntity<>(jsonBody, headers);

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
		SmsResponseDTO smsResponse = restTemplate.postForObject(
				new URI("https://sens.apigw.ntruss.com/sms/v2/services/" + this.serviceId + "/messages"), httpBody,
				SmsResponseDTO.class);
//		SmsResponseDTO responseDTO = new SmsResponseDTO(smsConfirmNum);
		return smsResponse;

	}

	// 인증번호 만들기
	public static String createSmsKey() {
		StringBuffer key = new StringBuffer();
		Random rnd = new Random();

		for (int i = 0; i < 5; i++) { // 인증코드 5자리
			key.append((rnd.nextInt(10)));
		}
		return key.toString();
	}

	public String makeSignature(Long time)
			throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {

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
}