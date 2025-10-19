package com.workflow.workflowpro.service.task;

import com.workflow.workflowpro.dto.TaskDTO;
import com.workflow.workflowpro.entity.TaskStatus;

import java.util.List;

public interface TaskService {

    TaskDTO create(TaskDTO taskDTO);

    TaskDTO updateStatus(long id, TaskStatus status);

    List<TaskDTO> getByUser(long userId);

    List<TaskDTO> getAll();

    void delete(long id);
}
