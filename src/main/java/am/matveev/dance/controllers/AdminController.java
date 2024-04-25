package am.matveev.dance.controllers;

import am.matveev.dance.dto.NewsDTO;
import am.matveev.dance.dto.ProjectDTO;
import am.matveev.dance.services.NewsService;
import am.matveev.dance.services.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RequestMapping("/admin")
@RestController
@RequiredArgsConstructor
public class AdminController{

    private final NewsService newsService;
    private final ProjectService projectService;

    @PostMapping("/news")
    public NewsDTO postNews(@RequestBody NewsDTO newsDTO){
        return newsService.createNews(newsDTO);
    }

    @PutMapping("/update/{newsId}")
    public NewsDTO updateNews(@PathVariable("newsId") int id, @RequestBody NewsDTO newsDTO){
        return newsService.updateNews(id, newsDTO);
    }

    @DeleteMapping("/delete/{newsId}")
    public void deleteNews(@PathVariable("newsId") int id){
        newsService.deleteNews(id);
    }

    @PostMapping("/projects")
    public ProjectDTO createProject(@RequestParam("file") MultipartFile file,
                                    @RequestParam("title") String title,
                                    @RequestParam("desc") String description){
        try{
            ProjectDTO projectDTO = new ProjectDTO();
            projectDTO.setTitle(title);
            projectDTO.setDescription(description);
            byte[] imageBytes = file.getBytes();
            projectDTO.setImages(imageBytes);
            return projectService.createProject(projectDTO);
        }catch(IOException e){
            log.error("Failed to read image file: {}", e.getMessage());
            throw new RuntimeException("Failed to read image file", e);
        }
    }

    @PutMapping("/projects/{projectId}/add-photo")
    public ProjectDTO addPhotoToProject(@PathVariable("projectId") int projectId,
                                        @RequestParam("file") MultipartFile file){
        try{
            if(file == null || file.isEmpty()){
                throw new IllegalArgumentException("File is empty or not provided");
            }

            ProjectDTO projectDTO = projectService.findOneProject(projectId);

            byte[] imageBytes = file.getBytes();
            projectDTO.setImages(imageBytes);

            return projectService.updateProject(projectId, projectDTO);
        }catch(Exception e){
            log.error("Failed to add photo to project: {}", e.getMessage());
            throw new RuntimeException("Failed to add photo to project", e);
        }
    }
    @DeleteMapping("/delete/{projectId}")
    public void deleteProject(@PathVariable("projectId") int id){
        projectService.deleteProject(id);
    }

}

