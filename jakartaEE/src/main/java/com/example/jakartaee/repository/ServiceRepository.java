package com.example.jakartaee.repository;

import com.example.jakartaee.domain.Service;

import java.util.List;

public interface ServiceRepository {

    Service save(Service service);

    Service find(Long id);

    List<Service> findAll();

    Long delete(Long id);

}
