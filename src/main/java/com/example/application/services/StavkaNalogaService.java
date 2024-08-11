package com.example.application.services;

import com.example.application.entity.StavkaNaloga;
import com.example.application.repository.StavkaNalogaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StavkaNalogaService {
    private final StavkaNalogaRepository repository;

    @Autowired
    public StavkaNalogaService(StavkaNalogaRepository repository) {
        this.repository = repository;
    }

    public Iterable<StavkaNaloga> findAll() {
        return repository.findAll();
    }

    public Optional<StavkaNaloga> findById(Long id) {
        return repository.findById(id);
    }

    public StavkaNaloga create(StavkaNaloga n) {
        return repository.save(n);
    }

    public StavkaNaloga update(StavkaNaloga n) {
        if(n != null &&  this.findById(n.getId()) != null) {
            return this.repository.save(n);
        }
        return null;
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
