package com.workflow.workflowpro.controller;

import com.workflow.workflowpro.dto.ProjectDTO;
import com.workflow.workflowpro.dto.TaskDTO;
import com.workflow.workflowpro.service.project.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    /**
     * Create a new project
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public ResponseEntity<ProjectDTO> createProject(@RequestBody ProjectDTO projectDTO) {
        ProjectDTO created = projectService.create(projectDTO);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public ResponseEntity<ProjectDTO> updateProject(@PathVariable Long id, @RequestBody ProjectDTO dto) {
        ProjectDTO updated = projectService.update(id, dto);
        return ResponseEntity.ok(updated);
    }


    /**
     * Assign a new task to a specific project
     */
    @PostMapping("/{projectId}/tasks")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public ResponseEntity<ProjectDTO> assignTaskToProject(@PathVariable Long projectId, @RequestBody TaskDTO taskDTO) {
        ProjectDTO updated = projectService.assignTask(projectId, taskDTO);
        return ResponseEntity.ok(updated);
    }

    /**
     * Get all projects
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    public ResponseEntity<List<ProjectDTO>> getAllProjects() {
        return ResponseEntity.ok(projectService.getAll());
    }

    /**
     * Get a specific project by ID
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    public ResponseEntity<ProjectDTO> getProjectById(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.getById(id));
    }

    /**
     * Get all projects managed by a specific manager
     */
    @GetMapping("/manager/{managerId}")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public ResponseEntity<List<ProjectDTO>> getProjectsByManager(@PathVariable Long managerId) {
        return ResponseEntity.ok(projectService.listByManager(managerId));
    }

    /**
     * Delete a project by ID
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
