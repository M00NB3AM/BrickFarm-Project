package com.brickfarm.controller.user.member.ysh;

import java.util.Map;
import java.util.Random;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.brickfarm.etc.ysh.MailUtils;

@Controller
@PropertySource("classpath:/env/mail.properties")
public class MailController {

	@Inject
	JavaMailSenderImpl mailSender;
	
	@Value("${mail.username}")
	private String emailId;

	@RequestMapping("/find")
	@ResponseBody
	public ResponseEntity<String> find_post(@RequestParam Map<String, Object> map, HttpServletRequest request,
			@RequestParam("email") String email, Model model) throws Exception {

		Random random = new Random();
		String strRandom = "";

		for (int i = 0; i < 6; i++) {
			strRandom += random.nextInt(10);
		}

		MailUtils sendMail = new MailUtils(mailSender);
		sendMail.setSubject("이메일 인증 코드입니다.");
		String message = new StringBuffer().append("<h1>[이메일 인증]발신전용(회신불가)<h1>").append("<p>인증번호 : ").append(strRandom)
				.append("</p>").toString();

		sendMail.setText(message);
		sendMail.setFrom(emailId, "관리자");
		sendMail.setTo(email);
		sendMail.send();
		System.out.println("인증번호 : " + strRandom);

		return new ResponseEntity<String>(strRandom, HttpStatus.OK);
	}
}
