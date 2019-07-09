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

import com.dhanjyothi.dao.AccountDao;
import com.dhanjyothi.model.Account;
import com.dhanjyothi.model.Beneficiaries;
import com.dhanjyothi.model.Transaction;
import com.dhanjyothi.model.User;
import com.dhanjyothi.service.AccountService;
import com.dhanjyothi.service.RegisterService;
import com.dhanjyothi.util.DhanJyothiUtil;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestBeanConfig.class })
public class AccountServiceTest {

	@Autowired
	private AccountService accountService;

	@Autowired
	private RegisterService registerService;

	@Autowired
	private AccountDao accountDao;

	@Autowired
	private DhanJyothiUtil djUtil;

	private User user;
	private Account account;
	private Transaction transaction;
	private Beneficiaries ben;

	@Before
	public void setup() {
		System.out.println(" Before method..");
		user = new User();
		account = new Account();
		transaction = new Transaction();
		ben=new Beneficiaries();
	}

	@Test
	public void test_getAccountDetails() throws Exception {
		System.out.println("GetAccountDetails method..");

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

		account.setAccountType("SAVINGS");
		account.setAccountBalance(10000);
		account.setAccountCreatedDate(djUtil.getCurrentDate());
		account.setUser(user);
		accountDao.openSavingsAccount(account, false);

		Account acc = accountService.getAccountDetails(user.getUserId(), account.getAccountType());
		System.out.println("Account ::" + acc);

		Assert.assertEquals(account.getAccountBalance(), acc.getAccountBalance());
		Assert.assertEquals(account.getAccountType(), acc.getAccountType());
		Assert.assertEquals(account.getAccountId(), acc.getAccountId());
		Assert.assertEquals(account.getAccountCreatedDate(), acc.getAccountCreatedDate());

		Assert.assertEquals(account.getUser().getUserId(), acc.getUser().getUserId());
		Assert.assertEquals(account.getUser().getFirstName(), acc.getUser().getFirstName());
		Assert.assertEquals(account.getUser().getLastName(), acc.getUser().getLastName());
		Assert.assertEquals(account.getUser().getDob(), acc.getUser().getDob());
		Assert.assertEquals(account.getUser().getEmailId(), acc.getUser().getEmailId());
		Assert.assertEquals(account.getUser().getAddressLine1(), acc.getUser().getAddressLine1());
		Assert.assertEquals(account.getUser().getAddressLine2(), acc.getUser().getAddressLine2());
		Assert.assertEquals(account.getUser().getCity(), acc.getUser().getCity());
		Assert.assertEquals(account.getUser().getState(), acc.getUser().getState());
		Assert.assertEquals(account.getUser().getAadharId(), acc.getUser().getAadharId());
		Assert.assertEquals(account.getUser().getMobileNumber(), acc.getUser().getMobileNumber());
		Assert.assertEquals(account.getUser().getPin(), acc.getUser().getPin());
		Assert.assertEquals(account.getUser().getPan(), acc.getUser().getPan());
		Assert.assertEquals(account.getUser().getUserName(), acc.getUser().getUserName());
		Assert.assertEquals(account.getUser().getPassword(), acc.getUser().getPassword());

		System.out.println("Object address not same :" + account + "=" + acc);
		Assert.assertNotSame(account.getUser(), acc.getUser());
		Assert.assertTrue("success", true);

	}

