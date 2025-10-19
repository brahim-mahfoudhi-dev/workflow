package com.workflow.workflowpro.controller;

import com.workflow.workflowpro.dto.TaskDTO;
import com.workflow.workflowpro.dto.TaskStatusUpdateDTO;
import com.workflow.workflowpro.service.task.TaskService;
import com.workflow.workflowpro.service.task.TaskServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskServiceImpl taskService;

    /**
     * Create a new task
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO taskDTO) {
        TaskDTO created = taskService.create(taskDTO);
        return ResponseEntity.ok(created);
    }

    /**
     * Update the status of a specific task
     */
    @PutMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public ResponseEntity<TaskDTO> updateStatus(@PathVariable long id,
                                                @RequestBody TaskStatusUpdateDTO statusDTO) {
        TaskDTO updated = taskService.updateStatus(id, statusDTO.status());
        return ResponseEntity.ok(updated);
    }

    /**
     * Get all tasks assigned to a specific user
     */
    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    public ResponseEntity<List<TaskDTO>> getTasksByUser(@PathVariable long userId) {
        List<TaskDTO> tasks = taskService.getByUser(userId);
        return ResponseEntity.ok(tasks);
    }

    /**
     * Get all tasks
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public ResponseEntity<List<TaskDTO>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAll());
    }

    /**
     * Delete a specific task by ID
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public ResponseEntity<Void> deleteTask(@PathVariable long id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
