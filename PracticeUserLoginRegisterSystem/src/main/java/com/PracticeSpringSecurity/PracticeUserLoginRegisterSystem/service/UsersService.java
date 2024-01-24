package com.PracticeSpringSecurity.PracticeUserLoginRegisterSystem.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.PracticeSpringSecurity.PracticeUserLoginRegisterSystem.dto.UsersDTO;
import com.PracticeSpringSecurity.PracticeUserLoginRegisterSystem.model.Users;

@Service
public interface UsersService extends UserDetailsService{
	
	public void saveNewUser(UsersDTO usersDto);
	Users findByEmail(String Email);
	public List<UsersDTO> findAllUser();
}
