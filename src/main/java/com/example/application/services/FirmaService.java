package com.example.application.services;

import com.example.application.entity.Firma;
import com.example.application.repository.FirmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FirmaService {
    private final FirmaRepository repository;

    @Autowired
    public FirmaService(FirmaRepository repository) {
        this.repository = repository;
    }

    public Iterable<Firma> findAll() {
        return repository.findAll();
    }

    public Optional<Firma> findById(Long id) {
        return repository.findById(id);
    }


    public Firma findByPib(String pib) {
        return repository.findByPib(pib);
    }

    public Firma create(Firma n) {
        return repository.save(n);
    }

    public Firma update(Firma n) {
        if(n != null &&  this.findById(n.getId()) != null) {
            return this.repository.save(n);
        }
        return null;
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }


//    public Page<Firma> findAll(Pageable pageable) {
//        return repository.findAll(pageable);
//    }


}
