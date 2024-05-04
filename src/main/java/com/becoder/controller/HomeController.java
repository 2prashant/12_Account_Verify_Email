package com.becoder.controller;

import java.lang.ProcessBuilder.Redirect;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AfterDomainEventPublication;
import org.springframework.data.repository.query.Param;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.becoder.entity.User;
import com.becoder.repository.UserRepo;
import com.becoder.service.UserService;
import com.becoder.service.UserServiceImpl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class HomeController
{
	
	@Autowired
	private UserServiceImpl userServiceImpl;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private UserService userService;
	
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
	
      @GetMapping("/")
      public ModelAndView index()
      {
    	  String viewName="index";
    	  return new ModelAndView(viewName);
      }
      
      @GetMapping("/register")
      public ModelAndView register()
      {
    	  String viewName="register";
    	  Map<String, Object> model=new HashMap<>();
    	  model.put("userdata",new User());
    	  return new ModelAndView(viewName,model);
      }
      
      @GetMapping("/signin")
      public ModelAndView login()
      {
    	  String viewName="login";
    	  return new ModelAndView(viewName);
      }
      
//      @GetMapping("/user/profile")
//      public ModelAndView profile(Principal p)
//      {
//    	  String viewName="profile";
//    	  String email=p.getName();
//    	  User  user=userRepo.findByEmail(email);
//    	  Map<String, Object> model=new HashMap<>();
//    	  model.put("user",user);
//    	  return new ModelAndView(viewName,model);
//      }
//      
      @GetMapping("/user/home")
      public ModelAndView home()
      {
    	  String viewName="home";
    	  return new ModelAndView(viewName);
      }
      
      @PostMapping("/register")
      public ModelAndView saveUser(@ModelAttribute User user,HttpSession session,HttpServletRequest request) 
      { 
    	  String url=request.getRequestURL().toString();
    	//  System.out.println(url);  //http:localhost:8080/saveUser
    	  
    	    url=url.replace(request.getServletPath(),"");
//    	    System.out.println(url);       //http://localhost:8072/verify?code=3453sdfsdcsadcscd
    	    
          User u=userServiceImpl.saveUser(user,url);
          if(u!=null)
          {
        	 // System.out.println("save sucess");
        	  session.setAttribute("msg", "Register Successfully");
          }
          else {
			//System.out.println("error in server");
        	  session.setAttribute("msg", "Something wrong server");
        	  
		}
          
        RedirectView rd=new RedirectView();
         rd.setUrl("/register");
          return new ModelAndView(rd);
      }
      
      @GetMapping("/verify")
      public ModelAndView verifyAccount(@Param("code") String code)
      {
    	  String  viewName="message";
    	  boolean f=userService.verifyAccount(code);
    	  Map<String, Object> model=new HashMap<>();
    	  if(f)
    	  {
    		  model.put("msg","Sucessfull your account is verified");
    	  }
    	  else {
			model.put("msg", "may be your vefication code is incorrect or already verified");
		}
    	  
    	 return new ModelAndView(viewName,model); 
      }
      
      
      
}
