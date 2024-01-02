package com.brickfarm.controller.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import retrofit2.http.GET;


@RequestMapping("/user/*")
@Controller
public class SecurityController {
	
	
	/*
	 * @GetMapping("/user/member") public void doMember() {
	 * System.out.println("회원가입한 유저만 이용 가능한 페이지 입니다."); }
	 * 
	 * @GetMapping("/admin/") public void doAdmin() {
	 * System.out.println("관리자만 접근이 가능한 페이지 입니다."); }
	 */	
}
