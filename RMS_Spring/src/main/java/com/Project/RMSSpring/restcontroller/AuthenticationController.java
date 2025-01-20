package com.Project.RMSSpring.restcontroller;


import com.Project.RMSSpring.entity.AuthenticationResponse;
import com.Project.RMSSpring.entity.User;
import com.Project.RMSSpring.service.AuthService;
import com.Project.RMSSpring.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class AuthenticationController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody User request
    ) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/register/admin")
    public ResponseEntity<AuthenticationResponse> registerAdmin(
            @RequestBody User request
    ) {
        return ResponseEntity.ok(authService.registerAdmin(request));
    }

    @PostMapping("/register/waiter")
    public ResponseEntity<AuthenticationResponse> registerWaiter(
            @RequestBody User request
    ) {
        return ResponseEntity.ok(authService.registerWaiter(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody User request
    ) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @GetMapping("/activate/{id}")
    public ResponseEntity<String> activateUser(@PathVariable("id") long id) {
        String response = authService.activateUser(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAllWaiters")
    public ResponseEntity<List<User>> getAllWaiters() {
        List<User> waiters = userService.getAllWaiters();
        return ResponseEntity.ok(waiters);
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
}
