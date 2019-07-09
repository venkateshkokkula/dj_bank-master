package com.dhanjyothi.service.impl;

import java.util.Base64;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dhanjyothi.dao.AccountDao;
import com.dhanjyothi.model.Account;
import com.dhanjyothi.model.Beneficiaries;
import com.dhanjyothi.model.User;
import com.dhanjyothi.model.Transaction;
import com.dhanjyothi.service.AccountService;
import com.dhanjyothi.util.DhanJyothiUtil;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountDao accountDao;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private DhanJyothiUtil djUtil;

	public Account getAccountDetails(int custId, String accType)
			throws Exception {
		return accountDao.getAccountDetails(custId, accType);
	}

	public void openSavingsAccount(User user) throws Exception {
		Account acc = new Account();
		acc.setAccountType("SAVINGS");
		acc.setAccountBalance(10000);
		acc.setAccountCreatedDate(djUtil.getCurrentDate());
		acc.setUser(user);
		accountDao.openSavingsAccount(acc, false);
	}

	public void openTermAccount(Account account, User cust)
			throws Exception {
		account.setAccountType("TERM");
		account.setUser(cust);
		account.setAccountCreatedDate(djUtil.getCurrentDate());
		//account.setMatureDate(djUtil.getTermMaturityDate(account.getDeposit_tenure()
		account.setInterestRate(djUtil.getInterstDate(account.getDepositTenure(),
				account.getMaturityAmount()));
		Transaction trans = new Transaction();
		trans.setTransactionType("Debit");
		trans.setTransactionDescription("Amount Debited for Term Account");
		trans.setTransactionDate(djUtil.getCurrentDate());
		trans.setTransactionAmount(account.getMaturityAmount());
		accountDao.openTermAccount(account);
	}

	public List<Account> getTermAccountDetails(int custId, String accType)
			throws Exception {
		return accountDao.getTermAccountDetails(custId, accType);
	}

	public Map<Integer, String> getTenureDetails() {
		return djUtil.getTenureDetails();
	}

	public boolean checkSavingsAccBalance(long termAmt) throws Exception {
		HttpSession ses = request.getSession();
		User cust = (User) ses.getAttribute("cust");
		if (cust != null) {
			Account savingsAcc = accountDao.getAccountDetails(cust.getUserId(),
					"SAVINGS");
			if (savingsAcc != null) {
				if (savingsAcc.getAccountBalance() >= termAmt) {
					return true;
				} else {
					return false;
				}

			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public void updateSavingsAccount(Account account, User cust)
			throws Exception {
		long savingsAccBalance = 0;
		account.setAccountType("SAVINGS");
		account.setUser(cust);
		account.setAccountUpdatedDate(djUtil.getCurrentDate());
		Account savingsAcc = accountDao.getAccountDetails(cust.getUserId(),
				"SAVINGS");
		if (savingsAcc != null) {
			savingsAccBalance = djUtil.getAccBalance(
					savingsAcc.getAccountBalance(), account.getMaturityAmount());
			account.setAccountBalance(savingsAccBalance);
			account.setAccountId(savingsAcc.getAccountId());
			account.setAccountCreatedDate(savingsAcc.getAccountCreatedDate());
			accountDao.openSavingsAccount(account, true);
			Transaction trans = new Transaction();
			trans.setTransactionType("Debit");
			trans.setTransactionDate(djUtil.getCurrentDate());
			trans.setTransactionDescription("Self Transfer Term Account Creation");
			trans.setTransactionAmount(account.getMaturityAmount());
			trans.setAccount(account);
			accountDao.updateTransactionDetails(trans);
		} else {
			throw new Exception();
		}

	}

	public User getCustomerDetails(User cust) throws Exception {
		return accountDao.getCustomerDetails(cust);
	}

	public void saveBeneficiaries(Account acc, Beneficiaries ben)
			throws Exception {
		ben.setAccount(acc);
		accountDao.saveBeneficiaries(ben);

	}

	public boolean checkAccountExists(Beneficiaries ben) throws Exception {
		Account acc;
		System.out.println("Payee Acc Num:" + ben.getBeneficiaryAccountNumber());
		if (ben.getBeneficiaryAccountNumber() != 0) {
			acc = accountDao.checkAccountExists(ben.getBeneficiaryAccountNumber());
			System.out.println("Account"+acc);
		} else {
			return false;
		}
		if (acc.getAccountId() != 0 && acc.getAccountType().equalsIgnoreCase("Savings")) {
			System.out.println("Entered inti second block of code");
			return true;
		} else {
			return false;
		}
	}

	public List<Beneficiaries> getAllBeneficiaries(int accId) throws Exception {
		return accountDao.getAllBeneficiaries(accId);
	}


	public void updateFromAccount(Account account, long transAmt,Transaction trans) throws Exception {
		long savingsAccBalance = 0;
		System.out.println("Entered into fromaccount");
		savingsAccBalance = djUtil.getAccBalance(
				account.getAccountBalance(), transAmt);
				account.setAccountBalance(savingsAccBalance);
				account.setAccountUpdatedDate(djUtil.getCurrentDate());
		accountDao.openSavingsAccount(account, true);
		Beneficiaries ben = trans.getBenificiary();
		System.out.println("Beneficiary Type"+ben.getBeneficiaryType());
		trans.setTransactionType("Debit");
		trans.setTransactionDate(djUtil.getCurrentDate());
		trans.setAccount(account);
		accountDao.updateTransactionDetails(trans);
		
	}

	public void updateToAccount(Transaction trans)
			throws Exception {
		System.out.println("trans.getBenificiary().getPayeeAccNum()==="+trans.getBenificiary().getBeneficiaryAccountNumber());
		Account acc = accountDao.getAccountDetails(trans.getBenificiary().getUser().getUserId(), "SAVINGS");
		System.out.println("Payee acc =="+acc.getAccountId());
		long savingsAccBalance = 0;
		savingsAccBalance = djUtil.addAccBalance(
				acc.getAccountBalance(), trans.getTransactionAmount());
		System.out.println("Balance Update"+acc.getAccountBalance()+" "+trans.getTransactionAmount()+" "+savingsAccBalance);
		acc.setAccountBalance(savingsAccBalance);
		acc.setAccountUpdatedDate(djUtil.getCurrentDate());
		accountDao.openSavingsAccount(acc, true);
		Beneficiaries ben = trans.getBenificiary();
		System.out.println("Beneficiary Type"+ben.getBeneficiaryType());
		trans.setTransactionType("Credit");
		trans.setTransactionDate(djUtil.getCurrentDate());
		trans.setAccount(acc);
		accountDao.updateTransactionDetails(trans);
	}

	public List<Transaction> loadAllTransactions(int accId) throws Exception {
		return accountDao.loadAllTransactions(accId);
	}

	public boolean isUserNameExists(String name) throws Exception {
		User cust = accountDao.isUserNameExists(name);
		if(null == cust) {
			return true;
		} else {
			return false;
		}
	}
	public Account checkAccountExists(int accId) throws Exception{
		return accountDao.checkAccountExists(accId);
		
	}

	public User getCustomerById(int custID) throws Exception {
		// TODO Auto-generated method stub
		return accountDao.getCustomerById(custID);
	}
}
