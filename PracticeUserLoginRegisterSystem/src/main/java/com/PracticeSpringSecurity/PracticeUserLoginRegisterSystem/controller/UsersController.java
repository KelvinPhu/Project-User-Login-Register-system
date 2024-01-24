package com.PracticeSpringSecurity.PracticeUserLoginRegisterSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.PracticeSpringSecurity.PracticeUserLoginRegisterSystem.dto.UsersDTO;
import com.PracticeSpringSecurity.PracticeUserLoginRegisterSystem.model.Users;
import com.PracticeSpringSecurity.PracticeUserLoginRegisterSystem.service.UsersService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersController {
	
	@Autowired
	private UsersService usersSevice;
	
	@GetMapping("/index")
	public String home() {
		return "index";
	}
	
	@GetMapping("/register")
	public String getReister(Model model) {
		UsersDTO usersDto = new UsersDTO();
		model.addAttribute("users", usersDto);
		return "register";
	}
	
	@PostMapping("/register/save")
	public String getSaveRegister(@Valid @ModelAttribute("users") UsersDTO usersDto, BindingResult result, Model model){
		
		Users checkUserExit = usersSevice.findByEmail(usersDto.getEmail());
		
		if(checkUserExit != null && checkUserExit.getEmail() != null && !checkUserExit.getEmail().isEmpty()) {
			result.rejectValue("email", null, "This email have already register with another account");
		}
		
		if(result.hasErrors()) {
			model.addAttribute("user", usersDto);
			return "/register";
		}
		
		usersSevice.saveNewUser(usersDto);
		return "redirect:/register?success";
	}
	
	@GetMapping("/login")
	public String getLogin() {
		return "login";
	}
	
	@GetMapping("/users")
	public String getAllUsers(Model model) {
		List<UsersDTO> usersDto = usersSevice.findAllUser();
		model.addAttribute("users", usersDto);
		return "users";
	}
}