package com.doncho.ppmtool.repositories;

import com.doncho.ppmtool.domain.Backlog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BacklogRepository extends CrudRepository<Backlog, Long>
{
    public Backlog findByProjectIdentifier(String identifier);
}
