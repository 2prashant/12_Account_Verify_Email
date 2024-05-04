package com.becoder.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import com.becoder.entity.User;
import com.becoder.repository.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.becoder.entity.User;
import com.becoder.repository.UserRepo;

@RestController
@RequestMapping("/admin")
public class AdminController {
	
    @Autowired
	private UserRepo userRepo;
	
    @ModelAttribute
	public void commonUser(Principal p,Model m)
	{
		if(p!=null)
		{
			String email=p.getName();
			User user=userRepo.findByEmail(email);
			m.addAttribute("user",user);
		}
	}
	
	@GetMapping("/profile")
	public ModelAndView profile(Principal p)
	{
		String viewName="admin_profile";
		return new ModelAndView(viewName);
	}
	
}


