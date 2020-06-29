package com.doncho.ppmtool.repositories;

import com.doncho.ppmtool.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long>
{


}
