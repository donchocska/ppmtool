package com.doncho.ppmtool.services;

import com.doncho.ppmtool.domain.Project;
import com.doncho.ppmtool.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService
{
    @Autowired
    private ProjectRepository projectRepository;

    public Project saveOrUpdateProject(Project project)
    {
        //Logic
        return projectRepository.save(project);
    }

}
