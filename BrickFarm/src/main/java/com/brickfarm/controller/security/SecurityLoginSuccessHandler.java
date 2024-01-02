package com.brickfarm.controller.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/*import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;*/
/*
public class SecurityLoginSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		// TODO Auto-generated method stub
		
	}
*/
	/*
	 * @Override public void onAuthenticationSuccess(HttpServletRequest request,
	 * HttpServletResponse response, Authentication authentication) throws
	 * IOException, ServletException { System.out.println("security login Success");
	 * List<String> roleNames = new ArrayList<String>();
	 * 
	 * authentication.getAuthorities().forEach(authority -> {
	 * roleNames.add(authority.getAuthority()); });
	 * 
	 * System.out.println("security ROLE NAMES : " + roleNames);
	 * 
	 * if(roleNames.contains("ROLE_ADMIN")) { response.sendRedirect("/admin/index");
	 * return; } if(roleNames.contains("ROLE_MEMBER")) {
	 * response.sendRedirect("/user/index"); return; } response.sendRedirect("/"); }
	 */

//}
