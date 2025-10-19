package com.workflow.workflowpro.service.project;

import com.workflow.workflowpro.dto.ProjectDTO;
import com.workflow.workflowpro.dto.TaskDTO;
import java.util.List;

public interface ProjectService {

    /**
     * Create a new project.
     */
    ProjectDTO create(ProjectDTO projectDTO);

    /**
     * Assign a task to a specific project.
     */
    ProjectDTO assignTask(Long projectId, TaskDTO taskDTO);

    /**
     * Retrieve all projects managed by a specific user (manager).
     */
    List<ProjectDTO> listByManager(Long managerId);

    /**
     * Retrieve a project by ID.
     */
    ProjectDTO getById(Long id);

    /**
     * Retrieve all projects.
     */
    List<ProjectDTO> getAll();

    /**
     * Delete a project.
     */
    void delete(Long id);

    /**
     * Update a project.
     */
    ProjectDTO update(Long id, ProjectDTO dto);
}
