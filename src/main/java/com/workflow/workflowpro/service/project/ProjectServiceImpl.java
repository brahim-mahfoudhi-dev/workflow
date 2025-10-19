package com.workflow.workflowpro.service.project;

import com.workflow.workflowpro.dto.ProjectDTO;
import com.workflow.workflowpro.dto.TaskDTO;
import com.workflow.workflowpro.entity.Project;
import com.workflow.workflowpro.entity.Task;
import com.workflow.workflowpro.repository.ProjectRepository;
import com.workflow.workflowpro.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;

    @Override
    @Transactional
    public ProjectDTO create(ProjectDTO projectDTO) {
        if (projectRepository.existsByName(projectDTO.name())) {
            throw new RuntimeException("Project already exists");
        }

        Project project = Project.builder()
                .name(projectDTO.name())
                .description(projectDTO.description())
                .startDate(projectDTO.startDate())
                .endDate(projectDTO.endDate())
                .build();

        projectRepository.save(project);
        return mapToDTO(project);
    }

    @Override
    @Transactional
    public ProjectDTO assignTask(Long projectId, TaskDTO taskDTO) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        Task task = Task.builder()
                .title(taskDTO.title())
                .description(taskDTO.description())
                .deadline(taskDTO.deadline().atStartOfDay())
                .status(taskDTO.status())
                .project(project)
                .build();

        taskRepository.save(task);
        return mapToDTO(project);
    }

    @Override
    public List<ProjectDTO> listByManager(Long managerId) {
        return projectRepository.findByManagerId(managerId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProjectDTO getById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        return mapToDTO(project);
    }

    @Override
    public List<ProjectDTO> getAll() {
        return projectRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        if (!projectRepository.existsById(id)) {
            throw new RuntimeException("Project not found");
        }
        projectRepository.deleteById(id);
    }

    @Override
    @Transactional
    public ProjectDTO update(Long id, ProjectDTO dto) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        project.setName(dto.name());
        project.setDescription(dto.description());
        project.setStartDate(dto.startDate());
        project.setEndDate(dto.endDate());
        // Optional: update manager if included in DTO
        // project.setManager(...);

        projectRepository.save(project);
        return mapToDTO(project);
    }


    private ProjectDTO mapToDTO(Project project) {
        Long managerId = project.getManager() != null ? project.getManager().getId() : null;

        return new ProjectDTO(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getStartDate(),
                project.getEndDate(),
                managerId
        );
    }
}
