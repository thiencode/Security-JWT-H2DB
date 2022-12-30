package com.thiencode.springbootsecurityhibernatejwt.controller;

import com.thiencode.springbootsecurityhibernatejwt.jwt.JwtTokenProvider;
import com.thiencode.springbootsecurityhibernatejwt.model.CustomUserDetail;
import com.thiencode.springbootsecurityhibernatejwt.payload.LoginRequest;
import com.thiencode.springbootsecurityhibernatejwt.payload.LoginResponse;
import com.thiencode.springbootsecurityhibernatejwt.payload.RandomStuff;
import com.thiencode.springbootsecurityhibernatejwt.repository.UserRepository;
import com.thiencode.springbootsecurityhibernatejwt.user.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class MainController {

    final JwtTokenProvider tokenProvider;
    final AuthenticationManager authenticationManager;

    final PasswordEncoder passwordEncoder;

    final UserRepository userRepository;

    public MainController(JwtTokenProvider tokenProvider, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }


    @PostMapping("/login")
    public LoginResponse authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        // Xác thực từ username và password.
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        // Nếu không xảy ra exception tức là thông tin hợp lệ
        // Set thông tin authentication vào Security Context
//        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Trả về jwt cho người dùng.
        String jwt = tokenProvider.generateToken((CustomUserDetail) authentication.getPrincipal());
        return new LoginResponse(jwt);
    }

    // Api /api/random yêu cầu phải xác thực mới có thể request
    @GetMapping("/random")
    public RandomStuff randomStuff() {
        return new RandomStuff("JWT Hợp lệ mới có thể thấy được message này");
    }

    @PostMapping("/create")
    public RandomStuff createAcc(@Valid @RequestBody LoginRequest loginRequest) {
        User user = new User();
        user.setUsername(loginRequest.getUsername());
        user.setPassword(passwordEncoder.encode(loginRequest.getPassword()));
        userRepository.save(user);
        if (user == null)
            return new RandomStuff("Chưa tạo được tài khoản!");
        else
            return new RandomStuff("Tạo tài khoản thành công!");

    }
}
