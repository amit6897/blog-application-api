package com.blogging.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component  //component hm islie bna rhe h jisse hm isko autowire bhi kr ske
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    //commence method jb execute hoga jb unauthorized person access krne k ki koshish krega hmari authorized api's ko
    //to hme kuch nhi krna, yha se hm ek unauthorized status bhejenge ay error bhejenge unauthorized with the help of http response
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Denied !!");
    }
}
