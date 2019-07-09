package com.dhanjyothi.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dhanjyothi.dao.AccountDao;
import com.dhanjyothi.model.Account;
import com.dhanjyothi.model.Beneficiaries;
import com.dhanjyothi.model.Transaction;
import com.dhanjyothi.model.User;

@Repository
public class AccountDaoImpl implements AccountDao {
	
	@Autowired
	private SessionFactory sessionFactory;

	public Account getAccountDetails(int custId, String accType)
			throws Exception {
		Session ses = sessionFactory.openSession();
		ses.beginTransaction();		
		TypedQuery<Account> typedQuery = ses.createQuery("from Account WHERE user.userId = :custId AND acc_type = :accType");
		typedQuery.setParameter("custId", custId);
		typedQuery.setParameter("accType", accType);
		Account acc = typedQuery.getSingleResult();
		
		ses.close();
		return acc;
	}

	public void openSavingsAccount(Account acc,boolean isSavingsAccountExists) throws Exception {
		Session ses = sessionFactory.openSession();
		ses.beginTransaction();
		if(isSavingsAccountExists){
			ses.update(acc);
		} else {
			ses.save(acc);
		}
		ses.getTransaction().commit();
		ses.close();
		
	}

	public void openTermAccount(Account acc) throws Exception {
		Session ses = sessionFactory.openSession();
		ses.beginTransaction();
		ses.save(acc);
		ses.getTransaction().commit();
		ses.close();
	}

	public List<Account> getTermAccountDetails(int custId, String accType)
			throws Exception {
		Session ses = sessionFactory.openSession();
		ses.beginTransaction();
		TypedQuery<Account> typedQuery = ses.createQuery("from Account WHERE user.userId = :custId AND acc_type = :accType");
		typedQuery.setParameter("custId", custId);
		typedQuery.setParameter("accType", accType);
		List<Account> acc = typedQuery.getResultList();
		ses.close();
		return acc;
	}

	public User getCustomerDetails(User cust) throws Exception {
		Session ses = sessionFactory.openSession();
		ses.beginTransaction();
		System.out.println("username and password"+cust.getUserName()+" "+cust.getPassword());
		TypedQuery<User> typedQuery = ses.createQuery("from User WHERE user_name = :userName AND password = :password");
		typedQuery.setParameter("userName", cust.getUserName());
		typedQuery.setParameter("password", cust.getPassword());
		User custObj = typedQuery.getSingleResult();
		ses.close();
		return custObj;
	}

	public void saveBeneficiaries(Beneficiaries ben) throws Exception {
		Session ses = sessionFactory.openSession();
		ses.beginTransaction();
		ses.save(ben);
		ses.getTransaction().commit();
		ses.close();
		
	}

	public Account checkAccountExists(int accId) throws Exception {
		Session ses = sessionFactory.openSession();
		ses.beginTransaction();
		TypedQuery<Account> typedQuery = ses.createQuery("from Account WHERE accountId = :accId");
		typedQuery.setParameter("accId", accId);
		Account acc = typedQuery.getSingleResult();
		ses.close();
		return acc;
	}

	public List<Beneficiaries> getAllBeneficiaries(int accId) throws Exception {
		Session ses = sessionFactory.openSession();
		ses.beginTransaction();
		TypedQuery<Beneficiaries> typedQuery = ses.createQuery("from Beneficiaries WHERE account.accountId = :accId");
		typedQuery.setParameter("accId", accId);
		List<Beneficiaries> ben = typedQuery.getResultList();
		ses.close();
		return ben;
	}

	public void updateTransactionDetails(Transaction trans) throws Exception {
		Session ses = sessionFactory.openSession();
		ses.beginTransaction();
		ses.save(trans);
		ses.getTransaction().commit();
		ses.close();
		
	}

	public List<Transaction> loadAllTransactions(int accId) throws Exception {
		Session ses = sessionFactory.openSession();
		ses.beginTransaction();
		TypedQuery<Transaction> typedQuery = ses.createQuery("from Transaction WHERE account.accountId = :accId");
		typedQuery.setParameter("accId", accId);
		List<Transaction> trans = typedQuery.getResultList();
		ses.close();
		return trans;
	}

	public User isUserNameExists(String name) throws Exception {
		Session ses = sessionFactory.openSession();
		ses.beginTransaction();
		TypedQuery<User> typedQuery = ses.createQuery("from User WHERE userName = :name");
		typedQuery.setParameter("name", name);
		User cus = typedQuery.getSingleResult();
		ses.close();
		return cus;
	}
	public User getCustomerById(int custID) throws Exception {
		Session ses = sessionFactory.openSession();
		ses.beginTransaction();
		System.out.println("Customer IdRev1"+custID);
		TypedQuery<User> typedQuery = ses.createQuery("from User WHERE userId = :custID");
		typedQuery.setParameter("custID", custID);
		User cus = typedQuery.getSingleResult();
		ses.close();
		return cus;
	}

}
