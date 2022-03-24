package com.example.jakartaee.repository;

import com.example.jakartaee.domain.Status;

import java.util.List;
import java.util.Optional;

public interface StatusRepository {

    Optional<Status> save(Status status);

    Optional<Status> findById(Long id);

    List<Status> findAll();

    Optional<Status> delete(Status status);

}
