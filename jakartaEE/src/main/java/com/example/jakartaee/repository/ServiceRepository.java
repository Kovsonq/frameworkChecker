package com.example.jakartaee.repository;

import com.example.jakartaee.domain.Service;

import java.util.List;
import java.util.Optional;

public interface ServiceRepository {

    Optional<Service> save(Service service);

    Optional<Service> findById(Long id);

    List<Service> findAll();

    Optional<Service> update(Service service);

    Optional<Service> delete(Service service);

}
