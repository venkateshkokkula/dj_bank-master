package com.dhanjyothi.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.dhanjyothi.model.User;
import com.dhanjyothi.model.KYC;
import com.dhanjyothi.service.RegisterService;
import com.dhanjyothi.validator.RegistrationValidator;

@Controller
public class RegisterController {

	@Autowired
	private RegistrationValidator regValidator;

	@Autowired
	private RegisterService registerService;

	@Value("${filePath}")
	private String fileUploadPath;

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public ModelAndView showRegister() {
		System.out.println("Hello Register form");
		ModelAndView mav = new ModelAndView();
		mav.addObject("cust", new User());
		mav.setViewName("signup");
		return mav;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView saveRegister(@ModelAttribute("cust") User cust,
			BindingResult bindingResult) {
		System.out.println("File Upload Path:" + fileUploadPath);
		System.out.println(cust);
		regValidator.validate(cust, bindingResult);
		if (bindingResult.hasErrors()) {
			return new ModelAndView("signup");
		}
		
		System.out.println(cust);
		
		Map<String, MultipartFile> filesMap = new HashMap<String, MultipartFile>();
		List<KYC> kycDetails = new ArrayList<KYC>();
		filesMap.put("DOB", cust.getKycDetails().get(0).getUser().getDobProof());
		filesMap.put("ADDRESS",  cust.getKycDetails().get(0).getUser().getAddressProof());
		filesMap.put("AADHAR",  cust.getKycDetails().get(0).getUser().getAadharProof());
		filesMap.put("PAN",  cust.getKycDetails().get(0).getUser().getPanProof());
		
		for (Map.Entry<String, MultipartFile> entry : filesMap.entrySet()) {
			KYC kyc = new KYC();
			MultipartFile mf = entry.getValue();
			String fileName = cust.getUserName() + "_"
					+ mf.getOriginalFilename();
			String docType = entry.getKey();
			System.out.println("FileName" + fileName);
			File uploadFile = new File(fileUploadPath, fileName);
			try {
				mf.transferTo(uploadFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
			kyc.setDoc_description(fileName);
			kyc.setKyc_type(docType);
			//kyc.setLocation(uploadFile.getAbsolutePath());
			kycDetails.add(kyc);
		}
		cust.setKycDetails(kycDetails);
		if(cust.getUserName().startsWith("admin"))
		{
			cust.setUserStatus("A");
			cust.setUserRole("M");
		}
		else
		{
		
		cust.setUserStatus("N");
		cust.setUserRole("C");
		}
		registerService.saveRegister(cust);
		return new ModelAndView("redirect:login");
	}

	@RequestMapping(value = "/getall", method = RequestMethod.GET)
	public ModelAndView getAllCustomers() {
		List<User> customers = registerService.getAllCustomers();
		System.out.println(customers);
		// return new ModelAndView("getall_customers", "customers", customers);
		ModelAndView mav = new ModelAndView();
		mav.addObject("customers", customers);
		mav.setViewName("getall_customers");
		return mav;
	}

}
