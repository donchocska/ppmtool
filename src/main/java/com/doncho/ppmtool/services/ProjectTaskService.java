package com.doncho.ppmtool.services;

import com.doncho.ppmtool.domain.Backlog;
import com.doncho.ppmtool.domain.ProjectTask;
import com.doncho.ppmtool.repositories.BacklogRepository;
import com.doncho.ppmtool.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectTaskService
{
    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;


    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask)
    {
        //Exceptions: Project not found

        // PTs to be added to a specific project, project!= null, BL exists
        Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);

        // set the BL to PT
        projectTask.setBacklog(backlog);

        // We want our project sequence to be like this IDPRO-1 IDPRO-2 ...100 101
        int backlogSequence = backlog.getpTSequence();
        // Update the BL SEQUENCE
        backlogSequence++;
        backlog.setpTSequence(backlogSequence);

        //Add sequence to Project Task
        projectTask.setProjectSequence(projectIdentifier + "-" + backlogSequence );
        projectTask.setProjectIdentifier(projectIdentifier);

        // INITIAL priority when priority null
        if(projectTask.getPriority() == 0)
        {
            projectTask.setPriority(3);
        }

        // INITIAL status when status is null
        if(projectTask.getStatus() == null || projectTask.getStatus().equals(""))
        {
            projectTask.setStatus("TO_DO");
        }

        if(projectTask.getPriority() == 0)
        {
            projectTask.setPriority(3);
        }

        return projectTaskRepository.save(projectTask);
    }

    public Iterable<ProjectTask> findBacklogById(String id)
    {
        return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
    }
}
