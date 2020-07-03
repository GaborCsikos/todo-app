package hu.gabor.csikos.todoapp.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.gabor.csikos.todoapp.dto.JWTTokenDTO;
import hu.gabor.csikos.todoapp.dto.UserDTO;
import hu.gabor.csikos.todoapp.entity.JWTStore;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;


public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JWTStore jwtStore;
    private final AuthenticationManager authenticationManager;


    public JWTAuthenticationFilter(JWTStore jwtStore, AuthenticationManager authenticationManager) {
        this.jwtStore = jwtStore;
        this.authenticationManager = authenticationManager;
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/login", "POST"));

    }


    @Override

    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        UserDTO credentials = readUserCredentials(request);
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                credentials.getUsername(), credentials.getPassword(), Collections.emptyList());

        return authenticationManager.authenticate(authToken);

    }


    private UserDTO readUserCredentials(HttpServletRequest request) {
        try {
            return new ObjectMapper().readValue(request.getInputStream(), UserDTO.class);
        } catch (IOException ioe) {
            throw new BadCredentialsException("Invalid request", ioe);
        }

    }


    @Override

    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,

                                            Authentication auth) throws IOException, ServletException {

        String token = jwtStore.generateToken(request, auth);
        JWTTokenDTO responseDTO = new JWTTokenDTO(token);
        String tokenDTOAsString = new ObjectMapper().writeValueAsString(responseDTO);
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(tokenDTOAsString);
        out.flush();
    }


    @Override

    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication Failed");
    }

}