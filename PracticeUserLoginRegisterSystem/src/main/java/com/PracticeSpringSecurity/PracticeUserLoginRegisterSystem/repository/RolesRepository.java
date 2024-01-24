package com.PracticeSpringSecurity.PracticeUserLoginRegisterSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.PracticeSpringSecurity.PracticeUserLoginRegisterSystem.model.Roles;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Long>{
	Roles findByRolesName(String rolesName);
}
