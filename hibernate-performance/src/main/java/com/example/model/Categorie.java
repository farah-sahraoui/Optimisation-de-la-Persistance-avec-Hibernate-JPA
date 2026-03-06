package com.example.model;

import javax.persistence.*;
import java.util.Set;
import java.util.HashSet;

@Entity
@Table(name = "categories")
@Cacheable
public class Categorie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false , unique = true)
    private String nom;

    @Column(length = 500)
    private String description;

    @ManyToMany(mappedBy = "categories")
    private Set<Livre> livres = new HashSet<>();


    public Categorie(){}

    public Categorie(String nom){
        this.nom = nom;
    }

    public Categorie(String nom , String description){
        this.nom = nom;
        this.description = description;
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

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public Set<Livre> getLivres(){
        return livres;
    }

    public void setLivres(Set<Livre> livres){
        this.livres = livres;
    }

    @Override
    public String toString(){
        return "Categorie [ nom=" + nom + " ]";
    }
}