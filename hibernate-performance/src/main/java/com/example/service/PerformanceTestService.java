package com.example.service;

import com.example.model.Auteur;
import com.example.model.Livre;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class PerformanceTestService {

    private EntityManagerFactory emf;

    public PerformanceTestService(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void testN1Problem() {

        EntityManager em = emf.createEntityManager();

        try {

            List<Auteur> auteurs = em.createQuery(
                            "SELECT a FROM Auteur a", Auteur.class)
                    .getResultList();

            for (Auteur auteur : auteurs) {

                System.out.println("Auteur : " + auteur.getNom());

                for (Livre livre : auteur.getLivres()) {

                    System.out.println("Livre : " + livre.getTitre());

                    System.out.println("Categories : " + livre.getCategories().size());
                }
            }

        } finally {

            em.close();
        }
    }

    public void testJoinFetch() {

        EntityManager em = emf.createEntityManager();

        try {

            List<Auteur> auteurs = em.createQuery(
                            "SELECT DISTINCT a FROM Auteur a LEFT JOIN FETCH a.livres",
                            Auteur.class)
                    .getResultList();

            for (Auteur auteur : auteurs) {

                System.out.println("Auteur : " + auteur.getNom());

                for (Livre livre : auteur.getLivres()) {

                    System.out.println("Livre : " + livre.getTitre());
                }
            }

        } finally {

            em.close();
        }
    }

    public void testEntityGraph() {

        EntityManager em = emf.createEntityManager();

        try {

            List<Livre> livres = em.createQuery(
                            "SELECT l FROM Livre l", Livre.class)
                    .getResultList();

            for (Livre livre : livres) {

                System.out.println("Livre : " + livre.getTitre());

                System.out.println("Auteur : " + livre.getAuteur().getNom());

                System.out.println("Categories : " + livre.getCategories().size());
            }

        } finally {

            em.close();
        }
    }
}