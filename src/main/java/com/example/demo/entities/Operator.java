package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "operators")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Operator {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private String department;
    @Builder.Default
    @ManyToMany(mappedBy = "operators")
    private List<ApplicationRequest> requests = new ArrayList<>();
}
