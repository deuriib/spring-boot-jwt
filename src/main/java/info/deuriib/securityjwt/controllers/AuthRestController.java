package info.deuriib.securityjwt.controllers;

import info.deuriib.securityjwt.models.Role;
import info.deuriib.securityjwt.models.User;
import info.deuriib.securityjwt.repositories.IRoleRepository;
import info.deuriib.securityjwt.repositories.IUserRepository;
import info.deuriib.securityjwt.requests.AddRoleToUserRequest;
import info.deuriib.securityjwt.requests.LoginUserRequest;
import info.deuriib.securityjwt.requests.RegisterUserRequest;
import info.deuriib.securityjwt.responses.AuthResponse;
import info.deuriib.securityjwt.security.JwtService;
import info.deuriib.securityjwt.security.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Date;

@RestController
@RequestMapping("/api/auth")
public class AuthRestController {
    private final AuthenticationManager _authenticationManager;
    private final PasswordEncoder _passwordEncoder;
    private final IRoleRepository _roleRepository;
    private final IUserRepository _userRepository;
    private final JwtService _jwtService;

    @Autowired
    public AuthRestController(AuthenticationManager authenticationManager,
                              PasswordEncoder passwordEncoder,
                              IRoleRepository roleRepository,
                              IUserRepository userRepository,
                              JwtService jwtService) {
        _authenticationManager = authenticationManager;
        _passwordEncoder = passwordEncoder;
        _roleRepository = roleRepository;
        _userRepository = userRepository;
        _jwtService = jwtService;
    }

    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody RegisterUserRequest request) {

        if (_userRepository.existsByUsername(request.username())) {
            return new ResponseEntity<>("User already registered, try another name", HttpStatus.BAD_REQUEST);
        }

        System.out.println("USERNAME: " + request.username());

        final User user = new User();
        user.setUsername(request.username());
        user.setPassword(_passwordEncoder.encode(request.password()));

        Role role = null;
        if (_roleRepository.findByName(SecurityConstants.ROLE_USER).isPresent()) {
            role = _roleRepository.findByName(SecurityConstants.ROLE_USER).get();
        } else {
            role = new Role();
            role.setName(SecurityConstants.ROLE_USER);
        }

        user.setRoles(Collections.singletonList(role));

        _userRepository.save(user);

        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }

    @PostMapping("register-admin")
    public ResponseEntity<String> registerAdmin(@RequestBody RegisterUserRequest request) {
        if (_userRepository.existsByUsername(request.username())) {
            return new ResponseEntity<>("User already registered, try another name", HttpStatus.BAD_REQUEST);
        }

        final User user = new User();
        user.setUsername(request.username());
        user.setPassword(_passwordEncoder.encode(request.password()));

        Role role = null;
        if (_roleRepository.findByName(SecurityConstants.ROLE_ADMIN).isPresent()) {
            role = _roleRepository.findByName(SecurityConstants.ROLE_ADMIN).get();
        } else {
            role = new Role();
            role.setName(SecurityConstants.ROLE_ADMIN);
        }

        user.setRoles(Collections.singletonList(role));

        _userRepository.save(user);

        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }

    @PostMapping("login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginUserRequest request) {
        final Authentication authentication = _authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        request.username(), request.password()
                ));

        SecurityContextHolder
                .getContext()
                .setAuthentication(authentication);

        final String token = _jwtService.generateToken(authentication);

        final Date expiryDate = _jwtService
                .getClaimsFromToken(token)
                .getExpiration();

        return new ResponseEntity<>(new AuthResponse(token, expiryDate), HttpStatus.OK);
    }

    @PostMapping
    @RequestMapping("/api/admin/add-role")
    public ResponseEntity<String> addRoleToUser(@RequestBody AddRoleToUserRequest request) {

        if (_roleRepository.findById(request.roleId()).isEmpty()
                || _userRepository.findById(request.userId()).isEmpty()) {
            return new ResponseEntity<>("Something went wrong adding role to the user", HttpStatus.BAD_REQUEST);
        }

        final Role role = _roleRepository.findById(request.roleId()).get();
        final User user = _userRepository.findById(request.userId()).get();
        user.setRoles(Collections.singletonList(role));

        _userRepository.save(user);

        return new ResponseEntity<>("Role added to user successfully", HttpStatus.OK);
    }

}
