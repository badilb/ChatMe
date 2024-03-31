package com.chatme.chatmeapp.controller.http.v1;

import com.chatme.chatmeapp.models.dto.LoginDTO;
import com.chatme.chatmeapp.models.dto.RegistrationDTO;
import com.chatme.chatmeapp.models.entity.Role;
import com.chatme.chatmeapp.models.entity.UserEntity;
import com.chatme.chatmeapp.service.RoleService;
import com.chatme.chatmeapp.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.DeferredSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository contextRepository = new HttpSessionSecurityContextRepository();

    @Autowired
    public AuthController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public void login(@RequestBody LoginDTO loginDTO, HttpServletRequest request, HttpServletResponse response) {
        Authentication authenticationRequest =
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());
        Authentication authenticationResponse =
                authenticationManager.authenticate(authenticationRequest);
        SecurityContext newContext = SecurityContextHolder.createEmptyContext();
        newContext.setAuthentication(authenticationResponse);
        SecurityContextHolder.setContext(newContext);
        contextRepository.saveContext(newContext, request, response);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody RegistrationDTO registrationDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setName(registrationDTO.getName());
        userEntity.setSurname(registrationDTO.getSurname());
        userEntity.setUsername(registrationDTO.getUsername());
        userEntity.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
        userEntity.setRoles(Collections.singletonList(roleService.findByName(Role.Types.USER.name())));

        userService.saveUser(userEntity);
    }
}
