package com.doncho.ppmtool.repositories;

import com.doncho.ppmtool.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long>
{
    User findByUserName(String userName);

    User getById(Long id);

    //Optional<User> findById(Long id);
}
