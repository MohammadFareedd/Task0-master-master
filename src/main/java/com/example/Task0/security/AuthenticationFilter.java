package com.example.Task0.security;


import com.example.Task0.service.BlacklistTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.stream.Stream;

@Configuration
public class AuthenticationFilter extends OncePerRequestFilter {
    private static final String[] excluded_urls = {
            "/login",
            "/users"
    };


    private UserDetailsService userDetailsService;

    private Jwt jwt;
    private AntPathMatcher pathMatcher;
    private BlacklistTokenService blacklistTokenService;
    @Autowired
    public AuthenticationFilter(UserDetailsService userDetailsService, Jwt jwt, AntPathMatcher pathMatcher, BlacklistTokenService blacklistTokenService) {
        this.userDetailsService = userDetailsService;
        this.jwt = jwt;
        this.pathMatcher = pathMatcher;
        this.blacklistTokenService = blacklistTokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException,java.io.IOException {

       String a = request.getHeader("Authorization");

       if(a!=null && blacklistTokenService.findBlacklistTokenByName( (a.split((" ")))[1])!=null) {


           String token = (a.split((" ")))[1];

           String userName = Jwt.extractUsername(token);


           UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

           if (userDetails==null){

               response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

           }
           else if (jwt.validateToken(token, userDetails)) {

               UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
               authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
               SecurityContextHolder.getContext().setAuthentication(authToken);
               filterChain.doFilter(request, response);

           }

       }
        else{response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);}




    }
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String url = request.getRequestURI();
        if(request.getMethod().equals("POST"))
            return Stream.of(excluded_urls).anyMatch(x -> pathMatcher.match(x, url));
        return false;
    }
}
