# Optimisation de la Persistance avec Hibernate & JPA
Ce projet démontre comment diagnostiquer et résoudre les problèmes de performance courants lors de l'utilisation d'un ORM (Object-Relational Mapping),
notamment le phénomène du N+1 Select, en utilisant des stratégies avancées de chargement et de mise en cache.

## Stack Technique:
Java

Hibernate / JPA

Ehcache (Cache de second niveau)

H2 Database

## Étape 1 : Le Problème de base (N+1 Select):
Par défaut, le chargement "Lazy" (paresseux) oblige Hibernate à exécuter une nouvelle requête SQL pour chaque relation enfant rencontrée.

Diagnostic dans les logs :


![WhatsApp Image 2026-03-06 at 16 47 27](https://github.com/user-attachments/assets/2c404a11-785f-4452-a33b-37b56b45b7ef)




On voit une requête SELECT pour l'auteur "Hugo".

Elle est immédiatement suivie d'une autre requête SELECT pour charger ses livres.

Impact : Si nous avons 100 auteurs, nous ferons 101 requêtes à la base de données.

## Étape 2 : Première Solution (JOIN FETCH):
La première optimisation consiste à utiliser JOIN FETCH dans nos requêtes JPQL pour ramener le parent et ses enfants en une seule fois.

Résultat de l'optimisation :

Hibernate utilise un LEFT OUTER JOIN pour récupérer l'auteur et ses livres associés.

Performance : Le nombre de requêtes SQL tombe de N+1 à 1 seule.


![WhatsApp Image 2026-03-06 at 16 49 10](https://github.com/user-attachments/assets/4c1a2d62-c1fe-4bb3-a22e-ecc6a70ef944)
![WhatsApp Image 2026-03-06 at 16 49 48](https://github.com/user-attachments/assets/489e17fc-6521-49c0-a33e-5679cb490bda)



## Étape 3 : Le problème complexe (N+1+1):
Lorsque les relations deviennent plus profondes (Auteur -> Livres -> Catégories), même un simple JOIN FETCH peut ne pas suffire,
et Hibernate recommence à multiplier les requêtes pour les niveaux inférieurs.

Nouveau diagnostic :

On observe une explosion de requêtes répétitives pour les catégories.

Chaque livre déclenche ses propres requêtes de jointure pour ses catégories.

## Étape 4 : La Solution Ultime (JPA Entity Graph):
Pour charger l'intégralité du graphe d'objets (Auteur, ses Livres ET leurs Catégories) en une seule transaction, j'ai implémenté les Entity Graphs.

![WhatsApp Image 2026-03-06 at 16 50 21](https://github.com/user-attachments/assets/45fa47be-5ab4-473d-9f20-3689869f8932)



![WhatsApp Image 2026-03-06 at 16 51 57](https://github.com/user-attachments/assets/cac455bd-b01b-47f3-92fc-6f8f3f59d056)



un chargement classique multiplie des requêtes SQL pour récupérer les entités liées (Auteur → Livres → Catégories), l'Entity Graph force Hibernate à
regrouper toutes ces données dans une seule et unique requête optimisée avec les jointures nécessaires. Cela permet de conserver une stratégie de chargement
"Lazy" par défaut pour la légèreté de l'application, tout en bénéficiant d'une récupération complète et atomique de l'arborescence d'objets uniquement lorsque
le scénario métier l'exige.



# conclusion:

Ce projet constitue une étude de cas sur l'amélioration des performances d'accès aux données dans une architecture JPA/Hibernate. 
L'objectif principal était d'éradiquer le problème de performance dit "N+1 Select", où chaque relation d'une entité déclenche une requête SQL
supplémentaire, saturant ainsi les ressources de la base de données.
