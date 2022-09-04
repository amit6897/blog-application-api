package com.blogging.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//token k sath hme jitne bhi operations perform krne h wo sb is class k ander honge
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        //1. get token
        String bearerToken = request.getHeader("Authorization");
        // token is something like - Bearer 213233423dsdd
        String token = null;
        String username = null;

        if (request!=null && bearerToken.startsWith("Bearer ")) {
            token = bearerToken.substring(7);   //this is a token without Bearer
            try {
                username = this.jwtTokenHelper.getUsernameFromToken(token);
            }
            catch(IllegalArgumentException a){
                System.out.println("Unable to get Jwt token");
            }
            catch(ExpiredJwtException c) {
                System.out.println("Jwt has expired");
            }
            catch(MalformedJwtException e) {
                System.out.println("Invalid jwt");
            }
        }
        else {
            System.out.println("JWT token does not begin with Bearer");
        }

        // once we get the token, validate the received token
        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if(this.jwtTokenHelper.validateToken(token, userDetails)) {
                // shi chal rha hai
                // authentication karna h
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
            else {
                System.out.println("Invalid JWT token");
            }
        }
        else {
            System.out.println("USERNAME IS NULL OR CONTEXT IS NOT NULL");
        }

        filterChain.doFilter(request, response);
    }
}
