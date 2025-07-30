package com.suzasob.building_permission.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.suzasob.building_permission.model.ConstructionProject;
import com.suzasob.building_permission.model.Permit;
import com.suzasob.building_permission.repository.PermitRepository;

@Service
public class PermitService {

    @Autowired
    private PermitRepository permitRepository;

    public Permit issuePermit(Permit permit) {
        return permitRepository.save(permit);
    }

    public Optional<Permit> getPermitByProject(ConstructionProject project) {
        return permitRepository.findByProject(project);
    }
}
