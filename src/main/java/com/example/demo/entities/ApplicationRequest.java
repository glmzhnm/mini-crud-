package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "application_requests")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationRequest {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;
    private String courseName;
    @Column(length = 2000)
    private String commentary;
    private String phone;
    @Builder.Default
    private boolean handled = false;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id")
    private Course course;
    @Builder.Default
    @ManyToMany
    @JoinTable(name = "request_operators",
        joinColumns = @JoinColumn(name = "request_id"),
        inverseJoinColumns = @JoinColumn(name = "operator_id"))
    private List<Operator> operators = new ArrayList<>();
}
