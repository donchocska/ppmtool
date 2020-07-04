package com.doncho.ppmtool.services;

import com.doncho.ppmtool.domain.Backlog;
import com.doncho.ppmtool.domain.Project;
import com.doncho.ppmtool.domain.User;
import com.doncho.ppmtool.exceptions.ProjectIdException;
import com.doncho.ppmtool.repositories.BacklogRepository;
import com.doncho.ppmtool.repositories.ProjectRepository;
import com.doncho.ppmtool.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService
{
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private UserRepository userRepository;

    public Project saveOrUpdateProject(Project project, String userName)
    {
        try
        {
            User user = userRepository.findByUserName(userName);

            project.setUser(user);
            project.setProjectLeader(user.getUserName());
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());

            if(0 == project.getId())
            {
                Backlog backlog = new Backlog();
                project.setBacklog(backlog);
                backlog.setProject(project);
                backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            }

            if(project.getId() != 0 )
            {
                project.setBacklog(backlogRepository.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
            }

            return projectRepository.save(project);

        } catch (Exception e)
        {
            throw new ProjectIdException("Project ID " + project.getProjectIdentifier().toUpperCase() + " already exist!");
        }
    }

    public Project findProjectByIdentifier(String projectId)
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
