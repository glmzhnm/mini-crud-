package com.example.demo.service;

import com.example.demo.entities.ApplicationRequest;
import com.example.demo.entities.Operator;
import com.example.demo.repository.ApplicationRequestRepository;
import com.example.demo.repository.OperatorRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OperatorService {
    private final OperatorRepository repo;
    private final ApplicationRequestRepository requestRepo;
    public OperatorService(OperatorRepository repo, ApplicationRequestRepository requestRepo) {this.repo = repo;
    this.requestRepo = requestRepo; }
    public List<Operator> findAll() { return repo.findAll(); }
    public Optional<Operator> findById(Long id) { return repo.findById(id); }
    public Operator addOperator(Operator o) { return repo.save(o); }
    public void deleteById(Long id) { Operator operator = repo.findById(id)
            .orElseThrow(() -> new RuntimeException("Operator not found"));

        // удаляем оператора из всех заявок
        List<ApplicationRequest> requests = requestRepo.findAll();
        for (ApplicationRequest req : requests) {
            if (req.getOperators().contains(operator)) {
                req.getOperators().remove(operator);
                requestRepo.save(req);
            }
        }
        repo.delete(operator);
    }

    public List<Operator> findAllByIds(List<Long> ids) {
        return repo.findAllById(ids);
    }

    public ApplicationRequest assignOperator(Long operatorId, Long requestId) {
        Operator op = repo.findById(operatorId).orElse(null);
        ApplicationRequest req = requestRepo.findById(requestId).orElse(null);
        if (op == null || req == null) return null;
        req.getOperators().add(op);
        req.setHandled(true);
        return requestRepo.save(req);
    }
}
