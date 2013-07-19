package com.izerui.controller;

import java.io.File;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FileViewController {
	private static Logger log = Logger.getLogger(FileViewController.class);
	@RequestMapping(value="/viewFile")
	public ModelAndView view(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		Enumeration eu = request.getParameterNames();
		while (eu.hasMoreElements()) {
			String elem = (String) eu.nextElement();
			try {
				modelMap.addAttribute(elem, new String(request.getParameter(elem).getBytes("ISO-8859-1"),"UTF-8"));
			} catch (UnsupportedEncodingException e) {
				log.error(e.getMessage());
			}
		}
		return new ModelAndView("/viewer/view.jsp",modelMap);
	}
	
	
	
}
