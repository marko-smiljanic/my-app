package com.example.application.entitet;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Korisnik {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String ime;

    @Column(nullable = false)
    private String prezime;

    @Column(nullable = false)
    private String telefon;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, length = 255)
    private String pass;


    @OneToMany(mappedBy = "korisnik")
    private Set<Nalog> nalozi = new HashSet<Nalog>();

    @OneToMany(mappedBy = "korisnik")
    private Set<KorisnikHasPravo> korisnikHasPravo = new HashSet<>();


    //za sada necu ovako
//    @Column(nullable = false)
//    @Enumerated(EnumType.STRING)
//    private Rola rola;

    public Korisnik() {
    }

    public Korisnik(Long id, String ime, String prezime, String telefon, String username, String pass) {
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
        this.telefon = telefon;
        this.username = username;
        this.pass = pass;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Set<Nalog> getNalozi() {
        return nalozi;
    }

    public void setNalozi(Set<Nalog> nalozi) {
        this.nalozi = nalozi;
    }

    public Set<KorisnikHasPravo> getKorisnikHasPravo() {
        return korisnikHasPravo;
    }

    public void setKorisnikHasPravo(Set<KorisnikHasPravo> korisnikHasPravo) {
        this.korisnikHasPravo = korisnikHasPravo;
    }
}
