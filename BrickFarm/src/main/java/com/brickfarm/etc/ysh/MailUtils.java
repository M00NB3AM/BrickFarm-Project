package com.brickfarm.etc.ysh;

import java.io.UnsupportedEncodingException;

import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class MailUtils {
	private JavaMailSender mailSender; //email 전송 기능의 기본이 되는 interface
	private MimeMessage message;
	private MimeMessageHelper messageHelper; //MimeMessage를 만들 수 있는 클래스, 텍스트,img 등 html 형식으로 제공
	
	public MailUtils(JavaMailSender mailSender) throws MessagingException {
		this.mailSender = mailSender;
		message = this.mailSender.createMimeMessage();
		messageHelper = new MimeMessageHelper(message, true, "UTF-8");
	}
	
	 // 메일의 제목
	public void setSubject(String subject) throws MessagingException {
		messageHelper.setSubject(subject);
	}
	
	//메일 내용
	public void setText(String htmlContent) throws MessagingException {
		messageHelper.setText(htmlContent, true);
	}
	
	//발신인
	public void setFrom(String email, String name) throws UnsupportedEncodingException, MessagingException {
		messageHelper.setFrom(email, name);
	}
	
	//수신인
	public void setTo(String email) throws MessagingException {
		messageHelper.setTo(email);
	}
	
	//이미지도 삽입 가능.
	public void addInline(String contentId, DataSource dataSource) throws MessagingException {
		messageHelper.addInline(contentId, dataSource);
	}
	
	//mail 전송
	public void send() {
		mailSender.send(message);
	}
	
	
	
}
