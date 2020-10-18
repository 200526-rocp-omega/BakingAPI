package com.revature;

import com.revature.dao.IUserDAO;
import com.revature.dao.UserDAO;
import com.revature.models.Role;
import com.revature.models.User;

public class Driver {

	public static void main(String[] args) {
		IUserDAO dao = new UserDAO();
		
		User user = new User(0, "username", "password", "nickname", "nickname2", "email", new Role(2,"Admin"));
		System.out.println(dao.insert(user));
		
		for(User u : dao.findAll()) {
			System.out.println(u);
		}

	}
}
