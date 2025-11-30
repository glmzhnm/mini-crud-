package com.example.demo.repository;

import com.example.demo.entities.ApplicationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRequestRepository extends JpaRepository<ApplicationRequest, Long> {
    List<ApplicationRequest> findByHandled(boolean handled);
    List<ApplicationRequest> findAllByCourseId(Long courseId);
}
