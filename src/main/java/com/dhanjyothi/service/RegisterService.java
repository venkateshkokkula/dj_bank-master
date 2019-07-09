package com.dhanjyothi.service;

import java.util.List;

import com.dhanjyothi.model.User;



public interface RegisterService {

	public void saveRegister(User customer);
	public List<User> getAllCustomers();
}
