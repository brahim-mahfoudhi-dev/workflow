package com.workflow.workflowpro.service.task;

import com.workflow.workflowpro.dto.TaskDTO;
import com.workflow.workflowpro.entity.Project;
import com.workflow.workflowpro.entity.Task;
import com.workflow.workflowpro.entity.TaskStatus;
import com.workflow.workflowpro.entity.User;
import com.workflow.workflowpro.repository.ProjectRepository;
import com.workflow.workflowpro.repository.TaskRepository;
import com.workflow.workflowpro.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    @Override
    public TaskDTO create(TaskDTO taskDTO) {
        Project project = projectRepository.findById(taskDTO.projectId())
                .orElseThrow(() -> new RuntimeException("Project not found"));

        User user = userRepository.findById(taskDTO.assignedToId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Task task = Task.builder()
                .title(taskDTO.title())
                .description(taskDTO.description())
                .status(taskDTO.status())
                .deadline(taskDTO.deadline().atStartOfDay())
                .project(project)
                .assignedTo(user)
                .build();

        Task saved = taskRepository.save(task);
        return mapToDTO(saved);
    }

    @Override
    public TaskDTO updateStatus(long id, TaskStatus status) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        task.setStatus(status);
        Task updated = taskRepository.save(task);
        return mapToDTO(updated);
    }

    @Override
    public List<TaskDTO> getByUser(long userId) {
        List<Task> tasks = taskRepository.findAllByAssignedToId(userId);
        return tasks.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> getAll() {
        return taskRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(long id) {
        if (!taskRepository.existsById(id)) {
            throw new RuntimeException("Task not found");
        }
        taskRepository.deleteById(id);
    }

    private TaskDTO mapToDTO(Task task) {
        return new TaskDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getDeadline().toLocalDate(),
                task.getProject().getId(),
                task.getAssignedTo().getId()
        );
    }
}
