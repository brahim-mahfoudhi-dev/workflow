package com.workflow.workflowpro.service.comment;

import com.workflow.workflowpro.dto.CommentDTO;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface CommentService {

    /**
     * Adds a comment to a specific task by the logged-in user.
     *
     * @param taskId         ID of the task
     * @param content        Text content of the comment
     * @param authentication Spring Security authentication object
     * @return the created CommentDTO
     */
    CommentDTO addCommentToTask(Long taskId, String content, Authentication authentication);

    /**
     * Lists all comments for a specific task.
     *
     * @param taskId ID of the task
     * @return list of comments for the task
     */
    List<CommentDTO> listCommentsByTask(Long taskId);

    /**
     * Retrieves a comment by its ID.
     *
     * @param commentId ID of the comment
     * @return CommentDTO
     */
    CommentDTO getCommentById(Long commentId);

    /**
     * Deletes a comment by its ID.
     *
     * @param commentId ID of the comment
     */
    void deleteComment(Long commentId);
}
