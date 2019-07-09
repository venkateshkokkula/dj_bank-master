package com.dhanjyothi.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.dhanjyothi.model.Account;
import com.dhanjyothi.model.User;
import com.dhanjyothi.model.Transaction;
import com.dhanjyothi.service.AccountService;
import com.dhanjyothi.util.DhanJyothiUtil;
import com.dhanjyothi.validator.TermAccountValidator;

@Controller
public class AccountController {
	
	@Autowired
	private AccountService accService;
	
	@Autowired
	private TermAccountValidator termAccValidator;
	
	@GetMapping("/createsavingsaccount")
	public String loadAccountCreationPage(Model model,HttpServletRequest req) {
		HttpSession ses = req.getSession();
		if(ses != null) {
			User cust = (User) ses.getAttribute("cust");
			if(cust != null) {
				try {
					accService.openSavingsAccount(cust);
					ses.setAttribute("account",accService.getAccountDetails(cust.getUserId(), "SAVINGS") );
				} catch (Exception e) {
					e.printStackTrace();
					return "error";
					
				}
				return "redirect:accsummary";
			} else {
				return "redirect:login";
			}
		} else {
			return "redirect:login";
		}
		
	}
	//loadtermaccount
	@GetMapping("/loadtermaccount")
	public String loadTermAccount(@ModelAttribute("account") Account account,Model model) {
		Map<Integer, String> map = accService.getTenureDetails();
		model.addAttribute("tenureList", map);
	
		return "createtermaccount";
	}
	
	@PostMapping("/createtermaccount")
	public String createtermaccount(@ModelAttribute("account") Account account,Model model,HttpServletRequest req,BindingResult bindingResult) {
		termAccValidator.validate(account, bindingResult);
		if(bindingResult.hasErrors()) {
			Map<Integer, String> map = accService.getTenureDetails();
			model.addAttribute("tenureList", map);
			return "createtermaccount";
		}
		HttpSession ses = req.getSession();
		if(ses != null) {
			User cust = (User) ses.getAttribute("cust");
			if(cust != null) {
				try {
					accService.updateSavingsAccount(account, cust);
					accService.openTermAccount(account,cust);
				} catch (Exception e) {
					e.printStackTrace();
					return "error";
					
				}
				return "redirect:accsummary";
			} else {
				return "redirect:login";
			}
		} else {
			return "redirect:login";
		}
	}
	
	@GetMapping("/viewtransactions")
	public String loadTransactionDetails(Model model,HttpServletRequest req) {
		HttpSession ses = req.getSession();
		if(ses != null) {
			Account acc = (Account) ses.getAttribute("account");
			if(acc != null) {
				try {
					List<Transaction> trans = accService.loadAllTransactions(acc.getAccountId());
					if(trans.size() > 0) {
						model.addAttribute("transactionsExists", true);
						model.addAttribute("transactionsList", trans);
					} else {
						model.addAttribute("transactionsExists", false);
					}
					
				} catch (Exception e) {
					e.printStackTrace();
					return "error";
					
				}
				return "viewtransactions";
			} else {
				return "redirect:login";
			}
		} else {
			return "redirect:login";
		}
	}
	
}
