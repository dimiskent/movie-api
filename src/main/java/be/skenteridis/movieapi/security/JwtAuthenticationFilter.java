package be.skenteridis.movieapi.security;

import be.skenteridis.movieapi.model.User;
import be.skenteridis.movieapi.repository.UserRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter implements Filter {
    private final JwtUtils utils;
    private final UserRepository repository;
    public JwtAuthenticationFilter(JwtUtils utils, UserRepository repository) {
        this.utils = utils;
        this.repository = repository;
    }
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String token = ((HttpServletRequest) servletRequest).getHeader("Authorization");
        if(token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            if(utils.isValid(token)) {
                User user = repository.findByEmail(utils.getSubject(token));
                if(user != null) {
                    SecurityContextHolder.getContext().setAuthentication(
                            new UsernamePasswordAuthenticationToken(
                                    user.getEmail(),
                                    null,
                                    List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
                            )
                    );
                }
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
