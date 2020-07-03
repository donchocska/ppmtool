package com.doncho.ppmtool.services;

import com.doncho.ppmtool.domain.User;
import com.doncho.ppmtool.exceptions.UserNameAlreadyExistsException;
import com.doncho.ppmtool.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder; // it is instantiated in Main class

    public User saveUser(User newUser){

        try
        {
            newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));

            //userName has to be unique
            newUser.setUserName(newUser.getUserName());

            // Make sure that password and confirmPassword match
            // We don't show or persist confirmPassword
            newUser.setConfirmPassword("");

            return userRepository.save(newUser);

        }
        catch (Exception e)
        {
            throw new UserNameAlreadyExistsException("UserName '" + newUser.getUserName() + "' already exists");
        }
    }


}
