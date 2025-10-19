package com.workflow.workflowpro.repository;

import com.workflow.workflowpro.entity.Comment;
import com.workflow.workflowpro.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByTask(Task task);
}
