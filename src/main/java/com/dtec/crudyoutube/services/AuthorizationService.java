package com.dtec.crudyoutube.services;

import com.dtec.crudyoutube.domain.user.AuthenticateDTO;
import com.dtec.crudyoutube.domain.user.LoginResponseDTO;
import com.dtec.crudyoutube.domain.user.RegisterDTO;
import com.dtec.crudyoutube.domain.user.User;
import com.dtec.crudyoutube.repositories.UserRepository;
import com.dtec.crudyoutube.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class AuthorizationService implements UserDetailsService {

    @Autowired
    private ApplicationContext context;

    @Autowired
    UserRepository repository;

    @Autowired
    private TokenService tokenService;

    private AuthenticationManager authenticationManager;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByLogin(username);
    }

    public ResponseEntity<Object> login(@RequestBody @Valid AuthenticateDTO data) {
        authenticationManager = context.getBean(AuthenticationManager.class);

        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    public ResponseEntity<Object> register(@RequestBody RegisterDTO registerDTO) {
        if (this.repository.findByLogin(registerDTO.login()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDTO.password());

        User newUser = new User(registerDTO.login(), encryptedPassword, registerDTO.role());
        this.repository.save(newUser);

        return ResponseEntity.ok(newUser);
    }
}
