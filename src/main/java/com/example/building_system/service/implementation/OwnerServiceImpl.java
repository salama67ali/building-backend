package com.example.building_system.service.implementation;

import com.example.building_system.dto.ProjectRequestDTO;
import com.example.building_system.dto.ProjectResponseDTO;
import com.example.building_system.service.OwnerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OwnerServiceImpl<OwnerRepository, Owner> implements OwnerService {

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public List<String> getNotifications(Long ownerId) {
        return List.of(
            "Project approved",
            "Document reviewed",
            "New message from consultant"
        );
    }

    @Override
    public ProjectResponseDTO submitProject(ProjectRequestDTO dto) {
        Project project = new Project();
        project.setProjectName(dto.getProjectName());
        project.setProjectDescription(dto.getProjectDescription());
        project.setOwnerId(dto.getOwnerId());

        return new ProjectResponseDTO(null, null, null);
    }

    @Override
    public List<ProjectResponseDTO> getProjectsByOwner(Long ownerId) {
        List<Project> projects = projectRepository.findByOwnerId(ownerId);
        return projects.stream()
                .map(p -> new ProjectResponseDTO(p.getId(), p.getProjectName(), p.getProjectDescription()))
                .collect(Collectors.toList());
    }

    @Override
    public ProjectResponseDTO getProjectDetails(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        return new ProjectResponseDTO(project.getId(), project.getProjectName(), project.getProjectDescription());
    }

    @Override
    public void uploadDocument(Long projectId, MultipartFile file) {
        // Hapa ungehifadhi file kwenye filesystem au database (e.g. as BLOB)
        // Kwenye mfano huu tunaacha tu method tupu
        System.out.println("File uploaded: " + file.getOriginalFilename());
    }

    @Override
    public void deleteProject(Long projectId) {
        projectRepository.deleteById(projectId);
    }
 
    @Override
    public ProjectResponseDTO updateProject(Long projectId, ProjectRequestDTO dto) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        project.setProjectName(dto.getProjectName());
        project.setProjectDescription(dto.getProjectDescription());
        Project updated = projectRepository.save(project);
        return new ProjectResponseDTO(updated.getId(), updated.getProjectName(), updated.getProjectDescription());
    }

    @SuppressWarnings("unchecked")
    @Override
    public Order getOwnerById(Long id) {
        return ownerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Owner not found"));
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Owner> getAllOwners() {
        return ownerRepository.findAll();
    } 

    @Override
    public void createOwner(Owner owner) {
        return ownerRepository.save(owner);
    }
 
    @Override
    public void updateOwner(Long id, Owner updatedOwner) {
        Owner owner = ownerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Owner not found"));
        owner.setName(updatedOwner.getName());
        owner.setEmail(updatedOwner.getEmail());
        return ownerRepository.save(owner);
    }

    @Override
    public void deleteOwner(Long id) {
        ownerRepository.deleteById(id);
    }

    @Override
    public void createOwner(Owner owner) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createOwner'");
    }

    @Override
    public void updateOwner(Long id, Owner updatedOwner) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateOwner'");
    }
}
