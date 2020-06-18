package com.revature.models;

import java.util.Objects;

public class UserAccountJoin {
	int userId;
	int accountId;
	
	public UserAccountJoin() {
		super();
	}
	
	public UserAccountJoin(int userId, int accountId) {
		super();
		this.userId = userId;
		this.accountId = accountId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(accountId, userId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof UserAccountJoin)) {
			return false;
		}
		UserAccountJoin other = (UserAccountJoin) obj;
		return accountId == other.accountId && userId == other.userId;
	}

	@Override
	public String toString() {
		return "UserAccountJoin [userId=" + userId + ", accountId=" + accountId + "]";
	}
	
	
}
