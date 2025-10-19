package com.workflow.workflowpro.controller;

import com.workflow.workflowpro.dto.CommentDTO;
import com.workflow.workflowpro.service.comment.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    /**
     * Add a comment to a task
     */
    @PostMapping("/task/{taskId}")
    public ResponseEntity<CommentDTO> addComment(
            @PathVariable Long taskId,
            @RequestParam String content,
            Authentication authentication
    ) {
        CommentDTO createdComment = commentService.addCommentToTask(taskId, content, authentication);
        return ResponseEntity.ok(createdComment);
    }

    /**
     * List all comments for a task
     */
    @GetMapping("/task/{taskId}")
    public ResponseEntity<List<CommentDTO>> listComments(@PathVariable Long taskId) {
        List<CommentDTO> comments = commentService.listCommentsByTask(taskId);
        return ResponseEntity.ok(comments);
    }

    /**
     * Get a single comment by ID
     */
    @GetMapping("/{commentId}")
    public ResponseEntity<CommentDTO> getComment(@PathVariable Long commentId) {
        CommentDTO comment = commentService.getCommentById(commentId);
        return ResponseEntity.ok(comment);
    }

    /**
     * Delete a comment by ID
     */
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
}
