package com.example.jakartaee.service;

import com.example.jakartaee.domain.Service;

import java.util.List;

public interface ServiceService {

    Service save(Service service);

    Service findById(Long id);

    List<Service> findAll();

    Service delete(Long id);

}