	@Test
	public void test_openSavingsAccount() throws Exception {
		System.out.println("OpenSavingsAccount method..");

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

		account.setAccountType("SAVINGS");
		account.setAccountBalance(10000);
		account.setAccountCreatedDate(djUtil.getCurrentDate());
		account.setUser(user);

		accountDao.openSavingsAccount(account, false);

		accountService.openSavingsAccount(user);
		Account acc = accountService.checkAccountExists(account.getAccountId());

		System.out.println("Account ::" + acc);

		Assert.assertEquals(account.getAccountBalance(), acc.getAccountBalance());
		Assert.assertEquals(account.getAccountType(), acc.getAccountType());
		Assert.assertEquals(account.getAccountId(), acc.getAccountId());
		Assert.assertEquals(account.getAccountCreatedDate(), acc.getAccountCreatedDate());

		Assert.assertEquals(account.getUser().getUserId(), acc.getUser().getUserId());
		Assert.assertEquals(account.getUser().getFirstName(), acc.getUser().getFirstName());
		Assert.assertEquals(account.getUser().getLastName(), acc.getUser().getLastName());
		Assert.assertEquals(account.getUser().getDob(), acc.getUser().getDob());
		Assert.assertEquals(account.getUser().getEmailId(), acc.getUser().getEmailId());
		Assert.assertEquals(account.getUser().getAddressLine1(), acc.getUser().getAddressLine1());
		Assert.assertEquals(account.getUser().getAddressLine2(), acc.getUser().getAddressLine2());
		Assert.assertEquals(account.getUser().getCity(), acc.getUser().getCity());
		Assert.assertEquals(account.getUser().getState(), acc.getUser().getState());
		Assert.assertEquals(account.getUser().getAadharId(), acc.getUser().getAadharId());
		Assert.assertEquals(account.getUser().getMobileNumber(), acc.getUser().getMobileNumber());
		Assert.assertEquals(account.getUser().getPin(), acc.getUser().getPin());
		Assert.assertEquals(account.getUser().getPan(), acc.getUser().getPan());
		Assert.assertEquals(account.getUser().getUserName(), acc.getUser().getUserName());
		Assert.assertEquals(account.getUser().getPassword(), acc.getUser().getPassword());

		System.out.println("Object address not same :" + account + "=" + acc);
		Assert.assertNotSame(account.getUser(), acc.getUser());
		Assert.assertTrue("success", true);

	}

	@Test
	public void test_openTermAccount() throws Exception {
		System.out.println("OpenTermAccount method..");

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

		account.setAccountType("SAVINGS");
		account.setAccountBalance(10000);
		account.setDepositTenure(2);
		account.setMaturityAmount(50000);
		account.setAccountCreatedDate(djUtil.getCurrentDate());
		account.setInterestRate(djUtil.getInterstDate(account.getDepositTenure(), account.getMaturityAmount()));
		account.setUser(user);

		transaction.setTransactionType("Debit");
		transaction.setTransactionDescription("Amount Debited for Term Account");
		transaction.setTransactionDate(djUtil.getCurrentDate());
		transaction.setTransactionAmount(account.getMaturityAmount());

		// accountDao.openTermAccount(account);
		accountService.openTermAccount(account, user);
		List<Account> accountList = accountService.getTermAccountDetails(user.getUserId(), account.getAccountType());

		for (Account acc : accountList) {
			System.out.println("Account ::" + acc);

			Assert.assertEquals(account.getDepositTenure(), acc.getDepositTenure());
			Assert.assertEquals(account.getMaturityAmount(), acc.getMaturityAmount());
			Assert.assertEquals(account.getAccountBalance(), acc.getAccountBalance());
			Assert.assertEquals(account.getAccountType(), acc.getAccountType());
			Assert.assertEquals(account.getAccountId(), acc.getAccountId());
			Assert.assertEquals(account.getAccountCreatedDate(), acc.getAccountCreatedDate());

			Assert.assertEquals(account.getUser().getUserId(), acc.getUser().getUserId());
			Assert.assertEquals(account.getUser().getFirstName(), acc.getUser().getFirstName());
			Assert.assertEquals(account.getUser().getLastName(), acc.getUser().getLastName());
			Assert.assertEquals(account.getUser().getDob(), acc.getUser().getDob());
			Assert.assertEquals(account.getUser().getEmailId(), acc.getUser().getEmailId());
			Assert.assertEquals(account.getUser().getAddressLine1(), acc.getUser().getAddressLine1());
			Assert.assertEquals(account.getUser().getAddressLine2(), acc.getUser().getAddressLine2());
			Assert.assertEquals(account.getUser().getCity(), acc.getUser().getCity());
			Assert.assertEquals(account.getUser().getState(), acc.getUser().getState());
			Assert.assertEquals(account.getUser().getAadharId(), acc.getUser().getAadharId());
			Assert.assertEquals(account.getUser().getMobileNumber(), acc.getUser().getMobileNumber());
			Assert.assertEquals(account.getUser().getPin(), acc.getUser().getPin());
			Assert.assertEquals(account.getUser().getPan(), acc.getUser().getPan());
			Assert.assertEquals(account.getUser().getUserName(), acc.getUser().getUserName());
			Assert.assertEquals(account.getUser().getPassword(), acc.getUser().getPassword());

			System.out.println("Object address not same :" + account + "=" + acc);
			Assert.assertNotSame(account.getUser(), acc.getUser());
			Assert.assertTrue("success", true);
		}

		Assert.assertEquals(1, accountList.size());

	}

