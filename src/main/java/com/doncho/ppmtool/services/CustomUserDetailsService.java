package com.doncho.ppmtool.services;

import com.doncho.ppmtool.domain.User;
import com.doncho.ppmtool.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CustomUserDetailsService implements UserDetailsService
{
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException
    {
        User user = userRepository.findByUserName(userName);

        if(null == user)
        {
            new UsernameNotFoundException("User not found!");
        }

        return user;
    }

    @Transactional
    public User loadUserById(Long id){

        User user = userRepository.getById(id);

        if(null == user)
        {
            new UsernameNotFoundException("User not found!");
        }

        return user;
    }
}
