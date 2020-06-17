package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Role;
import com.revature.models.User;
import com.revature.util.ConnectionUtil;

public class UserDAO implements IUserDAO {

	@Override
	public int insert(User u) {
		
		try (Connection conn = ConnectionUtil.getConnection()){
			String columns = "username, password, first_name, last_name, email, role_id";
			String sql = "INSERT INTO USERS (" + columns + ") VALUES (?, ?, ?, ?, ?, ?)";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, u.getUsername());
			stmt.setString(2, u.getPassword());
			stmt.setString(3, u.getFirstName());
			stmt.setString(4, u.getLastName());
			stmt.setString(5, u.getEmail());
			stmt.setInt(6, u.getRole().getId());
			
			return stmt.executeUpdate();
			
			} catch(SQLException e) {
				e.printStackTrace();
				return 0;
			}
			
	}

	@Override
	public List<User> findAll() {
		List<User> allUsers = new ArrayList<>();
		
		//below is a try-with-resources block
		// it allows us to instantiate some variable for use only inside the try block
		// and then at the end, it will automatically invoke the close() method resource
		try (Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM USERS INNER JOIN ROLES ON USERS.role_ID = ROLES.id ";
			
			Statement stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				int id = rs.getInt(1);//1 is the index in the DB of the id, we can use the index or the name id
				String username = rs.getString("username");
				String password = rs.getString("password");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				String email = rs.getString("email");
				int roleid = rs.getInt("role_id");
				String rolename = rs.getString("role");
				
				Role role = new Role(roleid, rolename);
				User u = new User(id, username, password, firstName, lastName, email, role);
				
				allUsers.add(u);
				
			}
			
		} catch(SQLException e) {
			// if something goes wrong, return an empty list
			e.printStackTrace();
			return new ArrayList<>();
		}
		
		
		return allUsers;
	}

	@Override
	public User findById(int id) {
        try (Connection conn = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM USERS INNER JOIN ROLES ON USERS.role_id = ROLES.id WHERE USERS.id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");
                int roleId = rs.getInt("role_id");
                String roleName = rs.getString("role");
                
                // And use that data to create a User object accordingly
                Role role = new Role(roleId, roleName);
                return new User(id, username, password, firstName, lastName, email, role);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        
        return null;
	}

	@Override
	public User findByUsername(String username) {
        try (Connection conn = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM USERS INNER JOIN ROLES ON USERS.role_id = ROLES.id WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()) {
                int id = rs.getInt("id");
                String password = rs.getString("password");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");
                int roleId = rs.getInt("role_id");
                String roleName = rs.getString("role");
                
                // And use that data to create a User object accordingly
                Role role = new Role(roleId, roleName);
                return new User(id, username, password, firstName, lastName, email, role);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        
        return null;
	}

	@Override
	public int update(User u) {
		try (Connection conn = ConnectionUtil.getConnection()){
			String sql = "UPDATE USERS SET id = ?, username = ?, password = ?, first_name = ?, last_name = ?, email = ?, role_id = ? WHERE id = ?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, u.getId());
			stmt.setString(2, u.getUsername());
			stmt.setString(3, u.getPassword());
			stmt.setString(4, u.getFirstName());
			stmt.setString(5, u.getLastName());
			stmt.setString(6, u.getEmail());
			stmt.setInt(7, u.getRole().getId());
			stmt.setInt(8, u.getId());
			
			
			return stmt.executeUpdate();
			
			} catch(SQLException e) {
				e.printStackTrace();
			}
			return 0;
		
	}

	@Override
	public int delete(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

}
