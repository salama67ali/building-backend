package com.example.building_system.service;

import com.example.building_system.dto.ProjectRequestDTO;
import com.example.building_system.dto.ProjectResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface OwnerService {
    List<String> getNotifications(Long ownerId);
    ProjectResponseDTO submitProject(ProjectRequestDTO projectRequestDTO);
    List<ProjectResponseDTO> getProjectsByOwner(Long ownerId);
    ProjectResponseDTO getProjectDetails(Long projectId);
    void uploadDocument(Long projectId, MultipartFile file);
    void deleteProject(Long projectId);
    ProjectResponseDTO updateProject(Long projectId, ProjectRequestDTO projectRequestDTO);
    
    <Owner> Owner getOwnerById(Long id);
    <Owner> List<Owner> getAllOwners();
    <Owner> Owner createOwner(Owner owner);
    <Owner> Owner updateOwner(Long id, Owner updatedOwner);
    void deleteOwner(Long id);
}
