package com.revature.dao;

import java.util.List;

import com.revature.models.Account;

public interface IAccountDAO {

	public List<Account> findAllAccounts();
	public Account findAccountById(int id);
	public List<Account> findByStatus(int statusId);
	public Account findAccountByUserId(int id);
	public int createAccount(Account a);
	public int update(Account a);
	
}
