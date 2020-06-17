package com.revature.controllers;

import java.util.List;

import com.revature.models.Account;
import com.revature.services.AccountService;

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
}
