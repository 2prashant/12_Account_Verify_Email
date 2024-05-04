package com.becoder.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.becoder.entity.User;
import com.becoder.repository.UserRepo;

import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Override
	public User saveUser(User user,String url) {
		// TODO Auto-generated method stub
		
	    String	password=passwordEncoder.encode(user.getPassword());
		user.setPassword(password);
		user.setRole("ROLE_USER");
		
		user.setEnable(false);
		user.setVerificationCode(UUID.randomUUID().toString());
		
		User newuser =userRepo.save(user);
		
		if(newuser!=null)
		{
			sendEmail(newuser, url);
		}
		
		return newuser;
	}
	
	@Override
	public void sendEmail(User user, String url) {
		// TODO Auto-generated method stub
		
		String from="prashant91209755506@gmail.com";// (sender)
		String to=user.getEmail();//which user (reciver)
		String subject="Account Verfication";//subject
		String content="Dear[[name]],<br>"+"please click the link below to verfiy your registration: <br>"
		             +"<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"+"Thank you,"+"prashant";
		
		try {
			
			MimeMessage message=mailSender.createMimeMessage();
			MimeMessageHelper helper=new MimeMessageHelper(message);
			
			helper.setFrom(from,"prashant");
			helper.setTo(to);
			helper.setSubject(subject);
			
			content=content.replace("[[name]]", user.getName());
			String siteUrl=url+"/verify?code="+user.getVerificationCode();
			
			content=content.replace("[[URL]]", siteUrl);
			
			helper.setText(content, true);
			
			mailSender.send(message);
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	@Override
	public boolean verifyAccount(String veroficationCode) {
		// TODO Auto-generated method stub
		
		User user=userRepo.findByVerificationCode(veroficationCode);
		if(user==null)
		{
			return false;
		}else {
			user.setEnable(true);
			 user.setVerificationCode(null);
			 userRepo.save(user); 
			return true;
		}
		
	}

	@Override
	public void removeSessionMessage() {
		// TODO Auto-generated method stub
		
    HttpSession	 session=((ServletRequestAttributes)(RequestContextHolder.getRequestAttributes())).getRequest().getSession();
    session.removeAttribute("msg");
		
	}

	


}
