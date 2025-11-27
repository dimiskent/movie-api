package be.skenteridis.movieapi.mapper;

import be.skenteridis.movieapi.dto.RegisterDTO;
import be.skenteridis.movieapi.dto.SuccessLoginDTO;
import be.skenteridis.movieapi.model.User;

public class AuthMapper {
    public User toUser(RegisterDTO dto, String hashedPassword) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(hashedPassword);
        return user;
    }
    public SuccessLoginDTO toDTO(String username, String token) {
        SuccessLoginDTO dto = new SuccessLoginDTO();
        dto.setName(username);
        dto.setToken(token);
        return dto;
    }
}
