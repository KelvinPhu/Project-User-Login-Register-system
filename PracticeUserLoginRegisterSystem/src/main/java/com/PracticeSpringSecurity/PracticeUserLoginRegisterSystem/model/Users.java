package com.PracticeSpringSecurity.PracticeUserLoginRegisterSystem.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
	name = "users",
	uniqueConstraints = {
		@UniqueConstraint(columnNames = {"email"})
	}
)
public class Users {
	
	private static final Long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long users_id;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private String email;
	@Column(nullable = false)
	private String password;
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(
		name = "users_roles",
		joinColumns = {@JoinColumn(name = "users_id", referencedColumnName = "users_id")},
		inverseJoinColumns = {@JoinColumn(name = "roles_id", referencedColumnName = "roles_id")}
	)
	private List<Roles> roles = new ArrayList<>();
}
