package com.revature.services;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.revature.dao.IUserDAO;
import com.revature.dao.UserDAO;
import com.revature.exceptions.NotLoggedInException;
import com.revature.models.User;
import com.revature.templates.LoginTemplate;

public class UserService {

	private IUserDAO dao = new UserDAO();

	// A starting place that I like to use, is to also create CRUD methods in the service layer
	// that will be used to interact with the DAO
	
	// Then additionally, you can have extra methods to enforce whatever features/rules that you want
	// For example, we might also have a login/logout method
	
	public int insert(User u) {
		return dao.insert(u);
	}
	
	public List<User> findAll() {
		return dao.findAll();
	}
	
	public User findById(int id) {
		return dao.findById(id);
	}
	
	public User findByUsername(String username) {
		return dao.findByUsername(username);
	}
	
	public int update(User u) {
		return dao.update(u);
	}
	
	public int delete(int id) {
		return dao.delete(id);
	}
	
	public User login(LoginTemplate lt) {
		
		User userFromDB = findByUsername(lt.getUsername());
		
		if(userFromDB == null) {
			return null;
		}
		
		if(userFromDB.getPassword().equals(lt.getPassword())) {
			return userFromDB;
		}
		
		return null;
	}
	
	public void logout(HttpSession session) {
		
		if(session == null || session.getAttribute("currentUser") == null) {
			throw new NotLoggedInException("User must be logged i, in order to logout");
		}
		
		session.invalidate();
		
	}
	
}
