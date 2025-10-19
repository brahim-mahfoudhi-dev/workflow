package com.workflow.workflowpro.dto;

import java.time.LocalDateTime;

public record CommentDTO(
        Long id,
        String content,
        LocalDateTime createdAt,
        Long taskId,
        Long authorId
) {}

