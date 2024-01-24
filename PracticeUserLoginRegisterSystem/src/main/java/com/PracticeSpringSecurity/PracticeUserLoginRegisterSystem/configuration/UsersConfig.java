package com.PracticeSpringSecurity.PracticeUserLoginRegisterSystem.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableMethodSecurity
public class UsersConfig {
	
	@Autowired
	private UserDetailsService userDetailSerice;
	
	@Bean
	public static PasswordEncoder passEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		http.csrf().disable().authorizeHttpRequests((authorize) -> {
			authorize.requestMatchers("/register/**").permitAll()
					 .requestMatchers("/index").permitAll()
					 .requestMatchers("/users").hasAuthority("USER")
					 .anyRequest().authenticated();
		}).formLogin(
			form -> form.loginPage("/login")
						.loginProcessingUrl("/login")
						.defaultSuccessUrl("/users")
						.permitAll()
		).logout(
			logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
							.permitAll()
		);
		return http.build();
	}
	
	@Autowired
	public void configGlobal(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userDetailSerice)
			.passwordEncoder(passEncoder());
	}
}