	@Test
	public void test_getTermAccountDetails() throws Exception {
		System.out.println("GetTermAccountDetails method..");

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

		account.setAccountType("SAVINGS");
		account.setAccountBalance(10000);
		account.setDepositTenure(2);
		account.setMaturityAmount(50000);
		account.setAccountCreatedDate(djUtil.getCurrentDate());
		account.setInterestRate(djUtil.getInterstDate(account.getDepositTenure(), account.getMaturityAmount()));
		account.setUser(user);
		accountDao.openSavingsAccount(account, false);
		
		transaction.setTransactionType("Debit");
		transaction.setTransactionDescription("Amount Debited for Term Account");
		transaction.setTransactionDate(djUtil.getCurrentDate());
		transaction.setTransactionAmount(account.getMaturityAmount());

		accountService.openTermAccount(account, user);

		List<Account> accountList = accountService.getTermAccountDetails(user.getUserId(), account.getAccountType());

		for (Account acc : accountList) {
			System.out.println("Account ::" + acc);

			Assert.assertEquals(account.getDepositTenure(), acc.getDepositTenure());
			Assert.assertEquals(account.getMaturityAmount(), acc.getMaturityAmount());
			Assert.assertEquals(account.getAccountBalance(), acc.getAccountBalance());
			Assert.assertEquals(account.getAccountType(), acc.getAccountType());
			Assert.assertEquals(account.getAccountId(), acc.getAccountId());
			Assert.assertEquals(account.getAccountCreatedDate(), acc.getAccountCreatedDate());

			Assert.assertEquals(account.getUser().getUserId(), acc.getUser().getUserId());
			Assert.assertEquals(account.getUser().getFirstName(), acc.getUser().getFirstName());
			Assert.assertEquals(account.getUser().getLastName(), acc.getUser().getLastName());
			Assert.assertEquals(account.getUser().getDob(), acc.getUser().getDob());
			Assert.assertEquals(account.getUser().getEmailId(), acc.getUser().getEmailId());
			Assert.assertEquals(account.getUser().getAddressLine1(), acc.getUser().getAddressLine1());
			Assert.assertEquals(account.getUser().getAddressLine2(), acc.getUser().getAddressLine2());
			Assert.assertEquals(account.getUser().getCity(), acc.getUser().getCity());
			Assert.assertEquals(account.getUser().getState(), acc.getUser().getState());
			Assert.assertEquals(account.getUser().getAadharId(), acc.getUser().getAadharId());
			Assert.assertEquals(account.getUser().getMobileNumber(), acc.getUser().getMobileNumber());
			Assert.assertEquals(account.getUser().getPin(), acc.getUser().getPin());
			Assert.assertEquals(account.getUser().getPan(), acc.getUser().getPan());
			Assert.assertEquals(account.getUser().getUserName(), acc.getUser().getUserName());
			Assert.assertEquals(account.getUser().getPassword(), acc.getUser().getPassword());

			System.out.println("Object address not same :" + account + "=" + acc);
			Assert.assertNotSame(account.getUser(), acc.getUser());
		}
	}

	@Test
	public void test_checkSavingsAccBalance() throws Exception {
		System.out.println("CheckSavingsAccBalance method..");

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

		account.setAccountType("SAVINGS");
		account.setAccountBalance(10000);
		account.setDepositTenure(2);
		account.setMaturityAmount(500000);
		account.setAccountCreatedDate(djUtil.getCurrentDate());
		account.setInterestRate(djUtil.getInterstDate(account.getDepositTenure(), account.getMaturityAmount()));
		account.setUser(user);
	
		//accountDao.openSavingsAccount(account, false);
		accountService.openTermAccount(account, user);

		
		ben.setAccount(account);
		ben.setBeneficiaryAccountNumber(account.getAccountId());
		ben.setBeneficiaryBank("DhanJyothi Bank");
		ben.setBeneficiaryBankIfsc(1234);
		ben.setBeneficiaryName("laxman rao");
		ben.setBeneficiaryNickName("lucky");
		ben.setBeneficiaryType("WITHIN");
		ben.setUser(user);
		ben.setUserId(user.getUserId());
		
		transaction.setTransactionType("Debit");
		transaction.setTransactionDescription("Amount Debited for Term Account");
		transaction.setTransactionDate(djUtil.getCurrentDate());
		transaction.setTransactionAmount(account.getMaturityAmount());
		transaction.setAccount(account);
		transaction.setTransactionDescription("debit");
		transaction.setBenificiary(ben);
		System.out.println("="+transaction.getTransactionAmount());
		
		accountService.saveBeneficiaries(account, ben);
		
		//accountDao.openTermAccount(account);
		//accountService.openTermAccount(account, user);
		List<Beneficiaries> benList=accountService.getAllBeneficiaries(account.getAccountId());
		System.out.println("Beneficiaries list :"+benList);
		for(Beneficiaries b:benList){
			System.out.println(b.getAccount().getMaturityAmount());
		boolean flag=accountService.checkSavingsAccBalance(b.getAccount().getMaturityAmount());
		System.out.println("Flag value :"+flag);
		Assert.assertTrue(flag);
		}	
	}

