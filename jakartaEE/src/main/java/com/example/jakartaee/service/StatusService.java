package com.example.jakartaee.service;

import com.example.jakartaee.domain.Status;

import java.util.List;

public interface StatusService {

    Status save(Status status);

    Status findById(Long id);

    List<Status> findAll();

    Status update(Status status);

    Status delete(Long id);

    Status approve(Long id);

    Status cancel(Long id);

    Status start(Long id);

    Status finish(Long id);

    Status close(Long id);

}
