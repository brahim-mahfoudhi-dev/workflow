package com.workflow.workflowpro.service.comment;

import com.workflow.workflowpro.dto.CommentDTO;
import com.workflow.workflowpro.entity.Comment;
import com.workflow.workflowpro.entity.Task;
import com.workflow.workflowpro.entity.User;
import com.workflow.workflowpro.repository.CommentRepository;
import com.workflow.workflowpro.repository.TaskRepository;
import com.workflow.workflowpro.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Override
    public CommentDTO addCommentToTask(Long taskId, String content, Authentication authentication) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        String username = authentication.getName();
        User author = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Comment comment = Comment.builder()
                .content(content)
                .createdAt(LocalDateTime.now())
                .task(task)
                .author(author)
                .build();

        Comment savedComment = commentRepository.save(comment);
        return mapToDTO(savedComment);
    }


    @Override
    public List<CommentDTO> listCommentsByTask(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        return commentRepository.findByTask(task).stream()
                .map(this::mapToDTO)   // map each Comment to CommentDTO
                .collect(Collectors.toList());
    }


    @Override
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        commentRepository.delete(comment);
    }

    @Override
    public CommentDTO getCommentById(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        return mapToDTO(comment);
    }

    private CommentDTO mapToDTO(Comment comment) {
        return new CommentDTO(
                comment.getId(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getTask().getId(),
                comment.getAuthor() != null ? comment.getAuthor().getId() : null
        );
    }
}
