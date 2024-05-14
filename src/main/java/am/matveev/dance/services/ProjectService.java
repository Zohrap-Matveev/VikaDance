package am.matveev.dance.services;

import am.matveev.dance.dto.ProjectDTO;
import am.matveev.dance.dto.ProjectDtoWithoutImage;
import am.matveev.dance.entities.ImageEntity;
import am.matveev.dance.entities.ProjectsEntity;
import am.matveev.dance.exceptions.ImageNotFoundException;
import am.matveev.dance.exceptions.ProjectNotFoundException;
import am.matveev.dance.mappers.ProjectMapper;
import am.matveev.dance.repositories.ImageRepository;
import am.matveev.dance.repositories.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectService{

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final ImageRepository imageRepository;

    @Transactional(readOnly = true)
    public List<ProjectDtoWithoutImage> getProjects(){
        List<ProjectsEntity> projectsEntities = projectRepository.findAll();
        List<ProjectDtoWithoutImage> projectDTOS = projectsEntities.stream()
                .map(projectMapper :: toDTOWithoutImage)
                .collect(Collectors.toList());
        return projectDTOS;
    }

    @Transactional(readOnly = true)
    public ProjectDTO findOneProject(int id){
        ProjectsEntity projectsEntity = projectRepository.findById(id)
                .orElseThrow(ProjectNotFoundException :: new);
        return projectMapper.toDTO(projectsEntity);
    }


    @Transactional
    public ProjectDTO createProject(ProjectDTO projectDTO){
        try{
            if(projectDTO == null || projectDTO.getImage() == null || projectDTO.getImage().length == 0){
                throw new IllegalArgumentException("Project data or image is missing");
            }

            ProjectsEntity projectsEntity = projectMapper.toProjectEntity(projectDTO);
            projectsEntity = projectRepository.save(projectsEntity);
            return projectMapper.toDTO(projectsEntity);
        }catch(Exception e){
            log.error("Failed to create project: {}", e.getMessage());
            throw new RuntimeException("Failed to create project", e);
        }
    }

    @Transactional
    public ProjectDTO addPhotoToProject(int projectId, MultipartFile file) throws IOException {
        log.info("Adding photo to project with ID: {}", projectId);
        ProjectsEntity project = projectRepository.findById(projectId)
                .orElseThrow(ProjectNotFoundException::new);

        log.info("Project before saving: {}", project);

        byte[] imageData = file.getBytes();

        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setImages(imageData);
        imageEntity.setProjects(project);
        imageRepository.save(imageEntity);

        List<ImageEntity> images = project.getImages();
        if (images == null) {
            images = new ArrayList<>();
            project.setImages(images);
        }
        images.add(imageEntity);

        project = projectRepository.save(project);

        log.info("Photo added successfully to project with ID: {}", projectId);
        return projectMapper.toDTO(project);
    }

    @Transactional
    public void deleteProject(int projectId){
        projectRepository.deleteById(projectId);
    }


    @Transactional(readOnly = true)
    public List<byte[]> getImagesByProjectId(int projectId) {
        ProjectsEntity project = projectRepository.findById(projectId)
                .orElseThrow(ProjectNotFoundException::new);

        List<ImageEntity> images = project.getImages();

        return images.stream()
                .map(ImageEntity::getImages)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public byte[] getImageByProjectIdAndImageId(int projectId, int imageId){
        try{
            ProjectsEntity project = projectRepository.findById(projectId)
                    .orElseThrow(ProjectNotFoundException :: new);

            ImageEntity image = imageRepository.findById(imageId)
                    .orElseThrow(ImageNotFoundException :: new);

            if(! Objects.equals(image.getProjects().getId(), project.getId())){
                throw new ImageNotFoundException();
            }
            return image.getImages();
        }catch(ProjectNotFoundException | ImageNotFoundException ex){
            log.error("Error occurred while getting image", ex);
            throw ex;
        }catch(Exception ex){
            log.error("Unexpected error occurred while getting image", ex);
            throw ex;
        }
    }
}
