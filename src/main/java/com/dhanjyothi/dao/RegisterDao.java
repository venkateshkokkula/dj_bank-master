package com.dhanjyothi.dao;

import java.util.List;

import com.dhanjyothi.model.User;

public interface RegisterDao {
	public void saveRegister(User customer);

	public List<User> getAllCustomers();
}
