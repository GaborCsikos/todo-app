package hu.gabor.csikos.todoapp.security;

import com.google.common.base.Optional;
import hu.gabor.csikos.todoapp.entity.JWTStore;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private final JWTStore jwtStore;
    private UserDetailsService userDetailsServiceImpl;

    public JWTAuthorizationFilter(JWTStore jwtStore, AuthenticationManager authManager, UserDetailsService userDetailsServiceImpl) {
        super(authManager);
        this.jwtStore = jwtStore;
        this.userDetailsServiceImpl = userDetailsServiceImpl;

    }


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        try {
            Optional<Authentication> auth = jwtStore.retrieveToken(request, userDetailsServiceImpl);
            SecurityContextHolder.getContext().setAuthentication(auth.get());
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
        }
        chain.doFilter(request, response);
    }


}