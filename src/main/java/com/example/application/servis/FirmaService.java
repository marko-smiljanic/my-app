package com.example.application.servis;

import com.example.application.entitet.Firma;
import com.example.application.repozitorijum.FirmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FirmaService {
    @Autowired
    private FirmaRepository firmaRepository;

    public Iterable<Firma> findAll() {
        return firmaRepository.findAll();
    }

    public Optional<Firma> findById(Long id) {
        return firmaRepository.findById(id);
    }

    public Firma save(Firma firma) {
        return firmaRepository.save(firma);
    }

    public void deleteById(Long id) {
        firmaRepository.deleteById(id);
    }
}
