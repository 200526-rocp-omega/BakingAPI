package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Account;
import com.revature.models.AccountStatus;
import com.revature.models.AccountType;
import com.revature.util.ConnectionUtil;

public class AccountDAO implements IAccountDAO {

	@Override
	public List<Account> findAllAccounts() {
		List<Account> allAccounts = new ArrayList<>();
		
		try (Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM ACCOUNTS, ACCOUNT_STATUS, ACCOUNT_TYPE WHERE ACCOUNTS.status_id = ACCOUNT_STATUS.id AND ACCOUNTS.type_id = ACCOUNT_TYPE.id";
			//SELECT id, balance, status_id, type_id FROM ACCOUNTS AS a INNER JOIN ACCOUNT_STATUS AS s ON a.status_id = s.id INNER JOIN ACCOUNT_TYPE AS t ON a.type_id = t.id
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			System.out.println(rs);
			
			while(rs.next()) {
				int id = rs.getInt(1);
				Double balance = rs.getDouble("balance");
				int statusId = rs.getInt("status_id");
				String status = rs.getString("status");
				int typeid = rs.getInt("type_id");
				String type = rs.getString("type");
				
				AccountStatus accStatus = new AccountStatus(statusId, status);
				AccountType accType = new AccountType(typeid, type);
				Account a = new Account(id, balance, accStatus, accType);
				
				allAccounts.add(a);
			}
			
		} catch (SQLException e){
			e.printStackTrace();
			return new ArrayList<>();
			
		}
		return allAccounts;
	}

	@Override
	public Account findAccountById(int id) {
        try (Connection conn = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM ACCOUNTS, ACCOUNT_STATUS, ACCOUNT_TYPE WHERE ACCOUNTS.status_id = ACCOUNT_STATUS.id AND ACCOUNTS.type_id = ACCOUNT_TYPE.id AND ACCOUNTS.id  = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()) {
				int accountId = rs.getInt("id");
            	Double balance = rs.getDouble("balance");
				int statusId = rs.getInt("status_id");
				String status = rs.getString("status");
				int typeid = rs.getInt("type_id");
				String type = rs.getString("type");
				
				AccountStatus accStatus = new AccountStatus(statusId, status);
				AccountType accType = new AccountType(typeid, type);
				return new Account(accountId, balance, accStatus, accType);
				
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        
        return null;
	}

	@Override
	public List<Account> findByStatus(int statusId) {
		List<Account> allAccounts = new ArrayList<>();
        try (Connection conn = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM ACCOUNTS, ACCOUNT_STATUS, ACCOUNT_TYPE WHERE ACCOUNTS.status_id = ACCOUNT_STATUS.id AND ACCOUNTS.type_id = ACCOUNT_TYPE.id AND ACCOUNTS.status_id  = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, statusId);
            
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()) {
				int accountId = rs.getInt("id");
            	Double balance = rs.getDouble("balance");
				int statusIdNum = rs.getInt("status_id");
				String status = rs.getString("status");
				int typeid = rs.getInt("type_id");
				String type = rs.getString("type");
				
				AccountStatus accStatus = new AccountStatus(statusIdNum, status);
				AccountType accType = new AccountType(typeid, type);
				Account a = new Account(accountId, balance, accStatus, accType);
				
				allAccounts.add(a);
				
            }
        } catch(SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
		return allAccounts;
	}
	
	@Override
	public List<Account> findAccountByUserId(int id) {
		List<Account> allAccounts = new ArrayList<>();
        try (Connection conn = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM ACCOUNTS WHERE ACCOUNTS.id IN (SELECT account_id FROM USERS_ACCOUNTS WHERE USERS_ACCOUNTS.user_id = ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()) {
				//int accountId = rs.getInt("id");
            	String status = "";
            	String type = "";
            	Double balance = rs.getDouble("balance");
				int statusIdNum = rs.getInt("status_id");
				switch (statusIdNum) {
					case 1:
						status = "Pending";
						break;
					case 2:
						status = "Open";
						break;
					case 3:
						status = "Closed";
						break;
					case 4:
						status = "Denied";
						break;
				}
				int typeid = rs.getInt("type_id");
				if (typeid ==1) {
					type = "Checking";
				} else {
					type = "Savings";
				}
				
				AccountStatus accStatus = new AccountStatus(statusIdNum, status);
				AccountType accType = new AccountType(typeid, type);
				Account a = new Account(id, balance, accStatus, accType);
				
				allAccounts.add(a);
				
            }
        } catch(SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }

		return allAccounts;
	}

	@Override
	public int createAccount(Account a) {
		try (Connection conn = ConnectionUtil.getConnection()){
			int randomIdNum = (int) Math.round(Math.random()  * 200000000);
			System.out.println(randomIdNum);
			String sql = "INSERT INTO ACCOUNTS VALUES (" + randomIdNum + " , ?, ?, ?)";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setDouble(1, a.getBalance());
			stmt.setInt(2, a.getStatus().getStatusId());
			stmt.setInt(3, a.getType().getTypeId());
			
			
			return stmt.executeUpdate();
			
			} catch(Exception e) {
				e.printStackTrace();
			}
		return 0;
	}

	@Override
	public int update(Account a) {
		try (Connection conn = ConnectionUtil.getConnection()){
			int accountId = a.getAccountId();
			
			AccountStatus accStatus = new AccountStatus();
			switch (a.getStatus().getStatusId()) {
				case 1:
					accStatus.setStatusId(a.getStatus().getStatusId());
					accStatus.setStatus("Pending");
					a.setStatus(accStatus);
					break;
				case 2:
					accStatus.setStatusId(a.getStatus().getStatusId());
					accStatus.setStatus("Open");
					a.setStatus(accStatus);
					break;
				case 3:
					accStatus.setStatusId(a.getStatus().getStatusId());
					accStatus.setStatus("Closed");
					a.setStatus(accStatus);
					break;
				case 4:
					accStatus.setStatusId(a.getStatus().getStatusId());
					accStatus.setStatus("Denied");
					a.setStatus(accStatus);
					break;
			}
			
			AccountType accType = new AccountType();
			if (a.getType().getTypeId() == 1) {
				accType.setTypeId(a.getType().getTypeId());
				accType.setType("Checking");
				a.setType(accType);
			} else {
				accType.setTypeId(a.getType().getTypeId());
				accType.setType("Savings");
				a.setType(accType);
			}

			String sql = "UPDATE ACCOUNTS SET balance = ?, status_id = ?, type_id = ? WHERE id = ?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setDouble(1, a.getBalance());
			stmt.setInt(2, a.getStatus().getStatusId());
			stmt.setInt(3, a.getType().getTypeId());
			stmt.setInt(4, accountId);
			
			
			return stmt.executeUpdate();
			
			} catch(Exception e) {
				e.printStackTrace();
			}
		
		return 0;
	}

}
