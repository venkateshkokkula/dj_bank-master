package com.dhanjyothi.controller;

import java.util.List;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.dhanjyothi.model.Account;
import com.dhanjyothi.model.User;
import com.dhanjyothi.service.AccountService;
import com.dhanjyothi.service.LoginService;

@Controller
public class LoginController {

	@Autowired
	private LoginService loginService;

	@Autowired
	private AccountService accService;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView showRegister() {
		System.out.println("Hello Login form");
		ModelAndView mav = new ModelAndView();
		mav.addObject("cust", new User());
		mav.setViewName("login");
		return mav;
	}

	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	public String validateCustomer(@ModelAttribute("cust") User cust,
			HttpServletRequest req, Model model) {
		HttpSession ses = req.getSession();
		User custObj = new User();
		System.out.println(cust);
		System.out.println("Customer"+cust.getUserName());
			int flag = loginService.validateCustomer(cust);
			if(cust.getUserName().equalsIgnoreCase("admin")||cust.getUserName().startsWith("admin"))
			{
				model.addAttribute("userName",cust.getUserName());
				return "adminSummary";
			}
			else{
			try {
				System.out.println("entered submit1");
				custObj = accService.getCustomerDetails(cust);
				ses.setAttribute("cust", custObj);
				return "redirect:accsummary";
			} catch (NoResultException ne) {
				System.out.println("entered submit2");
				model.addAttribute("userName",
						custObj.getFirstName().toUpperCase() + " " +custObj.getLastName().toUpperCase());
				model.addAttribute("isAccountExists", false);
				return "redirect:accsummary";
			} catch (Exception e) {
				System.out.println("entered submit3");
				e.printStackTrace();
				return "error";
			}
		}

		/*else {
			return "error";
		}*/
	}

	@RequestMapping("/accsummary")
	public String loadAccSummary(HttpServletRequest req, Model model) {
		HttpSession ses = req.getSession();
		Account acc;
		List<Account> termAcc;
		if (ses != null) {
			User cust = (User) ses.getAttribute("cust");
			if (cust != null) {
				try {
					System.out.println("CusomerId11....."+cust.getFirstName());
					acc = accService.getAccountDetails(cust.getUserId(), "SAVINGS");
					termAcc = accService.getTermAccountDetails(cust.getUserId(),
							"TERM");
					
					model.addAttribute("userName",
							cust.getFirstName() + cust.getLastName());
					if (acc != null) {
						ses.setAttribute("account",acc );
						model.addAttribute("isSavingsAccountExists", true);
						model.addAttribute("savingsaccount", acc);
					} else {
						model.addAttribute("isSavingsAccountExists", false);
					}
					if (termAcc.size() > 0) {
						model.addAttribute("isTermAccountExists", true);
						model.addAttribute("termaccount", termAcc);
					} else {
						model.addAttribute("isTermAccountExists", false);
					}
					return "accountsummary";
				} catch (NoResultException ne) {
					System.out.println("CusomerId....."+cust.getFirstName());
					model.addAttribute("userName",
							cust.getFirstName().toUpperCase() + " " +cust.getLastName().toUpperCase());
					//model.addAttribute("isAccountExists", false);
					return "accountsummary";
				} catch (Exception e) {
					e.printStackTrace();
					return "error";
				}
			} else {
				return "redirect:login";
			}
		} else {
			return "redirect:login";
		}

	}

	@RequestMapping("/adduser")
	public String addUser(HttpServletRequest req, Model model) {
		HttpSession ses = req.getSession();
		Account acc;
		List<Account> termAcc;
		User user = new User();
		user.setUserName("prabhu");
		User userSes = (User) ses.getAttribute("user");
		try {
			if (userSes != null) {
				acc = accService.getAccountDetails(userSes.getUserId(), "SAVINGS");
				termAcc = accService.getTermAccountDetails(userSes.getUserId(),
						"TERM");
				model.addAttribute("userName", userSes.getUserName());
				System.out.println(termAcc.size());
				if (acc != null) {
					model.addAttribute("isSavingsAccountExists", true);
					model.addAttribute("savingsaccount", acc);
				} else {
					model.addAttribute("isSavingsAccountExists", false);
				}
				if (termAcc.size() > 0) {
					model.addAttribute("isTermAccountExists", true);
					model.addAttribute("termaccount", termAcc);
				} else {
					model.addAttribute("isTermAccountExists", false);
				}
			} else {
				// accService.addUser(user);
				ses.setAttribute("user", user);
				acc = accService.getAccountDetails(user.getUserId(), "SAVINGS");
				termAcc = accService
						.getTermAccountDetails(user.getUserId(), "TERM");
				model.addAttribute("userName", user.getUserName());
				if (acc != null) {
					model.addAttribute("isSavingsAccountExists", true);
					model.addAttribute("savingsaccount", acc);
				} else {
					model.addAttribute("isSavingsAccountExists", false);
				}
				if (termAcc.size() > 0) {
					model.addAttribute("isTermAccountExists", true);
					model.addAttribute("termaccount", termAcc);
				} else {
					model.addAttribute("isTermAccountExists", false);
				}
			}
			System.out.println(acc.getAccountBalance() + "-" + acc.getAccountId() + "-"
					+ acc.getAccountType());
			return "accountsummary";
		} catch (NoResultException ne) {
			model.addAttribute("userName", user.getUserName());
			model.addAttribute("isAccountExists", false);
			return "accountsummary";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	@GetMapping("/logout")
	public String logoutPage(HttpServletRequest req) {
		HttpSession ses = req.getSession();
		ses.invalidate();
		return "redirect:login";
	}
}