	//@Test
	public void test_updateSavingsAccount() throws Exception {
		System.out.println("UpdateSavingsAccount method..");

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
		// registerService.saveRegister(user);

		account.setAccountType("SAVINGS");
		account.setAccountBalance(10000);
		account.setDepositTenure(2);
		account.setMaturityAmount(50000);
		account.setAccountCreatedDate(djUtil.getCurrentDate());
		account.setInterestRate(djUtil.getInterstDate(account.getDepositTenure(), account.getMaturityAmount()));
		account.setUser(user);


		accountService.updateSavingsAccount(account, user);

		Account acc = accountService.getAccountDetails(user.getUserId(), account.getAccountType());
		System.out.println("+++" +acc);
		
	}
	
	//@Test
	public void test_getCustomerDetails() throws Exception{
		System.out.println("GetCustomerDetails method..");

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
	
		User u=accountService.getCustomerDetails(user);
		System.out.println("User ::" +u);
		
		Assert.assertEquals(user.getUserId(), u.getUserId());
		Assert.assertEquals(user.getFirstName(), u.getFirstName());
		Assert.assertEquals(user.getLastName(), u.getLastName());
		Assert.assertEquals(user.getAddressLine1(), u.getAddressLine1());
		Assert.assertEquals(user.getAddressLine2(), u.getAddressLine2());
		Assert.assertEquals(user.getDob(), u.getDob());
		Assert.assertEquals(user.getEmailId(), u.getEmailId());
		Assert.assertEquals(user.getMobileNumber(), u.getMobileNumber());
		Assert.assertEquals(user.getAadharId(), u.getAadharId());
		Assert.assertEquals(user.getPan(), u.getPan());
		Assert.assertEquals(user.getPin(), u.getPin());
		Assert.assertEquals(user.getCity(), u.getCity());
		Assert.assertEquals(user.getState(), u.getState());
		Assert.assertEquals(user.getUserName(), u.getUserName());
		Assert.assertEquals(user.getPassword(), u.getPassword());
		Assert.assertNotSame(user, u);
		Assert.assertTrue("success",true);

	}
	
