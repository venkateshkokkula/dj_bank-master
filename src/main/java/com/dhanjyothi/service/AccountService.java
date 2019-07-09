package com.dhanjyothi.service;

import java.util.List;
import java.util.Map;

import com.dhanjyothi.model.Account;
import com.dhanjyothi.model.Beneficiaries;
import com.dhanjyothi.model.User;
import com.dhanjyothi.model.Transaction;

public interface AccountService {

	User getCustomerDetails(User cust) throws Exception;

	Account getAccountDetails(int custId, String accType) throws Exception;

	void openSavingsAccount(User cust) throws Exception;

	void openTermAccount(Account account, User custr) throws Exception;

	List<Account> getTermAccountDetails(int custId, String accType)
			throws Exception;

	Map<Integer, String> getTenureDetails();

	boolean checkSavingsAccBalance(long termAmt) throws Exception;

	void updateSavingsAccount(Account account, User cust) throws Exception;
	
	void saveBeneficiaries(Account acc,Beneficiaries ben) throws Exception;
	
	boolean checkAccountExists(Beneficiaries ben) throws Exception;
	
	List<Beneficiaries> getAllBeneficiaries(int accId) throws Exception;
	
	void updateFromAccount(Account account,long transAmt,Transaction trans) throws Exception;
	
	void updateToAccount(Transaction trans) throws Exception;
	
	List<Transaction> loadAllTransactions(int accId) throws Exception;
	
	boolean isUserNameExists(String name) throws Exception;
	
	Account checkAccountExists(int accId) throws Exception;
	
	User getCustomerById(int custID) throws Exception;
	
	
}
