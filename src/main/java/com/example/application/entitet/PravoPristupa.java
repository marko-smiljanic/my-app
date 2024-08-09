package com.example.application.entitet;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class PravoPristupa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String naziv;

    @OneToMany(mappedBy = "pravoPristupa")
    private Set<KorisnikHasPravo> korisnikHasPravo = new HashSet<KorisnikHasPravo>();


    public PravoPristupa() {
    }
    public PravoPristupa(Long id, String naziv) {
        super();
        this.id = id;
        this.naziv = naziv;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Set<KorisnikHasPravo> getKorisnikHasPravo() {
        return korisnikHasPravo;
    }

    public void setKorisnikHasPravo(Set<KorisnikHasPravo> korisnikHasPravo) {
        this.korisnikHasPravo = korisnikHasPravo;
    }
}
