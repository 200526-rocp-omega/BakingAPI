package com.revature.templates;

import java.util.Objects;

public class BalanceTemplate {

	private int accountId;
	private double amount;
	
	public BalanceTemplate() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BalanceTemplate(int accountId, double amount) {
		super();
		this.accountId = accountId;
		this.amount = amount;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Override
	public int hashCode() {
		return Objects.hash(accountId, amount);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof BalanceTemplate)) {
			return false;
		}
		BalanceTemplate other = (BalanceTemplate) obj;
		return accountId == other.accountId && Double.doubleToLongBits(amount) == Double.doubleToLongBits(other.amount);
	}

	@Override
	public String toString() {
		return "BalanceTemplate [accountId=" + accountId + ", amount=" + amount + "]";
	}
	
}
