package com.PracticeSpringSecurity.PracticeUserLoginRegisterSystem.service;


import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.PracticeSpringSecurity.PracticeUserLoginRegisterSystem.dto.UsersDTO;
import com.PracticeSpringSecurity.PracticeUserLoginRegisterSystem.model.Roles;
import com.PracticeSpringSecurity.PracticeUserLoginRegisterSystem.model.Users;
import com.PracticeSpringSecurity.PracticeUserLoginRegisterSystem.repository.RolesRepository;
import com.PracticeSpringSecurity.PracticeUserLoginRegisterSystem.repository.UsersRepository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class UsersServiceImplement implements UsersService{
	
	@Autowired
	private UsersRepository usersRepo;
	@Autowired
	private RolesRepository rolesRepo;
	@Autowired
	private PasswordEncoder passEncoder;

	@Override
	public void saveNewUser(UsersDTO usersDto) {
		Users users = new Users();
		users.setName(usersDto.getName());
		users.setEmail(usersDto.getEmail());
		users.setPassword(passEncoder.encode(usersDto.getPassword()));
		
		Roles roles = rolesRepo.findByRolesName("USER");
		if(roles == null) {
			roles = checkExitRole();
		}
		users.setRoles(Arrays.asList(roles));
		usersRepo.save(users);
	}

	private Roles checkExitRole() {
		Roles roles = new Roles();
		roles.setRolesName("USER");
		return rolesRepo.save(roles);
	}

	@Override
	public Users findByEmail(String Email) {
		return usersRepo.findByEmail(Email);
	}

	@Override
	public List<UsersDTO> findAllUser() {
		List<Users> users = usersRepo.findAll();
		return users.stream()
					.map((user) -> mapToUsers(user))
					.collect(Collectors.toList());
	}

	private UsersDTO mapToUsers(Users user) {
		UsersDTO usersDto = new UsersDTO();
	    String[] str = user.getName().split(" ");
	    usersDto.setName(str[0]);
	    usersDto.setEmail(user.getEmail());
	    return usersDto;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Users users = usersRepo.findByEmail(email);
		if(users != null) {
			return new User(
				users.getEmail(),
				users.getPassword(),
				mapolesToAuthorize(users.getRoles())
			);
		}else {
			throw new UsernameNotFoundException("email or password does ot exit");
		}
	}

	private Collection<? extends GrantedAuthority> mapolesToAuthorize(List<Roles> roles) {
		Collection<? extends GrantedAuthority> mapRoles = roles.stream()
				.map(role -> new SimpleGrantedAuthority(role.getRolesName()))
				.collect(Collectors.toSet());
		return mapRoles;
	}

}
