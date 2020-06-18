package com.revature.templates;

import java.util.Objects;

import com.revature.models.AccountStatus;
import com.revature.models.AccountType;

public class AccountUpdateTemplate {
	private int id;
	private double balance;
	private AccountStatus status;
	private AccountType type;
	
	public AccountUpdateTemplate() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AccountUpdateTemplate(int id, double balance, AccountStatus status, AccountType type) {
		super();
		this.id = id;
		this.balance = balance;
		this.status = status;
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public AccountStatus getStatus() {
		return status;
	}

	public void setStatus(AccountStatus status) {
		this.status = status;
	}

	public AccountType getType() {
		return type;
	}

	public void setType(AccountType type) {
		this.type = type;
	}

	@Override
	public int hashCode() {
		return Objects.hash(balance, id, status, type);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof AccountUpdateTemplate)) {
			return false;
		}
		AccountUpdateTemplate other = (AccountUpdateTemplate) obj;
		return Double.doubleToLongBits(balance) == Double.doubleToLongBits(other.balance) && id == other.id
				&& Objects.equals(status, other.status) && Objects.equals(type, other.type);
	}

	@Override
	public String toString() {
		return "AccountUpdateTemplate [id=" + id + ", balance=" + balance + ", status=" + status + ", type=" + type
				+ "]";
	}
	

	
	
	
}
