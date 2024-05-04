package com.becoder.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.becoder.entity.User;
import com.becoder.repository.UserRepo;

@RestController
@RequestMapping("/user")
public class UserController {
	
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
	public ModelAndView profile()
	{
		String viewName="profile";
		return new ModelAndView(viewName);
	}
}
