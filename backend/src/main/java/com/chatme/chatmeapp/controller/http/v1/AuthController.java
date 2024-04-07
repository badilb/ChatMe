package com.chatme.chatmeapp.controller.http.v1;

import com.chatme.chatmeapp.exception.UsernameTakenException;
import com.chatme.chatmeapp.models.dto.LoginDTO;
import com.chatme.chatmeapp.models.dto.RegistrationDTO;
import com.chatme.chatmeapp.models.entity.UserEntity;
import com.chatme.chatmeapp.models.enums.RoleType;
import com.chatme.chatmeapp.service.RoleService;
import com.chatme.chatmeapp.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
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
        SecurityContext emptyContext = SecurityContextHolder.createEmptyContext();
        emptyContext.setAuthentication(authenticationResponse);
        SecurityContextHolder.setContext(emptyContext);
        contextRepository.saveContext(emptyContext, request, response);
        log.info("User logged in: {}", loginDTO.getUsername());
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody RegistrationDTO registrationDTO) {
        // Check if the username exists in the database or not
        String username = registrationDTO.getUsername();
        if (userService.findByUsername(username) != null) {
            throw new UsernameTakenException(username);
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setName(registrationDTO.getName());
        userEntity.setSurname(registrationDTO.getSurname());
        userEntity.setUsername(registrationDTO.getUsername());
        userEntity.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
        userEntity.setRoles(Collections.singletonList(roleService.findByName(RoleType.USER)));

        userService.saveUser(userEntity);
        log.info("User authorized to system: {}", registrationDTO.getUsername());
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        DeferredSecurityContext deferredContext = contextRepository.loadDeferredContext(request);
        SecurityContext sessionContext = deferredContext.get();
        String username = sessionContext.getAuthentication().getName();

        SecurityContext emptyContext = SecurityContextHolder.createEmptyContext();
        SecurityContextHolder.setContext(emptyContext);
        contextRepository.saveContext(emptyContext, request, response);
        log.info("User logged out: {}", username);
    }
}
