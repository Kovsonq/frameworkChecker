package com.example.jakartaee.service.impl;

import com.example.jakartaee.domain.Status;
import com.example.jakartaee.domain.User;
import com.example.jakartaee.repository.StatusRepository;
import com.example.jakartaee.service.StatusService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.util.List;

@ApplicationScoped
@Transactional
public class StatusServiceImpl implements StatusService, Serializable {

    private final StatusRepository statusRepository;

    @Inject
    public StatusServiceImpl(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    @Override
    public Status save(Status status) {
        return statusRepository.save(status)
                .orElseThrow(() -> new EntityExistsException("Error during saving entity " + status));
    }

    @Override
    public Status findById(Long id) {
        return statusRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity Status with id " + id + ", not found."));
    }

    @Override
    public List<Status> findAll() {
        return statusRepository.findAll();
    }

    @Override
    public Status update(Status newStatus) {
        Status status = findById(newStatus.getId());

        status.setApprovedTime(newStatus.getApprovedTime());
        status.setCanceledTime(newStatus.getCanceledTime());
        status.setStartedTime(newStatus.getStartedTime());
        status.setFinishedTime(newStatus.getFinishedTime());
        status.setClosedTime(newStatus.getClosedTime());

        return statusRepository.update(status)
                .orElseThrow(() -> new EntityExistsException("Error during updating entity " + status));
    }

    @Override
    public Status delete(Long id) {
        Status status = findById(id);
        statusRepository.delete(status);
        return status;
    }

}
