package com.example.application.repozitorijum;

import com.example.application.entitet.Firma;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FirmaRepository extends CrudRepository<Firma, Long> {
}
