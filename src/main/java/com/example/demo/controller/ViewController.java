package com.example.demo.controller;

import com.example.demo.service.*;
import com.example.demo.entities.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class ViewController {
    private final ApplicationRequestService requestService;
    private final CourseService courseService;
    private final OperatorService operatorService;

    public ViewController(ApplicationRequestService requestService, CourseService courseService, OperatorService operatorService) {
        this.requestService = requestService;
        this.courseService = courseService;
        this.operatorService = operatorService;
    }

    @GetMapping("/")
    public String index() { return "index"; }

    @GetMapping("/requests")
    public String requestsPage(Model model) {
        model.addAttribute("requests", requestService.findAll());
        model.addAttribute("courses", courseService.findAll());
        model.addAttribute("operators", operatorService.findAll());
        return "requests";
    }


    @GetMapping("/courses")
    public String courses(Model model) {
        model.addAttribute("courses", courseService.findAll());
        return "courses";
    }

    @GetMapping("/operators")
    public String operators(Model model) {
        model.addAttribute("operators", operatorService.findAll());
        return "operators";
    }
    @PostMapping("/operators/add")
    public String addOperator(@RequestParam String name,
                              @RequestParam String surname,
                              @RequestParam String department,
                              RedirectAttributes redirectAttributes) {

        Operator operator = new Operator();
        operator.setName(name);
        operator.setSurname(surname);
        operator.setDepartment(department);

        operatorService.addOperator(operator);
        redirectAttributes.addFlashAttribute("message", "Operator added successfully!");
        return "redirect:/operators";
    }
    @PostMapping("/requests/add")
    public String addRequest(@RequestParam String userName,
                             @RequestParam String phone,
                             @RequestParam String commentary,
                             @RequestParam Long courseId,
                             @RequestParam(required = false) List<Long> operatorIds) {
        Course course = courseService.findById(courseId);
        ApplicationRequest request = new ApplicationRequest();
        request.setUserName(userName);
        request.setPhone(phone);
        request.setCommentary(commentary);
        request.setHandled(false);
        request.setCourse(course);
        if (operatorIds != null) {
            List<Operator> operators = new ArrayList<>(operatorService.findAllByIds(operatorIds));
            request.setOperators(operators);
        }

        requestService.add(request);
        return "redirect:/requests";
    }
    @PostMapping("/courses/add")
    public String addCourse(@RequestParam String title,
                            @RequestParam String description,
                            @RequestParam int price) {
        Course course = new Course();
        course.setTitle(title);
        course.setDescription(description);
        course.setPrice(price);

        courseService.addCourse(course);
        return "redirect:/courses";
    }
    @GetMapping("/requests/edit/{id}")
    public String editRequest(@PathVariable Long id, Model model) {
        ApplicationRequest request = requestService.findById(id);

        if (request == null) {
            return "redirect:/requests";
        }

        model.addAttribute("request", request);
        model.addAttribute("courses", courseService.findAll());
        model.addAttribute("operators", operatorService.findAll());
        return "edit-request";
    }
    @PostMapping("/requests/update")
    public String updateRequest(@RequestParam Long id,
                                @RequestParam String userName,
                                @RequestParam String phone,
                                @RequestParam String commentary,
                                @RequestParam Long courseId,
                                @RequestParam(required = false) List<Long> operatorIds,
                                @RequestParam(required = false, defaultValue = "false") boolean handled) {
        ApplicationRequest request = new ApplicationRequest();
        request.setId(id);
        request.setUserName(userName);
        request.setPhone(phone);
        request.setCommentary(commentary);
        request.setHandled(handled);
        requestService.update(request, courseId, operatorIds);
        return "redirect:/requests";
    }
    @GetMapping("/requests/delete/{id}")
    public String deleteRequest(@PathVariable Long id) {
        requestService.delete(id);
        return "redirect:/requests";
    }
    @GetMapping("/courses/delete/{id}")
    public String delete(@PathVariable Long id) {
        courseService.deleteById(id);
        return "redirect:/courses";
    }
    @GetMapping("/operators/delete/{id}")
    public String deleteOperator(@PathVariable Long id) {
        operatorService.deleteById(id);
        return "redirect:/operators";
    }
}