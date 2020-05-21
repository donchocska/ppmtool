package com.doncho.ppmtool.web;

import com.doncho.ppmtool.domain.Project;
import com.doncho.ppmtool.services.MapValidationErrorService;
import com.doncho.ppmtool.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult result)
    {
        ResponseEntity<?> responseE = mapValService.mapValidationService(result);
        if (null != responseE) //Negative scenario
        {
            return responseE;
        }

        Project project1 = projectService.saveOrUpdateProject(project);
        return new ResponseEntity<Project>(project, HttpStatus.CREATED);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<?> getProjectById(@PathVariable String projectId)
    {
        Project project = projectService.findProjectByIddentifier(projectId);

        return new ResponseEntity<Project>(project, HttpStatus.OK);
    }

    @GetMapping("/all")
    public Iterable<Project> getAllProjects()
    {
        return projectService.findAllProjects();
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable String projectId)
    {
        projectService.deleteProjectByIdentifier(projectId);

        return new ResponseEntity<String>("Project with ID '"+ projectId + "' was deleted!" , HttpStatus.OK);
    }
}
