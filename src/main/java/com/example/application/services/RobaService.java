package com.example.application.services;

import com.example.application.entity.Roba;
import com.example.application.repository.RobaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RobaService {
    private final RobaRepository repository;

    @Autowired
    public RobaService(RobaRepository repository) {
        this.repository = repository;
    }

    public Iterable<Roba> findAll() {
        return repository.findAll();
    }

    public Optional<Roba> findById(Long id) {
        return repository.findById(id);
    }

    public Roba create(Roba n) {
        return repository.save(n);
    }

    public Roba update(Roba n) {
        if(n != null &&  this.findById(n.getId()) != null) {
            return this.repository.save(n);
        }
        return null;
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
