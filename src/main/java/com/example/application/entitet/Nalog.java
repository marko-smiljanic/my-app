package com.example.application.entitet;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Nalog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime vremeKreiranja;

    @ManyToOne
    private Korisnik korisnik;

    @ManyToOne
    //@JoinColumn(name = "firma_id", nullable = false)
    private Firma firma;

    @OneToMany(mappedBy = "nalog")
    private Set<StavkaNaloga> stavkeNaloga = new HashSet<StavkaNaloga>();



    public Nalog() {
    }

    public Nalog(Long id, LocalDateTime vremeKreiranja, Korisnik korisnik, Firma firma) {
        this.id = id;
        this.vremeKreiranja = vremeKreiranja;
        this.korisnik = korisnik;
        this.firma = firma;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<StavkaNaloga> getStavkeNaloga() {
        return stavkeNaloga;
    }

    public void setStavkeNaloga(Set<StavkaNaloga> stavkeNaloga) {
        this.stavkeNaloga = stavkeNaloga;
    }

    public LocalDateTime getVremeKreiranja() {
        return vremeKreiranja;
    }

    public void setVremeKreiranja(LocalDateTime vremeKreiranja) {
        this.vremeKreiranja = vremeKreiranja;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    public Firma getFirma() {
        return firma;
    }

    public void setFirma(Firma firma) {
        this.firma = firma;
    }


}
