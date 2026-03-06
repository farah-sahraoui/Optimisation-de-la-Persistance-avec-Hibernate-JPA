package com.example.model;

import javax.persistence.*;
import java.util.Set;
import java.util.HashSet;

@Entity
@Table(name = "livres")
@Cacheable
@NamedEntityGraph(
        name = "Livre.auteurCategories",
        attributeNodes = {
                @NamedAttributeNode("auteur"),
                @NamedAttributeNode("categories")
        }
)
public class Livre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titre;

    @Column(name = "annee_publication")
    private Integer anneePublication;

    @Column(unique = true)
    private String isbn;

    @Column(length = 2000)
    private String resume;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auteur_id")
    private Auteur auteur;

    @ManyToMany(cascade = {CascadeType.PERSIST , CascadeType.MERGE})
    @JoinTable(
            name = "livre_categorie",
            joinColumns = @JoinColumn(name = "livre_id"),
            inverseJoinColumns = @JoinColumn(name = "categorie_id")
    )
    private Set<Categorie> categories = new HashSet<>();


    public Livre(){}

    public Livre(String titre){
        this.titre = titre;
    }

    public Livre(String titre , Integer anneePublication , String isbn){
        this.titre = titre;
        this.anneePublication = anneePublication;
        this.isbn = isbn;
    }

    public void ajouterCategorie(Categorie categorie){
        categories.add(categorie);
        categorie.getLivres().add(this);
    }

    public void retirerCategorie(Categorie categorie){
        categories.remove(categorie);
        categorie.getLivres().remove(this);
    }

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getTitre(){
        return titre;
    }

    public void setTitre(String titre){
        this.titre = titre;
    }

    public Integer getAnneePublication(){
        return anneePublication;
    }

    public void setAnneePublication(Integer anneePublication){
        this.anneePublication = anneePublication;
    }

    public String getIsbn(){
        return isbn;
    }

    public void setIsbn(String isbn){
        this.isbn = isbn;
    }

    public String getResume(){
        return resume;
    }

    public void setResume(String resume){
        this.resume = resume;
    }

    public Auteur getAuteur(){
        return auteur;
    }

    public void setAuteur(Auteur auteur){
        this.auteur = auteur;
    }

    public Set<Categorie> getCategories(){
        return categories;
    }

    public void setCategories(Set<Categorie> categories){
        this.categories = categories;
    }

    @Override
    public String toString(){
        return "Livre : " + titre + " (" + anneePublication + ")";
    }
}
