package com.dhanjyothi.dao;

import java.util.List;

import com.dhanjyothi.model.Account;
import com.dhanjyothi.model.Beneficiaries;
import com.dhanjyothi.model.Transaction;
import com.dhanjyothi.model.User;

public interface AccountDao {
	
	User getCustomerDetails(User cust) throws Exception;
	
	Account getAccountDetails(int custId, String accType) throws Exception;
	
	void openSavingsAccount(Account acc,boolean isSavingsAccountExists) throws Exception;
	
	void openTermAccount(Account acc) throws Exception;
	
	List<Account> getTermAccountDetails(int custId, String accType) throws Exception;
	
	void saveBeneficiaries(Beneficiaries ben) throws Exception;
	
	Account checkAccountExists(int l) throws Exception;
	
	List<Beneficiaries> getAllBeneficiaries(int accId) throws Exception;
	
	void updateTransactionDetails(Transaction trans) throws Exception;
	
	List<Transaction> loadAllTransactions(int accId) throws Exception;
	
	User isUserNameExists(String name) throws Exception;
	
	User getCustomerById(int custID) throws Exception;

}
