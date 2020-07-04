package com.doncho.ppmtool.web;

import com.doncho.ppmtool.domain.ProjectTask;
import com.doncho.ppmtool.domain.User;
import com.doncho.ppmtool.services.MapValidationErrorService;
import com.doncho.ppmtool.services.ProjectTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/backlog")
@CrossOrigin
public class BacklogController
{
    @Autowired
    private ProjectTaskService projectTaskService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @PostMapping("/{backlog_id}")
    public ResponseEntity<?> addProjectTaskToBacklog(@Valid @RequestBody ProjectTask projectTask,
                                                     BindingResult result,
                                                     @PathVariable String backlog_id,
                                                     Principal principal)
    {

        ResponseEntity <?> errorMap = mapValidationErrorService.mapValidationService(result);
        if(errorMap != null)
        {
            return errorMap;
        }

        ProjectTask projectTask1 = projectTaskService.addProjectTask(backlog_id, projectTask, ((User)((UsernamePasswordAuthenticationToken)principal).getPrincipal()).getUserName());

        return new ResponseEntity<ProjectTask>(projectTask1, HttpStatus.CREATED);
    }


    @GetMapping("/{backlog_id}")
    public Iterable<ProjectTask> getProjectBacklog(@PathVariable String backlog_id, Principal principal)
    {
        return projectTaskService.findBacklogById(backlog_id, ((User)((UsernamePasswordAuthenticationToken)principal).getPrincipal()).getUserName());
    }

    @GetMapping("/{backlog_id}/{pt_id}")
    public ResponseEntity<?> getProjectTask(@PathVariable String backlog_id, @PathVariable String pt_id, Principal principal)
    {
        ProjectTask projectTask = projectTaskService.findPTByProjectSequence(backlog_id, pt_id, ((User)((UsernamePasswordAuthenticationToken)principal).getPrincipal()).getUserName());
        return new ResponseEntity<ProjectTask>(projectTask, HttpStatus.OK);
    }

    @PatchMapping("/{backlog_id}/{pt_id}")
    public ResponseEntity<?> updateProjectTask(@Valid @RequestBody ProjectTask projectTask,
                                               BindingResult result, @PathVariable String backlog_id,
                                               @PathVariable String pt_id,
                                               Principal principal)
    {
        ResponseEntity <?> errorMap = mapValidationErrorService.mapValidationService(result);
        if(errorMap != null)
        {
            return errorMap;
        }

        ProjectTask updatedTask = projectTaskService.updateProjectSequence(projectTask, backlog_id, pt_id, ((User)((UsernamePasswordAuthenticationToken)principal).getPrincipal()).getUserName());
        return new ResponseEntity<ProjectTask>(updatedTask, HttpStatus.OK);
    }

    @DeleteMapping("/{backlog_id}/{pt_id}")
    public ResponseEntity<?> deleteProjectTask(@PathVariable String backlog_id, @PathVariable String pt_id, Principal principal)
    {
        projectTaskService.deletePtByProjectSequence(backlog_id, pt_id, ((User)((UsernamePasswordAuthenticationToken)principal).getPrincipal()).getUserName());

        return new ResponseEntity<String>("Project task '"+ pt_id + "' was deleted!" , HttpStatus.OK);
    }
}
