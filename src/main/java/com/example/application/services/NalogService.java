package com.example.application.services;

import com.example.application.entity.Nalog;
import com.example.application.repository.NalogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NalogService {
    private final NalogRepository repository;

    @Autowired
    public NalogService(NalogRepository repository) {
        this.repository = repository;
    }

    public Iterable<Nalog> findAll() {
        return repository.findAll();
    }

    public Optional<Nalog> findById(Long id) {
        return repository.findById(id);
    }

    public Nalog create(Nalog n) {
        return repository.save(n);
    }

    public Nalog update(Nalog n) {
        if(n != null &&  this.findById(n.getId()) != null) {
            return this.repository.save(n);
        }
        return null;
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