	@Test
	public void test_saveBeneficiaries() throws Exception{
		
		System.out.println("SaveBeneficiaries method..");

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
		
		account.setAccountType("SAVINGS");
		account.setAccountBalance(10000);
		account.setDepositTenure(2);
		account.setMaturityAmount(50000);
		account.setAccountCreatedDate(djUtil.getCurrentDate());
		account.setInterestRate(djUtil.getInterstDate(account.getDepositTenure(), account.getMaturityAmount()));
		account.setUser(user);
		accountDao.openSavingsAccount(account, false);
		
		ben.setAccount(account);
		ben.setBeneficiaryAccountNumber(account.getAccountId());
		ben.setBeneficiaryBank("DhanJyothi Bank");
		ben.setBeneficiaryBankIfsc(1234);
		ben.setBeneficiaryName("laxman rao");
		ben.setBeneficiaryNickName("lucky");
		ben.setBeneficiaryType("WITHIN");
		ben.setUser(user);
		ben.setUserId(user.getUserId());
		accountService.openTermAccount(account, user);

		accountService.saveBeneficiaries(account, ben);
		List<Beneficiaries> benList=accountService.getAllBeneficiaries(account.getAccountId());
		System.out.println(":::"+benList);
		
		for(Beneficiaries b:benList){
			Assert.assertEquals(ben.getBeneficiaryId(), b.getBeneficiaryId());
			Assert.assertEquals(ben.getBeneficiaryBank(), b.getBeneficiaryBank());
			Assert.assertEquals(ben.getBeneficiaryBankIfsc(), b.getBeneficiaryBankIfsc());
			Assert.assertEquals(ben.getBeneficiaryName(), b.getBeneficiaryName());
			Assert.assertEquals(ben.getBeneficiaryNickName(), b.getBeneficiaryNickName());
			Assert.assertEquals(ben.getBeneficiaryType(), b.getBeneficiaryType());
			Assert.assertEquals(ben.getBeneficiaryAccountNumber(), b.getBeneficiaryAccountNumber());
			System.out.println("Account address not same :"+ben.getAccount()+":"+b.getAccount());
			Assert.assertNotSame(ben.getAccount(), b.getAccount());
			Assert.assertEquals(ben.getAccount().getAccountId(), b.getAccount().getAccountId());
			Assert.assertEquals(ben.getAccount().getAccountBalance(), b.getAccount().getAccountBalance());
			Assert.assertEquals(ben.getAccount().getAccountType(), b.getAccount().getAccountType());
			Assert.assertEquals(ben.getAccount().getMaturityAmount(), b.getAccount().getMaturityAmount());
			Assert.assertEquals(ben.getAccount().getAccountCreatedDate(), b.getAccount().getAccountCreatedDate());
			Assert.assertEquals(ben.getAccount().getAccountUpdatedDate(), b.getAccount().getAccountUpdatedDate());
			Assert.assertEquals(ben.getAccount().getDepositTenure(), b.getAccount().getDepositTenure());
			Assert.assertEquals(ben.getUserId(), b.getUserId());
			System.out.println("User address not same :"+ben.getUser()+":"+b.getUser());
			Assert.assertNotSame(ben.getUser(), b.getUser());
			Assert.assertEquals(ben.getUser().getUserId(), b.getUser().getUserId());
			Assert.assertEquals(ben.getUser().getFirstName(), b.getUser().getFirstName());
			Assert.assertEquals(ben.getUser().getLastName(), b.getUser().getLastName());
			Assert.assertEquals(ben.getUser().getAddressLine1(), b.getUser().getAddressLine1());
			Assert.assertEquals(ben.getUser().getAddressLine2(), b.getUser().getAddressLine2());
			Assert.assertEquals(ben.getUser().getCity(), b.getUser().getCity());
			Assert.assertEquals(ben.getUser().getState(), b.getUser().getState());
			Assert.assertEquals(ben.getUser().getPin(), b.getUser().getPin());
			Assert.assertEquals(ben.getUser().getPan(), b.getUser().getPan());
			Assert.assertEquals(ben.getUser().getAadharId(), b.getUser().getAadharId());
			Assert.assertEquals(ben.getUser().getEmailId(), b.getUser().getEmailId());
			Assert.assertEquals(ben.getUser().getMobileNumber(), b.getUser().getMobileNumber());
			Assert.assertEquals(ben.getUser().getUserName(), b.getUser().getUserName());
			Assert.assertEquals(ben.getUser().getPassword(), b.getUser().getPassword());
		}		
		Assert.assertEquals(1, benList.size());
	}
	
	@Test
	public void test_checkAccountExists() throws Exception{
		
		System.out.println("CheckAccountExists method..");

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
		
		account.setAccountType("SAVINGS");
		account.setAccountBalance(10000);
		account.setDepositTenure(2);
		account.setMaturityAmount(50000);
		account.setAccountCreatedDate(djUtil.getCurrentDate());
		account.setInterestRate(djUtil.getInterstDate(account.getDepositTenure(), account.getMaturityAmount()));
		account.setUser(user);
		accountDao.openSavingsAccount(account, false);
		
		System.out.println(account.getAccountId()+":"+user.getUserId());
		ben.setAccount(account);
		ben.setBeneficiaryAccountNumber(account.getAccountId());
		ben.setBeneficiaryBank("DhanJyothi Bank");
		ben.setBeneficiaryBankIfsc(1234);
		ben.setBeneficiaryName("laxman rao");
		ben.setBeneficiaryNickName("lucky");
		ben.setBeneficiaryType("WITHIN");
		ben.setUser(user);
		ben.setUserId(user.getUserId());
		accountService.openTermAccount(account, user);
		accountService.saveBeneficiaries(account, ben);

		List<Beneficiaries> benList=accountService.getAllBeneficiaries(account.getAccountId());
		System.out.println("Beneficiaries list :"+benList);
		for(Beneficiaries b:benList){
		boolean flag=accountService.checkAccountExists(b);
		System.out.println("Flag value :"+flag);
		Assert.assertTrue(flag);
		}
		
	}

	@After
	public void teardown() {
		System.out.println(" After method..");

	}

}
