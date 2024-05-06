package am.matveev.dance.service;

import am.matveev.dance.dto.ProjectDTO;
import am.matveev.dance.dto.ProjectDtoWithoutImage;
import am.matveev.dance.entities.ProjectsEntity;
import am.matveev.dance.exceptions.ProjectNotFoundException;
import am.matveev.dance.mappers.ProjectMapper;
import am.matveev.dance.repositories.ProjectRepository;
import am.matveev.dance.services.ProjectService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest{

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ProjectMapper projectMapper;

    @InjectMocks
    private ProjectService projectService;

    @Test
    public void getProjectDtoWithoutImage() throws IOException{

        ProjectsEntity projectsEntity = new ProjectsEntity();
        File file = new File("/home/genilot/Pictures/Screenshots/Screenshot from 2024-05-02 14-30-05.png");
        byte[] imageData = Files.readAllBytes(file.toPath());
        projectsEntity.setId(1);
        projectsEntity.setTitle("For Madmen Only");
        projectsEntity.setImage(imageData);

        when(projectRepository.findAll()).thenReturn(Arrays.asList(projectsEntity));

        ProjectDtoWithoutImage projectDtoWithoutImage = new ProjectDtoWithoutImage();
        projectDtoWithoutImage.setId(1);
        projectDtoWithoutImage.setTitle("For Madmen Only");
        projectDtoWithoutImage.setImage(imageData);

        when(projectMapper.toDTOWithoutImage(projectsEntity)).thenReturn(projectDtoWithoutImage);

        List<ProjectDtoWithoutImage> result = projectService.getProjects();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(projectDtoWithoutImage.getId(), result.get(0).getId());
    }

    @Test
    public void testFindOneProject() throws IOException{
        int projectId = 1;
        ProjectsEntity projectsEntity = new ProjectsEntity();
        File file = new File("/home/genilot/Pictures/Screenshots/Screenshot from 2024-05-02 14-30-05.png");
        byte[] imageData = Files.readAllBytes(file.toPath());
        projectsEntity.setId(projectId);
        projectsEntity.setTitle("For Madmen Only");
        projectsEntity.setImage(imageData);
        projectsEntity.setDescription("Very cool Project");

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(projectsEntity));

        ProjectDTO projectDto = new ProjectDTO();
        projectDto.setId(projectsEntity.getId());
        projectDto.setTitle(projectsEntity.getTitle());
        projectsEntity.setImage(projectDto.getImage());
        projectDto.setDescription(projectsEntity.getDescription());

        when(projectMapper.toDTO(projectsEntity)).thenReturn(projectDto);

        ProjectDTO result = projectService.findOneProject(projectId);

        assertNotNull(result);
        assertEquals(projectDto, result);

    }

    @Test
    public void testFindOneProject_NotFound() throws IOException{
        int projectId = 1;
        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

        assertThrows(ProjectNotFoundException.class, () -> projectService.findOneProject(projectId));
    }
}
