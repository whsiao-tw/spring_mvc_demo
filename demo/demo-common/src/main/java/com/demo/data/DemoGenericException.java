package com.demo.data;

import java.util.ArrayList;
import java.util.List;

public class DemoGenericException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2107416845082658890L;
	
	private String errCode;
	
	private List<String> errMsg = new ArrayList<String>();
	
	public DemoGenericException(DemoErrorMessage.ErrorCode errCode) {
		this.errCode = errCode.name();
		this.errMsg.add(errCode.toString());
	}

	public DemoGenericException(DemoErrorMessage.ErrorCode errCode, String errMsg) {
		this.errCode = errCode.name();
		this.errMsg.add(errCode.toString() + ":" + errMsg);
	}

	public DemoGenericException(DemoErrorMessage.ErrorCode errCode, List<String> errMsgs) {
		this.errCode = errCode.name();
		this.errMsg = errMsgs;
	}

	public DemoGenericException(String errCode, String errMsg) {
		this.errCode = errCode;
		this.errMsg.add(errMsg);
	}

	public DemoGenericException(String errCode, List<String> errMsgs) {
		this.errCode = errCode;
		this.errMsg = errMsgs;
	}
	
	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public List<String> getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(List<String> errMsg) {
		this.errMsg = errMsg;
	}
}
