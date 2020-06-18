package com.revature.dao;

import java.util.List;

import com.revature.models.Account;
import com.revature.templates.BalanceTemplate;

public interface IAccountDAO {

	public List<Account> findAllAccounts();
	public Account findAccountById(int id);
	public List<Account> findByStatus(int statusId);
	public List<Account> findAccountByUserId(int id);
	public int createAccount(Account a);
	public int update(Account a);
	public int withdraw(BalanceTemplate bt);
	public int usersAccounts(int userId, int accountId);
}
