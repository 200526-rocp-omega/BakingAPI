package com.revature.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.authorization.AuthService;
import com.revature.controllers.AccountController;
import com.revature.controllers.UserController;
import com.revature.exceptions.AuthorizationException;
import com.revature.exceptions.NotLoggedInException;
import com.revature.models.Account;
import com.revature.models.AccountStatus;
import com.revature.models.AccountType;
import com.revature.models.Role;
import com.revature.models.User;
import com.revature.services.UserService;
import com.revature.services.AccountService;
import com.revature.templates.AccountUpdateTemplate;
import com.revature.templates.BalanceTemplate;
import com.revature.templates.LoginTemplate;
import com.revature.templates.MessageTemplate;

public class FrontController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final UserController userController = new UserController();
	private static final AccountController accountController = new AccountController();
	private static final ObjectMapper om = new ObjectMapper();
	private UserService service = new UserService();
	private AccountService accservice = new AccountService();
	//private UserService service = new UserService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException {
		res.setContentType("application/json");
		res.setStatus(404);
		// Prevents non-desired endpoints from being successful
		final String URI = req.getRequestURI().replace("/rocp-project", "").replaceFirst("/", "");
		String[] portions = URI.split("/");
		try {
			switch(portions[0]) {
			case "users":
				if(portions.length == 2) {
					// Delegate to a Controller method to handle obtaining a User by ID
					int id = Integer.parseInt(portions[1]);
					AuthService.guard(req.getSession(false), id, "Employee", "Admin");
					User u = userController.findUserById(id);
					res.setStatus(200);
					res.getWriter().println(om.writeValueAsString(u));
				} else {
					// Delegate to a Controller method to handle obtaining ALL Users
					AuthService.guard(req.getSession(false), "Employee", "Admin");
					List<User> all = userController.findAllUsers();
					res.setStatus(200);
					res.getWriter().println(om.writeValueAsString(all));
				}
				break;
			case "accounts":
				if(portions.length < 2) {
					AuthService.guard(req.getSession(false), "Employee", "Admin");
					List<Account> all = accountController.findAllAccounts();
					res.setStatus(200);
					res.getWriter().println(om.writeValueAsString(all));
				} else if(portions.length == 2) {
					int id = Integer.parseInt(portions[1]);
					AuthService.guard(req.getSession(false), id, "Employee", "Admin");
					Account a = accountController.findAccountById(id);
					res.setStatus(200);
					res.getWriter().println(om.writeValueAsString(a));
				} else if(portions.length == 3 && portions[1].equals("status")) {
					int statusId = Integer.parseInt(portions[2]);
					AuthService.guard(req.getSession(false), statusId, "Employee", "Admin");
					List<Account> a = accountController.findByStatus(statusId);
					res.setStatus(200);
					res.getWriter().println(om.writeValueAsString(a));
				} else if(portions.length == 3 && portions[1].equals("owner")) {
					int id = Integer.parseInt(portions[2]);
					AuthService.guard(req.getSession(false), id, "Employee", "Admin");
					List<Account> a = accountController.findAccountByUserId(id);
					res.setStatus(200);
					res.getWriter().println(om.writeValueAsString(a));
				}
				break;
			}
		} catch(AuthorizationException e) {
			res.setStatus(401);
			MessageTemplate message = new MessageTemplate("The incoming token has expired or You need more Privilage");
			res.getWriter().println(om.writeValueAsString(message));
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException {
		res.setContentType("application/json");
		res.setStatus(404);
		// Prevents non-desired endpoints from being successful
		final String URI = req.getRequestURI().replace("/rocp-project", "").replaceFirst("/", "");
		System.out.println(URI);
		String[] portions = URI.split("/");
		try {
			switch(portions[0]) {
			case "logout":
				if(userController.logout(req.getSession(false))) {
					res.setStatus(200);
					res.getWriter().println("You have been successfully logged out");
				} else {
					res.setStatus(400);
					res.getWriter().println("You were not logged in to begin with");
				}
				break;
			case "login":
				HttpSession session = req.getSession();
				User current = (User) session.getAttribute("currentUser");
				// Already logged in
				if(current != null) {
					res.setStatus(400);
					res.getWriter().println("You are already logged in as user " + current.getUsername());
					return;
				}

				BufferedReader reader = req.getReader();
				StringBuilder sb = new StringBuilder();
				
				String line;
				while( (line = reader.readLine()) != null ) {
					sb.append(line);
				}
				
				String body = sb.toString();
				LoginTemplate lt = om.readValue(body, LoginTemplate.class);
				User u = service.login(lt);
				PrintWriter writer = res.getWriter();

				if(u == null) {
					// Login failed
					res.setStatus(400);
					
					writer.println("Username or password was incorrect");
					return;
				}
				
				// Login successful
				session.setAttribute("currentUser", u);
				res.setStatus(200);
				writer.println(om.writeValueAsString(u));
				res.setContentType("application/json");
				break;
			
			case "accounts":
				System.out.println(portions[1]);
				if(portions.length == 1) {
					AuthService.guard(req.getSession(false), "Employee", "Admin");
					Scanner in = new Scanner(System.in);
					Scanner inString = new Scanner(System.in);
					System.out.println("Enter the New Account information: balance, statusId, typeId");
					String dataString = in.nextLine();
					String[] inputData = dataString.split(",");
					String status = null;
					String type = null;
					double accBalance = Double.parseDouble(inputData[0]);
					int statusId = Integer.parseInt(inputData[1]);
					int typeId = Integer.parseInt(inputData[2]);
					switch (statusId) {
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
					if(typeId == 1) {
						type = "Checking";
					} else {
						type =  "Savings";
					}
	
					AccountStatus accStatus = new AccountStatus(statusId, status);
					AccountType accType = new AccountType(typeId, type);
		
					Account a = new Account(0, accBalance, accStatus, accType);
					
					int number = accountController.createAccount(a);
					res.setStatus(201);
					res.getWriter().println(om.writeValueAsString(a));
					if(number == 0) {
						System.out.println("You have successfully updated the User");
					}
				} else if (portions[1].equalsIgnoreCase("deposit")) {
					HttpSession sessionWD = req.getSession();
					User currentUser = (User) sessionWD.getAttribute("currentUser");
					int sessionId = currentUser.getId();
					AuthService.guard(req.getSession(false), sessionId, "Admin");
					
					BufferedReader readerWD = req.getReader();
					StringBuilder sbWD = new StringBuilder();
					String lineWD;
					
					while( (lineWD = readerWD.readLine()) != null ) {
						sbWD.append(lineWD);
					}

					String bodyWD = sbWD.toString();
					
					BalanceTemplate bt = om.readValue(bodyWD, BalanceTemplate.class);
					
					int returnnumber = accountController.usersAccounts(sessionId, bt.getAccountId());
					if(bt.getAmount() < 1) {
						System.out.println("Enter an amout greater than 0");
					} else if (returnnumber == 0){
						
						int number = accountController.withdraw(bt);
						res.setStatus(201);
						res.getWriter().println(om.writeValueAsString(bt));
					} else {
						System.out.println("You do NOT have access to the account");
					}
					
				} else if (portions[1].toLowerCase() == "withdraw") {
					
					
				}
				break;
			}
		} catch(NotLoggedInException e) {
			res.setStatus(401);
			MessageTemplate message = new MessageTemplate("The incoming token has expired");
			res.getWriter().println(om.writeValueAsString(message));
		}
	}
	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException {
		res.setContentType("application/json");
		res.setStatus(404);
		final String URI = req.getRequestURI().replace("/rocp-project", "").replaceFirst("/", "");
		System.out.println(URI);
		String[] portions = URI.split("/");
		try {
			switch(portions[0]) {
			case "users":
				AuthService.guard(req.getSession(false), "Employee", "Admin");
				String s;
				Scanner in = new Scanner(System.in);
				System.out.println("Enter the Id number");
				s = in.nextLine();
				int idNum = Integer.parseInt(s);
				Scanner inString = new Scanner(System.in);
				System.out.println("Enter the information you want to update. Follow the pattern: username, password, first name, last name, email, role(num)");
				String dataString = in.nextLine();
				String[] inputData = dataString.split(",");
				String roleName = null;
				int roleId = Integer.parseInt(inputData[5]);
				switch (roleId) {
					case 1:
						roleName = "Standard";
						break;
					case 2:
						roleName = "Premium";
						break;
					case 3:
						roleName = "Employee";
						break;
					case 4:
						roleName = "Admin";
						break;
				}
				Role role = new Role(roleId, roleName);
				User u = new User(idNum, inputData[0], inputData[1], inputData[2], inputData[3], inputData[4], role);
				
				int number = userController.update(u);
				res.setStatus(200);
				res.getWriter().println(om.writeValueAsString(u));
				if(number == 0) {
					System.out.println("You have successfully updated the User");
				}
				
				break;
			
			case "accounts":	
				AuthService.guard(req.getSession(false), "Admin");
				
				BufferedReader reader = req.getReader();
				StringBuilder sb = new StringBuilder();
				String line;
				while( (line = reader.readLine()) != null ) {
					sb.append(line);
				}
				String body = sb.toString();
				Account a = om.readValue(body, Account.class);
				
			
				int numbera = accountController.update(a);
				res.setStatus(201);
				res.getWriter().println(om.writeValueAsString(a));
				if(numbera == 0) {
					System.out.println("You have successfully updated the User");
				}
					break;
				}
		}catch(NotLoggedInException e) {
			res.setStatus(401);
			MessageTemplate message = new MessageTemplate("The incoming token has expired");
			res.getWriter().println(om.writeValueAsString(message));
		}
	}
}

