package com.example.application.entitet;

import jakarta.persistence.*;

@Entity
public class KorisnikHasPravo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Korisnik korisnik;
    @ManyToOne
    private PravoPristupa pravoPristupa;


    public KorisnikHasPravo() {
    }
    public KorisnikHasPravo(Long id, Korisnik korisnik, PravoPristupa pravoPristupa) {
        super();
        this.id = id;
        this.korisnik = korisnik;
        this.pravoPristupa = pravoPristupa;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Korisnik getKorisnik() {
        return korisnik;
    }
    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }
    public PravoPristupa getPravoPistupa() {
        return pravoPristupa;
    }
    public void setPravoPistupa(PravoPristupa pravoPistupa) {
        this.pravoPristupa = pravoPistupa;
    }


}
