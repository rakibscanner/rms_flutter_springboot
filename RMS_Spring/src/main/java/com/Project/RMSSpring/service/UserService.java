package com.Project.RMSSpring.service;

import com.Project.RMSSpring.entity.Role;
import com.Project.RMSSpring.entity.User;
import com.Project.RMSSpring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllWaiters() {
        return userRepository.findAllByRole(Role.WAITER).orElse(new ArrayList<>());
    }

    public List<User> getAllUsers() {
        return userRepository.findAllByRole(Role.USER).orElse(new ArrayList<>());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(
                        ()->  new UsernameNotFoundException("User Not Found With this Email Address"));
    }
}
