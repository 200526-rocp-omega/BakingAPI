package com.revature.services;

import java.util.List;

import com.revature.dao.AccountDAO;
import com.revature.dao.IAccountDAO;
import com.revature.models.Account;

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
	
	public Account findAccountByUserId(int id) {
		return adao.findAccountByUserId(id);
	}
}