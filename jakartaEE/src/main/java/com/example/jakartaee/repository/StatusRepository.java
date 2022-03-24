package com.example.jakartaee.repository;

import com.example.jakartaee.domain.Status;

import java.util.List;

public interface StatusRepository {

    Status save(Status status);

    Status find(Long id);

    List<Status> findAll();

    Long delete(Long id);

}
