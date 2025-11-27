package be.skenteridis.movieapi.service;

import be.skenteridis.movieapi.dto.LoginDTO;
import be.skenteridis.movieapi.dto.RegisterDTO;
import be.skenteridis.movieapi.dto.SuccessLoginDTO;
import be.skenteridis.movieapi.exception.InvalidCredentialsException;
import be.skenteridis.movieapi.exception.ResourceAlreadyExistsException;
import be.skenteridis.movieapi.mapper.AuthMapper;
import be.skenteridis.movieapi.model.User;
import be.skenteridis.movieapi.repository.UserRepository;
import be.skenteridis.movieapi.security.JwtUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthService {
    private final BCryptPasswordEncoder encoder;
    private final UserRepository repository;
    private final JwtUtils jwtUtils;
    private final AuthMapper mapper;
    public AuthService(BCryptPasswordEncoder encoder, UserRepository repository, JwtUtils jwtUtils) {
        this.encoder = encoder;
        this.repository = repository;
        this.jwtUtils = jwtUtils;
        this.mapper = new AuthMapper();
    }

    public SuccessLoginDTO login(LoginDTO dto) {
        User user = repository.findByEmail(dto.getEmail());
        if(user == null || !encoder.matches(dto.getPassword(), user.getPassword())) throw new InvalidCredentialsException();
        return mapper.toDTO(
                user.getName(),
                jwtUtils.generate(dto.getEmail(), Map.of("userId", user.getId(), "role", user.getRole().name()))
        );
    }

    public void register(RegisterDTO dto) {
        if (repository.existsByEmail(dto.getEmail())) throw new ResourceAlreadyExistsException("Email is already used!");
        if (repository.existsByName(dto.getName())) throw new ResourceAlreadyExistsException("Username is already used!");
        repository.save(mapper.toUser(dto, encoder.encode(dto.getPassword())));
    }
}
