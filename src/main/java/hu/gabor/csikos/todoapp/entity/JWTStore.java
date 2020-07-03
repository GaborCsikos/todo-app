package hu.gabor.csikos.todoapp.entity;

import com.google.common.base.Optional;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class JWTStore {

    private static final int EXPIRATION = 60 * 1000;

    private final byte[] secret;

    public JWTStore(byte[] secret) {
        this.secret = secret;
    }

    public String generateToken(HttpServletRequest request, Authentication auth) {
        return generateToken(auth);
    }

    public String generateToken(Authentication auth) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(auth.getName())
                .claim("authorities", auth.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + EXPIRATION))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public Optional<Authentication> retrieveToken(HttpServletRequest request, UserDetailsService userDetailsServiceImpl) {
        UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(getUsername(request.getHeader(HttpHeaders.AUTHORIZATION)));
        return Optional.of(new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities()));

    }


    public String generateToken(String userName, List<GrantedAuthority> authorities) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(userName)
                .claim(userName, authorities)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + EXPIRATION))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }


    public String getUsername(String token) {

        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token.substring(7)).getBody().getSubject();

    }


}