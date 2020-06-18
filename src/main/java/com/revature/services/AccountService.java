package com.revature.services;

import java.util.List;

import com.revature.dao.AccountDAO;
import com.revature.dao.IAccountDAO;
import com.revature.models.Account;
import com.revature.templates.BalanceTemplate;

public class AccountService {

	private IAccountDAO adao = new AccountDAO();
	
	public List<Account> findAllAccounts(){
		return adao.findAllAccounts();
	}
	
	public Account findAccountById(int id){
		return adao.findAccountById(id);
	}
	
	public List<Account> findByStatus(int statusId){
		return adao.findByStatus(statusId);
	}
	
	public List<Account> findAccountByUserId(int id) {
		return adao.findAccountByUserId(id);
	}
	
	public int createAccount(Account a) {
		return adao.createAccount(a);
	}
	
	public int update(Account a) {
		return adao.update(a);
	}
	
	public int withdraw(BalanceTemplate bt) {
		return adao.deposit(bt);
	}
	
	public int usersAccounts(int userId, int accountId) {
		return adao.usersAccounts(userId, accountId);
	}
}
