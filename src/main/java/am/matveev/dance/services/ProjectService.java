package am.matveev.dance.services;

import am.matveev.dance.dto.ProjectDTO;
import am.matveev.dance.entities.ProjectsEntity;
import am.matveev.dance.exceptions.ProjectNotFoundException;
import am.matveev.dance.mappers.ProjectMapper;
import am.matveev.dance.repositories.AdminRepository;
import am.matveev.dance.repositories.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectService{

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final AdminRepository adminRepository;

    @Transactional(readOnly = true)
    public List<ProjectDTO> getProjects(){
        List<ProjectsEntity> projectsEntities = projectRepository.findAll();
        List<ProjectDTO> projectDTOS = projectsEntities.stream()
                .map(projectMapper::toDTO)
                .collect(Collectors.toList());
        return projectDTOS;
    }

    @Transactional(readOnly = true)
    public ProjectDTO findOneProject(int id){
        ProjectsEntity projectsEntity = projectRepository.findById(id)
                .orElseThrow(ProjectNotFoundException ::new);
        return projectMapper.toDTO(projectsEntity);
    }


    @Transactional
    public ProjectDTO createProject(ProjectDTO projectDTO) {
        try {
            if (projectDTO == null || projectDTO.getBytes() == null || projectDTO.getBytes().length == 0) {
                throw new IllegalArgumentException("Project data or image is missing");
            }

            ProjectsEntity projectsEntity = projectMapper.toProjectEntity(projectDTO);
            projectsEntity = projectRepository.save(projectsEntity);
            return projectMapper.toDTO(projectsEntity);
        } catch (Exception e) {
            log.error("Failed to create project: {}", e.getMessage());
            throw new RuntimeException("Failed to create project", e);
        }
    }


    @Transactional
    public ProjectDTO updateProject(int id, ProjectDTO projectDTO) {
        try {
            ProjectDTO existingProject = findOneProject(id);

            existingProject.setTitle(projectDTO.getTitle());
            existingProject.setDescription(projectDTO.getDescription());
            existingProject.setBytes(projectDTO.getBytes());
            projectRepository.save(projectMapper.toProjectEntity(existingProject));
            return projectDTO;

        } catch (Exception e) {
            log.error("Failed to update project: {}", e.getMessage());
            throw new RuntimeException("Failed to update project", e);
        }
    }


}
