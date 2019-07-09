package org.dhanjyothi.test;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.dhanjyothi.model.User;
import com.dhanjyothi.service.RegisterService;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestBeanConfig.class })
public class RegisterServiceTest {

	@Autowired
	private RegisterService registerService;

	private User user;

	@Before
	public void setup() {
		System.out.println(" Before method..");
		user = new User();
	}

	@Test
	public void test_saveRegister() {

		System.out.println("Save method..");
		user.setFirstName("laxman");
		user.setLastName("rao");
		user.setAddressLine1("chn");
		user.setAddressLine2("sez");
		user.setAadharId("123412341234");
		user.setCity("chnn");
		user.setDob("10/06/1991");
		user.setEmailId("laxman@gmail.com");
		user.setMobileNumber("9999999999");
		user.setPan("ASDFG1234J");
		user.setPin("600042");
		user.setState("tamilnadu");
		user.setUserName("laxman123");
		user.setPassword("Asdfasdf@1");
		registerService.saveRegister(user);
		List<User> listUser = registerService.getAllCustomers();
		for (User u : listUser) {
			Assert.assertEquals(u.getFirstName(), user.getFirstName());
			Assert.assertEquals(u.getLastName(), user.getLastName());
			Assert.assertEquals(u.getDob(), user.getDob());
			Assert.assertEquals(u.getAddressLine1(), user.getAddressLine1());
			Assert.assertEquals(u.getAddressLine2(), user.getAddressLine2());
			Assert.assertEquals(u.getAadharId(), user.getAadharId());
			Assert.assertEquals(u.getCity(), user.getCity());
			Assert.assertEquals(u.getState(), user.getState());
			Assert.assertEquals(u.getEmailId(), user.getEmailId());
			Assert.assertEquals(u.getMobileNumber(), user.getMobileNumber());
			Assert.assertEquals(u.getPan(), user.getPan());
			Assert.assertEquals(u.getPin(), user.getPin());
			Assert.assertEquals(u.getUserName(), user.getUserName());
			Assert.assertEquals(u.getPassword(), user.getPassword());

		}
		Assert.assertEquals(1, listUser.size());

	}

	@Test
	public void test_GetAllCustomers() {

		System.out.println("GetAll method..");

		user.setFirstName("doredla");
		user.setLastName("rao");
		user.setAddressLine1("chn");
		user.setAddressLine2("sez");
		user.setAadharId("123412341234");
		user.setCity("chnn");
		user.setDob("10/06/1991");
		user.setEmailId("laxman@gmail.com");
		user.setMobileNumber("9999999999");
		user.setPan("ASDFG1234J");
		user.setPin("600042");
		user.setState("tamilnadu");
		user.setUserName("laxman123");
		user.setPassword("Asdfasdf@1");
		registerService.saveRegister(user);

		user.setFirstName("lucky");
		user.setLastName("dore");
		user.setAddressLine1("chn");
		user.setAddressLine2("sez");
		user.setAadharId("123412341234");
		user.setCity("chnn");
		user.setDob("10/06/1991");
		user.setEmailId("laxman@gmail.com");
		user.setMobileNumber("9999999999");
		user.setPan("ASDFG1234J");
		user.setPin("600042");
		user.setState("tamilnadu");
		user.setUserName("laxman1234");
		user.setPassword("Asdfasdf@1");
		registerService.saveRegister(user);

		List<User> userList = registerService.getAllCustomers();
		Assert.assertNotNull(userList);
		Assert.assertFalse(userList.size() > 0);

	}

	@After
	public void teardown() {
		System.out.println(" After method..");

	}

}
