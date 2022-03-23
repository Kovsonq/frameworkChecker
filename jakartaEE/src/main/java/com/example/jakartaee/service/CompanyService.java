package com.example.jakartaee.service;

import com.example.jakartaee.domain.Company;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.util.List;

@ApplicationScoped
@Transactional
public class CompanyService implements Serializable {

    @PersistenceContext
    private EntityManager em;

    public Company findCompany(Long id) {
        return em.find(Company.class, id);
    }

    public void saveCompany(Company company) {
        em.persist(company);
    }

    public void deleteCompany(Company company) {
        em.remove(company);
    }

    public boolean checkManager(){
        return em.isOpen();
    }

    public List createQuery(String query){
        return em.createQuery(query).getResultList();
    }
}
