package cihad.learning.socialmediaapp.controllers;

import cihad.learning.socialmediaapp.entities.RefreshToken;
import cihad.learning.socialmediaapp.entities.User;
import cihad.learning.socialmediaapp.security.JwtTokenProvider;
import cihad.learning.socialmediaapp.services.RefreshTokenService;
import cihad.learning.socialmediaapp.services.UserService;
import cihad.learning.socialmediaapp.services.requests.RefreshRequest;
import cihad.learning.socialmediaapp.services.requests.UserRequest;
import cihad.learning.socialmediaapp.services.responses.AuthenticationResponse;
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
@RestController
@RequestMapping("/auth")
public class AuthController {
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;
    private UserService userService;
    private PasswordEncoder passwordEncoder;
    private RefreshTokenService refreshTokenService;

    public AuthController(AuthenticationManager authenticationManager, UserService userService, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider, RefreshTokenService refreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody UserRequest loginRequest) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword());
        Authentication auth = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwtToken = jwtTokenProvider.generateJwtToken(auth);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        User user = userService.getByUserName(loginRequest.getUserName());
        authenticationResponse.setAccessToken("Bearer " + jwtToken);
        authenticationResponse.setRefreshToken(refreshTokenService.createRefreshToken(user));
        authenticationResponse.setUserId(user.getId());
        return authenticationResponse;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody UserRequest registerRequest) {
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        if(userService.getByUserName(registerRequest.getUserName()) != null) {
            authenticationResponse.setMessage("Username already in use.");
            return new ResponseEntity<>(authenticationResponse, HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        user.setUserName(registerRequest.getUserName());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        userService.add(user);

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(registerRequest.getUserName(), registerRequest.getPassword());
        Authentication auth = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwtToken = jwtTokenProvider.generateJwtToken(auth);

        authenticationResponse.setMessage("User successfully registered.");
        authenticationResponse.setAccessToken("Bearer " + jwtToken);
        authenticationResponse.setRefreshToken(refreshTokenService.createRefreshToken(user));
        authenticationResponse.setUserId(user.getId());

        return new ResponseEntity<>(authenticationResponse, HttpStatus.CREATED);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponse> refresh(@RequestBody RefreshRequest refreshRequest) {
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        RefreshToken refreshToken = refreshTokenService.getByUser(refreshRequest.getUserId());
        if(refreshToken.getToken().equals(refreshRequest.getRefreshToken()) &&
                !refreshTokenService.isRefreshExpired(refreshToken)) {

            User user = refreshToken.getUser();
            String jwtToken = jwtTokenProvider.generateJwtTokenByUserId(user.getId());
            authenticationResponse.setMessage("Token successfully refreshed.");
            authenticationResponse.setAccessToken("Bearer " + jwtToken);
            authenticationResponse.setUserId(user.getId());
            return new ResponseEntity<>(authenticationResponse, HttpStatus.OK);
        } else {
            authenticationResponse.setMessage("Refresh token is not valid.");
            return new ResponseEntity<>(authenticationResponse, HttpStatus.UNAUTHORIZED);
        }

    }

}
