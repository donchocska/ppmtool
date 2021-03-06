package com.doncho.ppmtool.services;

import com.doncho.ppmtool.domain.Backlog;
import com.doncho.ppmtool.domain.Project;
import com.doncho.ppmtool.domain.User;
import com.doncho.ppmtool.exceptions.ProjectIdException;
import com.doncho.ppmtool.exceptions.ProjectNotFoundException;
import com.doncho.ppmtool.repositories.BacklogRepository;
import com.doncho.ppmtool.repositories.ProjectRepository;
import com.doncho.ppmtool.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;

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
        // project.getId != null
        // find by db id -> null

        if(project.getId() != null)
        {
            Project existingProject = projectRepository.findByProjectIdentifier(project.getProjectIdentifier());

            if(null != existingProject && (!existingProject.getProjectLeader().equals(userName)))
            {
                throw new ProjectNotFoundException("Project with id:[" + project.getProjectIdentifier() + "] is not found in your account!");
            }
            else if(null == existingProject)
            {
                throw new ProjectNotFoundException("Project with id:[" + project.getProjectIdentifier() + "] cannot be updated, because does not exist!");
            }
        }

        try
        {
            User user = userRepository.findByUserName(userName);

            project.setUser(user);
            project.setProjectLeader(user.getUserName());
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());

            if(null == project.getId() || 0 == project.getId())
            {
                Backlog backlog = new Backlog();
                project.setBacklog(backlog);
                backlog.setProject(project);
                backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            }

            if(null != project.getId())
            {
                project.setBacklog(backlogRepository.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
            }

            return projectRepository.save(project);

        } catch (Exception e)
        {
            throw new ProjectIdException("Project ID " + project.getProjectIdentifier().toUpperCase() + " already exist!");
        }
    }

    public Project findProjectByIdentifier(String projectId, String userName)
    {
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

        if (project == null)
        {
            throw new ProjectIdException("Project ID '" + projectId.toUpperCase() + "' does not exist!");
        }

        if(!project.getProjectLeader().equals(userName))
        {
            throw new ProjectNotFoundException("Project not found in your account");
        }

        return project;
    }

    public Iterable<Project> findAllProjects(String userName)
    {
        //return projectRepository.findAll();
        return projectRepository.findAllByProjectLeader(userName);
    }


    public void deleteProjectByIdentifier(String identifier, String userName)
    {
//        Project project = projectRepository.findByProjectIdentifier(identifier);
//
//        if (null == project)
//        {
//            throw new ProjectIdException("Project ID '" + identifier.toUpperCase() + "' does not exist!");
//        }

        projectRepository.delete(findProjectByIdentifier(identifier, userName));

    }

}
