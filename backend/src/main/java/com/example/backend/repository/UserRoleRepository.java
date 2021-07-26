package com.example.backend.repository;


import com.example.backend.model.UserRole;
import com.example.backend.model.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, String> {
    Optional<UserRole> findByRole(RoleEnum roleEnum);

}
