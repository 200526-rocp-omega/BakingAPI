package com.revature.controllers;

import java.util.List;

import com.revature.models.Account;
import com.revature.services.AccountService;
import com.revature.templates.BalanceTemplate;

public class AccountController {

	private final AccountService accountService = new AccountService();

		public Account findAccountById(int id) {
			return accountService.findAccountById(id);
			
		}
		
		public List<Account> findAllAccounts() {
			return accountService.findAllAccounts();	
		}
		
		
		public List<Account> findByStatus(int statusId) {
			return accountService.findByStatus(statusId);
		}
		
		
		public List<Account> findAccountByUserId(int id) {
			return accountService.findAccountByUserId(id);
		}
		
		public int createAccount(Account a) {
			return accountService.createAccount(a);
		}
		
		public int update(Account a) {
			return accountService.update(a);
		}
		
		public int withdraw(BalanceTemplate bt) {
			return accountService.withdraw(bt);
		}
		
		public int usersAccounts(int userId, int accountId) {
			return accountService.usersAccounts(userId, accountId);
		}
}
