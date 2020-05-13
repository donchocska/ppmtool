package com.doncho.ppmtool.services;

import com.doncho.ppmtool.domain.Project;
import com.doncho.ppmtool.exceptions.ProjectIdException;
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
        try
        {
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            return projectRepository.save(project);

        } catch (Exception e)
        {
            throw new ProjectIdException("Project ID" + project.getProjectIdentifier().toUpperCase() + "already exist!");
        }
    }

    public Project findProjectByIddentifier(String projectId)
    {
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

        if (project == null)
        {
            throw new ProjectIdException("Project ID '" + projectId.toUpperCase() + "' does not exist!");
        }

        return project;
    }

    public Iterable<Project> findAllProjects()
    {
        return projectRepository.findAll();
    }


    public void deleteProjectByIdentifier(String identifier)
    {
        Project project = projectRepository.findByProjectIdentifier(identifier);

        if (null == project)
        {
            throw new ProjectIdException("Project ID '" + identifier.toUpperCase() + "' does not exist!");
        }

        projectRepository.delete(project);

    }

}
