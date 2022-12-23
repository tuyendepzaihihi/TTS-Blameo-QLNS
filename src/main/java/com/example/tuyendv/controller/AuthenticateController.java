package com.example.tuyendv.controller;

import com.example.tuyendv.constant.Constant;
import com.example.tuyendv.entity.ERole;
import com.example.tuyendv.entity.Role;
import com.example.tuyendv.entity.User;
import com.example.tuyendv.payload.request.LoginRequest;
import com.example.tuyendv.payload.response.MessageResponse;
import com.example.tuyendv.payload.request.SignupRequest;
import com.example.tuyendv.payload.response.Response;
import com.example.tuyendv.repository.RoleRepository;
import com.example.tuyendv.repository.UserRepository;
import com.example.tuyendv.security.jwt.JwtUtils;
import com.example.tuyendv.util.ValueUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthenticateController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepo;

    @Autowired
    RoleRepository roleRepo;

    @Autowired
    PasswordEncoder passEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping("/information-user")
    public ResponseEntity<?> getUserInfoResponse() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Map<String, Object> account = new HashMap<>();
        User user = userRepo.findByUserName(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Username is not found!"));
        account.put("username", user.getUserName());
        account.put("fullName", user.getFullName());
        account.put("email", user.getEmail());
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority) //item -> item.getAuthority()
                .collect(Collectors.toList());
        account.put("roles", roles);
        return ResponseEntity.ok().body(new Response<>(account, 200, "User information successfully!"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtils.generateJwtToken(authentication);
        return ResponseEntity.ok().body(new Response<>(token, 200, "User login successfully!"));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
        if (ValueUtils.isEmail(signUpRequest.getEmail())) {
            if (userRepo.existsByUserName(signUpRequest.getUserName())) {
                return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
            }

            if (userRepo.existsByEmail(signUpRequest.getEmail())) {
                return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
            }

            User user = new User();
            BeanUtils.copyProperties(signUpRequest, user);
            user.setPassword(passEncoder.encode(signUpRequest.getPassword()));
            Set<Role> roles = new HashSet<>();
            roles.add(roleRepo.findByRole(Constant.ROLE_SUPER_ADMIN).orElseThrow(
                    () -> new RuntimeException("Role is not found!")
            ));
            user.setRoles(roles);
            user.setStatus(1);
            return ResponseEntity.ok().body(
                    new Response<>(userRepo.save(user), 200, "User registered successfully!"));
        }
        System.out.println(ValueUtils.isEmail(signUpRequest.getEmail()));
        return ResponseEntity.badRequest().body(new MessageResponse("Email invalidate!"));
    }
}
