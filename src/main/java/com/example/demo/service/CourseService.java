package com.example.demo.service;

import com.example.demo.entities.ApplicationRequest;
import com.example.demo.entities.Course;
import com.example.demo.repository.ApplicationRequestRepository;
import com.example.demo.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    private final CourseRepository repo;
    private final ApplicationRequestRepository requestRepo;
    public CourseService(CourseRepository repo, ApplicationRequestRepository requestRepo) {
        this.repo = repo;
        this.requestRepo=requestRepo;}

    public List<Course> findAll() { return repo.findAll(); }
    public Course findById(Long id) {
        return repo.findById(id).orElse(null);
    }

    public Course addCourse(Course c) { return repo.save(c); }
    public void deleteById(Long id) {
        Course course = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        List<ApplicationRequest> requests = requestRepo.findAllByCourseId(id);
        for (ApplicationRequest req : requests) {
            req.getOperators().clear(); // очищаем связи ManyToMany
            requestRepo.delete(req);    // удаляем заявку
        }

        repo.delete(course);
    }
}
