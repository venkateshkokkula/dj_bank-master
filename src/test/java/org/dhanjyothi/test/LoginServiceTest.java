package org.dhanjyothi.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.dhanjyothi.model.User;
import com.dhanjyothi.service.LoginService;
import com.dhanjyothi.service.RegisterService;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestBeanConfig.class })
public class LoginServiceTest {

	@Autowired
	private LoginService loginService;
	
	@Autowired
	private RegisterService registerService;

	
	private User user;
	@Test
	public void testValidateCustomer(){
		user=new User();
		user.setUserName("laxman123");
		user.setPassword("Asdfasdf@1");
		registerService.saveRegister(user);
		List<User> list=registerService.getAllCustomers();
		for(User u:list){
			System.out.println(u.getUserName()+"::::"+u.getPassword());
		}
		
		int result=loginService.validateCustomer(user);
		System.out.println(";"+result);
		Assert.assertEquals(1, result);
		
	}
}
