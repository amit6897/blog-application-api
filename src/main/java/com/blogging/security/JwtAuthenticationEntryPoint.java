package com.blogging.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component  //we are creating it as component so that we can auto-wire it as well
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    //commence method jb execute hoga jb unauthorized person access krne k ki koshish krega hmari authorized api's ko
    //to hme kuch nhi krna, yha se hm ek unauthorized status bhejenge ay error bhejenge unauthorized with the help of http response
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Denied !!");
    }
}
