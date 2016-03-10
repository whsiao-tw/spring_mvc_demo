package com.demo.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

public class CustomerForm {
	
	@NotNull
	@Size(min=1)
	private String name;

	private String address;
	
	@NotNull
	@Size(min=8)
	@Pattern(regexp="(^$|[0-9]{8,10})")
	private String phone;
	
	@NotNull
	@Size(min=1)
	@Email(message="e-Mail 格式且不可為空")
	private String email;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
