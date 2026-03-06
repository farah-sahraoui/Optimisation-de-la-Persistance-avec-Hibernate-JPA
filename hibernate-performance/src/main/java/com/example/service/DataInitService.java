package com.example.service;

import com.example.model.Auteur;
import com.example.model.Categorie;
import com.example.model.Livre;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class DataInitService {

    private EntityManagerFactory emf;

    public DataInitService(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void initData() {

        EntityManager em = emf.createEntityManager();

        try {

            em.getTransaction().begin();

            Categorie roman = new Categorie("Roman", "Oeuvres de fiction narrative");
            Categorie sf = new Categorie("Science-Fiction", "Histoires futuristes");
            Categorie fantasy = new Categorie("Fantasy", "Univers imaginaire");
            Categorie policier = new Categorie("Policier", "Enquetes et mysteres");
            Categorie bio = new Categorie("Biographie", "Recits de vie");

            em.persist(roman);
            em.persist(sf);
            em.persist(fantasy);
            em.persist(policier);
            em.persist(bio);

            Auteur a1 = new Auteur("Hugo", "Victor", "victor.hugo@mail.com");

            Livre l1 = new Livre("Les Miserables", 1862, "9781111111111");
            l1.setResume("Histoire d'un ancien prisonnier cherchant redemption");
            l1.ajouterCategorie(roman);

            Livre l2 = new Livre("Notre Dame de Paris", 1831, "9782222222222");
            l2.setResume("Une histoire autour de la cathedrale de Paris");
            l2.ajouterCategorie(roman);

            a1.ajouterLivre(l1);
            a1.ajouterLivre(l2);

            Auteur a2 = new Auteur("Asimov", "Isaac", "asimov@mail.com");

            Livre l3 = new Livre("Fondation", 1951, "9783333333333");
            l3.setResume("La creation d'une fondation pour sauver la civilisation");
            l3.ajouterCategorie(sf);

            Livre l4 = new Livre("Les Robots", 1950, "9784444444444");
            l4.setResume("Histoires autour des robots et de leurs lois");
            l4.ajouterCategorie(sf);

            a2.ajouterLivre(l3);
            a2.ajouterLivre(l4);

            Auteur a3 = new Auteur("Tolkien", "JRR", "tolkien@mail.com");

            Livre l5 = new Livre("Le Seigneur des Anneaux", 1954, "9785555555555");
            l5.setResume("Une quete pour detruire un anneau malefique");
            l5.ajouterCategorie(fantasy);

            Livre l6 = new Livre("Le Hobbit", 1937, "9786666666666");
            l6.setResume("Les aventures de Bilbon");
            l6.ajouterCategorie(fantasy);

            a3.ajouterLivre(l5);
            a3.ajouterLivre(l6);

            Auteur a4 = new Auteur("Christie", "Agatha", "christie@mail.com");

            for (int i = 1; i <= 20; i++) {

                Livre livre = new Livre("Mystere " + i, 1920 + i, "97877777777" + i);

                livre.setResume("Un mystere resolu par un detective");
                livre.ajouterCategorie(policier);

                a4.ajouterLivre(livre);
            }

            em.persist(a1);
            em.persist(a2);
            em.persist(a3);
            em.persist(a4);

            em.getTransaction().commit();

            System.out.println("Initialisation des donnees terminee");

        } catch (Exception e) {

            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            e.printStackTrace();

        } finally {

            em.close();
        }
    }

    public List<Auteur> getAuteurs() {

        EntityManager em = emf.createEntityManager();

        try {

            return em.createQuery("SELECT a FROM Auteur a", Auteur.class).getResultList();

        } finally {

            em.close();
        }
    }

    public List<Categorie> getCategories() {

        EntityManager em = emf.createEntityManager();

        try {

            return em.createQuery("SELECT c FROM Categorie c", Categorie.class).getResultList();

        } finally {

            em.close();
        }
    }
}