package PaperWeight.Biscuit_corpo.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import PaperWeight.Biscuit_corpo.entity.ERole;
import PaperWeight.Biscuit_corpo.entity.Roles;
import PaperWeight.Biscuit_corpo.entity.User;
import PaperWeight.Biscuit_corpo.repository.RoleRepository;
import PaperWeight.Biscuit_corpo.repository.UserRepository;
import PaperWeight.Biscuit_corpo.request.LoginRequest;
import PaperWeight.Biscuit_corpo.request.SignupRequest;
import PaperWeight.Biscuit_corpo.response.JwtResponse;
import PaperWeight.Biscuit_corpo.response.MessageResponse;
import PaperWeight.Biscuit_corpo.secuitry.jwt.JwtUtil;
import PaperWeight.Biscuit_corpo.service.UserDetailsImpl;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserRepository userRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtil jwtUtils;

  @GetMapping("/")
  public String Tester() {
    var a = "b";
    return "worked";
  }
  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);

    //UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal(); fcuk this line
    //System.out.println("\n\n\nUser roles: " + userDetails.getAuthorities()); y tf this not working >:(
    /*List<String> roles = userDetails.getAuthorities().stream()
        .map(item -> item.getAuthority())
        .collect(Collectors.toList());
    return ResponseEntity.ok(new JwtResponse(jwt,
                         userDetails.getUsername(),
                         roles));*/
    User temp = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User couldnt be found using this username"));

    return ResponseEntity.ok(new JwtResponse(jwt,
                         temp.getUsername(),temp.getRoles()
                         ));
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity
              .badRequest()
              .body(new MessageResponse("Error: Username is already taken!"));
    }

    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity
              .badRequest()
              .body(new MessageResponse("Error: Email is already in use!"));
    }

    // Create new user's account
    User user = new User(signUpRequest.getUsername(),
            signUpRequest.getEmail(),
            encoder.encode(signUpRequest.getPassword()));

    Set<Roles> roles = new HashSet<>();
    Roles userRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: bruh auth role"));
    roles.add(userRole);

    user.setRoles(roles);

    userRepository.save(user);

    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }
}
    /*
    if (strRoles == null) {
      Roles userRole = roleRepository.findByName(ERole.ROLE_USER)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(userRole);
    } else {
      strRoles.forEach(role -> {
        switch (role) {
          case "admin":
            Roles adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(adminRole);

            break;
          case "mod":
            Roles modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(modRole);

            break;
          default:
            Roles userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        }
      });
    }*/
