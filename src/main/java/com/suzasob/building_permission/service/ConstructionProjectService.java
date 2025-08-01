package com.suzasob.building_permission.service;

import com.suzasob.building_permission.dto.*;
import com.suzasob.building_permission.model.ConstructionProject;
import com.suzasob.building_permission.model.User;
import com.suzasob.building_permission.repository.ConstructionProjectRepository;
import com.suzasob.building_permission.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConstructionProjectService {

    private final ConstructionProjectRepository projectRepository;
    private final UserRepository userRepository;

    // Create project from DTO
    public ProjectResponseDTO submitProject(ProjectRegistrationDTO dto) {
        ConstructionProject project = mapToEntity(dto);
        ConstructionProject saved = projectRepository.save(project);
        return mapToDTO(saved);
    }

    // Get all projects
    public List<ProjectResponseDTO> getAllProjects() {
        return projectRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Get by status
    public List<ProjectResponseDTO> getProjectsByStatus(String status) {
        return projectRepository.findByStatus(status.toUpperCase())
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Mapping DTO → Entity
    public ConstructionProject mapToEntity(ProjectRegistrationDTO dto) {
        ConstructionProject entity = new ConstructionProject();
        entity.setName(dto.getName());
        entity.setAddress(dto.getAddress());
        entity.setStatus(dto.getStatus());
        entity.setSubmissionDate(dto.getSubmissionDate());
        entity.setTorDocument(dto.getTorDocument());
        entity.setBuildingPlan(dto.getBuildingPlan());
        entity.setLatitude(dto.getLatitude());
        entity.setLongitude(dto.getLongitude());

        entity.setOwner(userRepository.findById(dto.getOwnerId()).orElse(null));
        entity.setConsultant(userRepository.findById(dto.getConsultantId()).orElse(null));
        entity.setEngineer(userRepository.findById(dto.getEngineerId()).orElse(null));
        return entity;
    }

    // Mapping Entity → DTO
    public ProjectResponseDTO mapToDTO(ConstructionProject project) {
        ProjectResponseDTO dto = new ProjectResponseDTO();
        dto.setProjectId(project.getProjectId());
        dto.setName(project.getName());
        dto.setAddress(project.getAddress());
        dto.setStatus(project.getStatus());
        dto.setSubmissionDate(project.getSubmissionDate());
        dto.setTorDocument(project.getTorDocument());
        dto.setBuildingPlan(project.getBuildingPlan());
        dto.setLatitude(project.getLatitude());
        dto.setLongitude(project.getLongitude());

        dto.setOwner(mapUser(project.getOwner()));
        dto.setConsultant(mapUser(project.getConsultant()));
        dto.setEngineer(mapUser(project.getEngineer()));

        if (project.getGISAssessment() != null) {
            dto.setRiskType(project.getGISAssessment());
            dto.setRiskLevel(project.getGISAssessment());
            dto.setReportFile(project.getGISAssessment());
            dto.setAssessmentDate(project.getGISAssessment());
        }
        return dto;
    }

    private SimpleUserDTO mapUser(User user) {
        if (user == null) return null;
        SimpleUserDTO dto = new SimpleUserDTO();
        dto.setUserId(user.getUserId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole().name());
        return dto;
    }

   public ProjectResponseDTO getProjectById(Long id) {
    ConstructionProject project = projectRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Project with ID " + id + " not found"));
    return mapToDTO(project);
}
    public ProjectResponseDTO updateProjectStatus(Long projectId, ProjectUpdateDTO updateDTO) {
        ConstructionProject project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project with ID " + projectId + " not found"));
        project.setStatus(updateDTO.toUpperCase());
        ConstructionProject updatedProject = projectRepository.save(project);
        return mapToDTO(updatedProject);
    }
}