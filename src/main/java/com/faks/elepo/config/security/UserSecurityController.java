package com.faks.elepo.config.security;

import com.faks.elepo.database.repository.UserRepository;
import com.faks.elepo.model.dto.LoginRequestDTO;
import com.faks.elepo.model.dto.RegisterUserDTO;
import com.faks.elepo.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class UserSecurityController {
    @Value("${application.secretKey}")
    private String secretKey;
    @Value("${application.jwtTokenDuration}")
    private Integer jwtTokenDuration;

    private final UserRepository userRepository;
    private final ContextReader contextReader;

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(8);

    public UserSecurityController(UserRepository userRepository, ContextReader contextReader) {
        this.userRepository = userRepository;
        this.contextReader = contextReader;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterUserDTO registerUserDTO) {
        Optional<User> optionalUser = userRepository.findByEmailOrUsername(registerUserDTO.getEmail(), registerUserDTO.getUsername());

        if (optionalUser.isPresent()) {
            return new ResponseEntity<>("User already exists!", HttpStatus.BAD_REQUEST);
        }

        userRepository.save(new User(
                registerUserDTO.getEmail(),
                bCryptPasswordEncoder.encode(registerUserDTO.getPassword()),
                registerUserDTO.getUsername(),
           "ROLE_USER"
        ));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO, HttpServletResponse response) {
        Optional<User> optionalUser = userRepository.findByEmail(loginRequestDTO.getEmail());

        if (optionalUser.isEmpty()) {
            return new ResponseEntity<>("User does not exist!", HttpStatus.FORBIDDEN);
        }
        User user = optionalUser.get();

        if (!bCryptPasswordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword())) {
            return new ResponseEntity<>("Invalid password!", HttpStatus.FORBIDDEN);
        }

        if (user.isDisabled()) {
            return new ResponseEntity<>("User account has been disabled!", HttpStatus.FORBIDDEN);
        }

        String role = userRepository.findByEmail(loginRequestDTO.getEmail()).get().getRole();
        String token = getJWTToken(loginRequestDTO.getEmail(), role);
        response.setHeader("access-token", token);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    private String getJWTToken(String email, String role) {
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(role);

        return Jwts
                .builder()
                .setId("elektronickoPoslovanje")
                .setSubject(email)
                .claim(
                        "authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList())
                )
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtTokenDuration))
                .signWith(
                        SignatureAlgorithm.HS512,
                        secretKey.getBytes()
                )
                .compact();
    }
}
