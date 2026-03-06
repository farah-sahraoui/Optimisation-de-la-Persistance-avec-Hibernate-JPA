package com.example;

import com.example.service.DataInitService;
import com.example.service.PerformanceTestService;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class App {

    public static void main(String[] args) {

        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("hibernate-performance");

        try {

            DataInitService dataService = new DataInitService(emf);
            dataService.initData();

            PerformanceTestService testService =
                    new PerformanceTestService(emf);

            System.out.println("TEST N+1");
            testService.testN1Problem();

            System.out.println("TEST JOIN FETCH");
            testService.testJoinFetch();

            System.out.println("TEST ENTITY GRAPH");
            testService.testEntityGraph();

        } finally {

            emf.close();
        }
    }
}