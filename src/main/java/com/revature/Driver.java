package com.revature;

import com.revature.dao.IUserDAO;
import com.revature.dao.UserDAO;
import com.revature.models.Role;
import com.revature.models.User;

public class Driver {

	public static void main(String[] args) {
		IUserDAO dao = new UserDAO();
		
		User user = new User(0, "miryb4", "passwordmiry4", "Miry4", "Brach4", "miry4@yahoo.com", new Role(2,"Admin"));
		System.out.println(dao.insert(user));
		
		for(User u : dao.findAll()) {
			System.out.println(u);
		}

	}
}
