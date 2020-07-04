package com.doncho.ppmtool.web;

import com.doncho.ppmtool.domain.Project;
import com.doncho.ppmtool.domain.User;
import com.doncho.ppmtool.services.MapValidationErrorService;
import com.doncho.ppmtool.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/project")
@CrossOrigin
public class ProjectController
{
    @Autowired
    private ProjectService projectService;

    @Autowired
    private MapValidationErrorService mapValService;

    @PostMapping("")
    public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult result, Principal principal)
    {
        ResponseEntity<?> responseE = mapValService.mapValidationService(result);
        if (null != responseE) //Negative scenario
        {
            return responseE;
        }

        Project project1 = projectService.saveOrUpdateProject(project, ((User)((UsernamePasswordAuthenticationToken)principal).getPrincipal()).getUserName());
        return new ResponseEntity<Project>(project, HttpStatus.CREATED);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<?> getProjectById(@PathVariable String projectId, Principal principal)
    {
        Project project = projectService.findProjectByIdentifier(projectId, ((User)((UsernamePasswordAuthenticationToken)principal).getPrincipal()).getUserName());

        return new ResponseEntity<Project>(project, HttpStatus.OK);
    }

    @GetMapping("/all")
    public Iterable<Project> getAllProjects(Principal principal)
    {
        return projectService.findAllProjects(((User)((UsernamePasswordAuthenticationToken)principal).getPrincipal()).getUserName());
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable String projectId, Principal principal)
    {
        projectService.deleteProjectByIdentifier(projectId, ((User)((UsernamePasswordAuthenticationToken)principal).getPrincipal()).getUserName());

        return new ResponseEntity<String>("Project with ID '"+ projectId + "' was deleted!" , HttpStatus.OK);
    }
}
