package com.Project.RMSSpring.repository;

import com.Project.RMSSpring.entity.Role;
import com.Project.RMSSpring.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<List<User>> findAllByRole(Role role);
}
