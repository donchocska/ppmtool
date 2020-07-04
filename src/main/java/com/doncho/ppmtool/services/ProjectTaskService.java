package com.doncho.ppmtool.services;

import com.doncho.ppmtool.domain.Backlog;
import com.doncho.ppmtool.domain.Project;
import com.doncho.ppmtool.domain.ProjectTask;
import com.doncho.ppmtool.exceptions.ProjectNotFoundException;
import com.doncho.ppmtool.repositories.BacklogRepository;
import com.doncho.ppmtool.repositories.ProjectRepository;
import com.doncho.ppmtool.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectTaskService
{
    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectService projectService;


    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask, String usernName)
    {
        //Exceptions: Project not found
//        try
//        {
            // PTs to be added to a specific project, project!= null, BL exists
            Backlog backlog = projectService.findProjectByIdentifier(projectIdentifier, usernName).getBacklog();

            // set the BL to PT
            projectTask.setBacklog(backlog);

            // We want our project sequence to be like this IDPRO-1 IDPRO-2 ...100 101
            int backlogSequence = backlog.getpTSequence();
            // Update the BL SEQUENCE
            backlogSequence++;
            backlog.setpTSequence(backlogSequence);

            //Add sequence to Project Task
            projectTask.setProjectSequence(projectIdentifier + "-" + backlogSequence);
            projectTask.setProjectIdentifier(projectIdentifier);

            // INITIAL priority when priority null
            if (projectTask.getPriority() == null || projectTask.getPriority() == 0)
            {
                projectTask.setPriority(3);
            }

            // INITIAL status when status is null
            if (projectTask.getStatus() == null || projectTask.getStatus().equals(""))
            {
                projectTask.setStatus("TO_DO");
            }

            if (projectTask.getPriority() == null || projectTask.getPriority() == 0)
            {
                projectTask.setPriority(3);
            }
//        } catch (Exception e)
//        {
//            throw new ProjectNotFoundException("Project not Found");
//        }

        return projectTaskRepository.save(projectTask);
    }

    public Iterable<ProjectTask> findBacklogById(String id, String userName)
    {
//        Project project = projectRepository.findByProjectIdentifier(id);
//
//        if (null == project)
//        {
//            throw new ProjectNotFoundException("Project with " + id + " does not exist");
//        }

        projectService.findProjectByIdentifier(id, userName);

        return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
    }

    public ProjectTask findPTByProjectSequence(String backlog_id, String pt_id, String userName)
    {

        //make sure we are searching on an existing backlog
//        Backlog backlog = backlogRepository.findByProjectIdentifier(backlog_id);
//        if (null == backlog)
//        {
//            throw new ProjectNotFoundException("Project with ID: " + backlog_id + " does not exists");
//        }

        projectService.findProjectByIdentifier(backlog_id, userName);

        //make sure that our task exists
        ProjectTask projectTask = projectTaskRepository.findByProjectSequence(pt_id);
        if (null == projectTask)
        {
            throw new ProjectNotFoundException("Project task '" + pt_id + "' not found");
        }

        //make sure that the backlog/project id in the path corresponds to the right project
        if (!projectTask.getProjectIdentifier().equals(backlog_id))
        {
            throw new ProjectNotFoundException("Project task '" + pt_id + "' does not exists in project: " + backlog_id);
        }

        return projectTask;
    }

    public ProjectTask updateProjectSequence(ProjectTask updatedTask, String backlog_id, String pt_id, String userName)
    {
        ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id, userName);

        projectTask = updatedTask;

        return projectTaskRepository.save(projectTask);
    }

    public void deletePtByProjectSequence(String backlog_id, String pt_id, String userName)
    {
        ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id, userName);

        projectTaskRepository.delete(projectTask);
    }

    //Update project task

    // find existing project task

    // replace it with updated task

    // save update
}
