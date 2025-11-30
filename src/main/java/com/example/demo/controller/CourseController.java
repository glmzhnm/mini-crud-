package com.example.demo.controller;

import com.example.demo.entities.Course;
import com.example.demo.service.CourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
    private final CourseService courseService;
    public CourseController(CourseService courseService) { this.courseService = courseService; }
    @GetMapping()
    public List<Course> findAll(){
        return courseService.findAll();
    }
    @GetMapping("/{id}")
    public Course findById(@PathVariable Long id) {
        return courseService.findById(id);
    }
    @PostMapping
    public ResponseEntity<Course> addCourse(@RequestBody Course course) {
        Course savedCourse = courseService.addCourse(course);
        return ResponseEntity.status(201).body(savedCourse);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id){
        courseService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
