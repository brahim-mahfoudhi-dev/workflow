package com.workflow.workflowpro.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, nullable = false, length = 50)
    private String username;

    @Column(unique = true, nullable = false, length = 100)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "manager", cascade = CascadeType.ALL)
    private List<Project> managedProjects;

    @OneToMany(mappedBy = "assignedTo", cascade = CascadeType.ALL)
    private Set<Task> assignedTasks;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private Set<Comment> comments;

}
