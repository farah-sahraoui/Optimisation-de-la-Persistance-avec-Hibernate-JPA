package com.example.model;

import javax.persistence.*;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "auteurs")
@Cacheable
public class Auteur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    @Column(unique = true)
    private String email;

    @OneToMany(mappedBy = "auteur", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Livre> livres = new ArrayList<>();


    public Auteur(){}

    public Auteur(String nom, String prenom){
        this.nom = nom;
        this.prenom = prenom;
    }

    public Auteur(String nom, String prenom, String email){
        this(nom, prenom);
        this.email = email;
    }

    public void ajouterLivre(Livre livre){
        if(livre != null){
            livres.add(livre);
            livre.setAuteur(this);
        }
    }

    public void supprimerLivre(Livre livre){
        if(livres.contains(livre)){
            livres.remove(livre);
            livre.setAuteur(null);
        }
    }

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getNom(){
        return nom;
    }

    public void setNom(String nom){
        this.nom = nom;
    }

    public String getPrenom(){
        return prenom;
    }

    public void setPrenom(String prenom){
        this.prenom = prenom;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public List<Livre> getLivres(){
        return livres;
    }

    public void setLivres(List<Livre> livres){
        this.livres = livres;
    }

    @Override
    public String toString(){
        return "Auteur : " + nom + " " + prenom + " (" + email + ")";
    }
}