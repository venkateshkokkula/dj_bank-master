package com.dhanjyothi.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class ApplicationFilter implements Filter{
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("Init method call");
	}

	public void destroy() {
		System.out.println("Destroy method call");

	}
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2) throws ServletException,IOException
			 {
		System.out.println("Dofilter method call");
		try{
		arg2.doFilter(arg0, arg1);
		}catch(Exception e){
			e.printStackTrace();
			arg0.setAttribute("errormessage", e);
			arg0.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(arg0, arg1);
		}
	}

}
