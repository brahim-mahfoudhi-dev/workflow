package com.workflow.workflowpro.repository;

import com.workflow.workflowpro.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAllByAssignedToId(long userId);
}
