package com.blogging.security;

import com.blogging.entities.User;
import com.blogging.exceptions.ResourceNotFoundException;
import com.blogging.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // loading user from db by username
        User user = this.userRepository.findByEmail(username).orElseThrow(() ->
                new ResourceNotFoundException("User", "Email id" + username, 0L));
        return user;
    }
}
