package com.PracticeSpringSecurity.PracticeUserLoginRegisterSystem.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersDTO {
	
	private Long id;
	
	@NotEmpty
	private String name;
	
	@Email(message = "Invalid email address")
	@NotEmpty(message = "Email cannot be empty")
	private String email;
	
	@NotEmpty(message = "Password cannot be empty")
	private String password;
}
