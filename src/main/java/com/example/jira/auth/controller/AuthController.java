package com.example.jira.auth.controller;

import com.example.jira.auth.exception.AppException;
import com.example.jira.auth.exception.InvalidOldPasswordException;
import com.example.jira.auth.model.Role;
import com.example.jira.auth.model.RoleName;
import com.example.jira.auth.model.User;
import com.example.jira.auth.payload.ApiResponse;
import com.example.jira.auth.payload.JwtAuthenticationResponse;
import com.example.jira.auth.payload.LoginRequest;
import com.example.jira.auth.payload.SignUpRequest;
import com.example.jira.auth.repository.RoleRepository;
import com.example.jira.auth.repository.UserRepository;
import com.example.jira.auth.security.JwtTokenProvider;
import com.example.jira.auth.utils.GenericResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;
import java.util.Locale;
import java.util.Optional;

@Api( description="API pour la gestion des authentifications.")
@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController implements CommandLineRunner {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private MessageSource messages;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @ApiOperation(value = "Operation de connexion")
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @ApiOperation(value = "Operation de création de compte (inscription)")
    @PreAuthorize("hasRole('ROLE_ADMIN')") // Seul l'admin peut creer des comptes
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest /*, Authentication authentication*/) {
        /*
        log.info("CURRENT USER {}", authentication.getName());
        log.info("ROLES {}", authentication.getAuthorities());
        log.info("USERS {}", authentication.getDetails());

         */

        if(userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(),
                signUpRequest.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }

    // NEED TO BE MOVED IN USER SERVICE
    public void changeUserPassword(final User user, final String password) {
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }
    public boolean checkIfValidOldPassword(final User user, final String oldPassword) {
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }
    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    @ResponseBody
    public GenericResponse changeUserPassword(Locale locale,
                                              @RequestParam("password") String password,
                                              @RequestParam("oldpassword") String oldPassword) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "";
        if (principal instanceof UserDetails) {
             username = ((UserDetails)principal).getUsername();
        } else {
             username = principal.toString();
        }

// getAuthorities() - Returns the authorities granted to the user.
//        System.out.println("User has authorities: " + userDetails.getAuthorities());
        Optional<User> user = userRepository.findByUsername( username );

        if (!checkIfValidOldPassword(user.get(), oldPassword)) {
            throw new InvalidOldPasswordException();
        }
        changeUserPassword(user.get(), password);
        return new GenericResponse("Mot de passe mis à jours avec succès");
    }

    /*##############################################################################################*/
    // Creation du compte de l'administrateur : automatiquement fait au démarrage de l'appli
    public void registerAdmin() {
        // Creating admin's account
        User admin = new User();
        admin.setName("administrator");
        admin.setUsername("admin");
        admin.setPassword("admin");
        // Verifying if the admin is already created & Creating it
        if(userRepository.existsByUsername(admin.getUsername())==false) {
            admin.setPassword(passwordEncoder.encode(admin.getPassword()));

            Role userRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                    .orElseThrow(() -> new AppException("Admin Role not set."));

            admin.setRoles(Collections.singleton(userRole));

            userRepository.save(admin);
        }
    }

    // @Override
    public void run(String... args) {
       registerAdmin();
    }

}