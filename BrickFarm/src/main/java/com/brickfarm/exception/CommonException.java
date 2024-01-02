package com.brickfarm.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CommonException {
	
	@ExceptionHandler(Exception.class)
	public String exceptionHandling(Exception e, Model model) {
		model.addAttribute("errorMsg", e.getMessage());
		model.addAttribute("errorStack", e.getStackTrace());
		
		return "./commonError";
	}
}
