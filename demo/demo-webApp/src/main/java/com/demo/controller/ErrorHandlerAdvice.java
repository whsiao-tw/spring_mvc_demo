package com.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.demo.data.DemoGenericException;

@ControllerAdvice
public class ErrorHandlerAdvice {

	@ExceptionHandler
	@ResponseStatus(value=HttpStatus.NOT_ACCEPTABLE) // Error. 406
	@ResponseBody
	public Map<String, Object> ExceptionHandler(DemoGenericException ex) {
		
		final Map<String, Object> map = new HashMap<String, Object>();
		map.put("errCode", ex.getErrCode());
		map.put("errMsg", ex.getErrMsg());
		
		return map;
	}
}
