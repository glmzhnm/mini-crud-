package com.example.demo.service;

import com.example.demo.entities.ApplicationRequest;
import com.example.demo.entities.Course;
import com.example.demo.entities.Operator;
import com.example.demo.repository.ApplicationRequestRepository;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.OperatorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationRequestService {
    private final ApplicationRequestRepository repo;
    private final CourseRepository courseRepo;
    private final OperatorRepository operatorRepo;
    public ApplicationRequestService(ApplicationRequestRepository repo, CourseRepository courseRepo, OperatorRepository operatorRepo) { this.repo = repo;
        this.courseRepo = courseRepo;
        this.operatorRepo = operatorRepo;
    }
    public List<ApplicationRequest> findAll() { return repo.findAll(); }
    public ApplicationRequest findById(Long id) { return repo.findById(id).orElse(null); }
    public ApplicationRequest add(ApplicationRequest r) { return repo.save(r); }
    public void delete(Long id) {  ApplicationRequest existing = repo.findById(id)
            .orElseThrow(() -> new RuntimeException("Request not found"));
        existing.getOperators().clear();
        repo.delete(existing); }
    public void update(ApplicationRequest request, Long courseId, List<Long> operatorIds) {
        ApplicationRequest existing = repo.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("Request not found"));

        existing.setUserName(request.getUserName());
        existing.setPhone(request.getPhone());
        existing.setCommentary(request.getCommentary());
        existing.setHandled(request.isHandled());

        Course course = courseRepo.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        existing.setCourse(course);

        List<Operator> existingOperators = existing.getOperators();
        existingOperators.clear();

        if (operatorIds != null && !operatorIds.isEmpty()) {
            List<Operator> operators = operatorRepo.findAllById(operatorIds);
            existingOperators.addAll(operators);
        }

        repo.save(existing);
    }

}
