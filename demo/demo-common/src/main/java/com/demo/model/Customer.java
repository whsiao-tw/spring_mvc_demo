package com.demo.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection="CUSTOMER")
public class Customer implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8112692267184511162L; 

	@Id
	private String id;
	
	@Indexed
	@Field("NAME")
	private String name;
	
	@Field("ADDRESS")
	private String address;
	
	@Indexed
	@Field("PHONE")
	private String phone;
	
	@Indexed(unique=true)
	@Field("EMAIL")
	private String email;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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
