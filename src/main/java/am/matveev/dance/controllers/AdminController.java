package am.matveev.dance.controllers;

import am.matveev.dance.dto.ProjectDTO;
import am.matveev.dance.request.BioRequest;
import am.matveev.dance.request.DeleteNewsRequest;
import am.matveev.dance.request.NewsRequest;
import am.matveev.dance.request.UpdateNewsRequest;
import am.matveev.dance.services.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RequestMapping("/api/admin")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AdminController{

    private final NewsService newsService;
    private final ProjectService projectService;
    private final CheckService checkService;
    private final BioService bioService;
    private final ImageService imageService;

    @PostMapping("/news")
    public ResponseEntity<?> postNews(@RequestBody @Valid NewsRequest request){
        if(! checkService.checkAdminPassword(request.getEnteredPassword())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access.");
        }
        return ResponseEntity.ok(newsService.createNews(request.getNewsDTO()));
    }

    @PutMapping("/update/news/{newsId}")
    public ResponseEntity<?> updateNews(@PathVariable("newsId") int id, @RequestBody @Valid UpdateNewsRequest request){
        if(! checkService.checkAdminPassword(request.getEnteredPassword())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access.");
        }
        newsService.updateNews(id, request.getNewsDTO());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/news/{newsId}")
    public ResponseEntity<?> deleteNews(@PathVariable("newsId") int id, @RequestBody @Valid DeleteNewsRequest request){
        log.info("Received password: {}", request.getEnteredPassword());
        if(! checkService.checkAdminPassword(request.getEnteredPassword())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access.");
        }
        newsService.deleteNews(id);
        return ResponseEntity.ok("News deleted successfully.");
    }

    @PostMapping("/projects")
    public ResponseEntity<?> createProject(@RequestPart("file") MultipartFile file,
                                           @RequestPart("title") String title,
                                           @RequestPart("desc") String description,
                                           @RequestPart("enteredPassword") String enteredPassword){
        if(! checkService.checkAdminPassword(enteredPassword)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access.");
        }

        try{
            ProjectDTO projectDTO = new ProjectDTO();
            projectDTO.setTitle(title);
            projectDTO.setDescription(description);
            byte[] imageBytes = file.getBytes();
            projectDTO.setImage(imageBytes);
            projectService.createProject(projectDTO);
            return ResponseEntity.ok().build();
        }catch(IOException e){
            log.error("Failed to read image file: {}", e.getMessage());
            throw new RuntimeException("Failed to read image file", e);
        }
    }

    @PutMapping("/update/{projectId}/add-photo")
    public ResponseEntity<?> addPhotoToProject(@PathVariable int projectId,
                                               @RequestPart("file") MultipartFile file,
                                               @RequestPart("enteredPassword") String enteredPassword){
        if(! checkService.checkAdminPassword(enteredPassword)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access.");
        }

        try{
            ProjectDTO updatedProject = projectService.addPhotoToProject(projectId, file);
            return ResponseEntity.ok(updatedProject);
        }catch(Exception e){
            log.error("Failed to add photo to project: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add photo");
        }
    }

    @PostMapping("/bio")
    public ResponseEntity<?> createBio(@RequestBody @Valid BioRequest request){
        if(! checkService.checkAdminPassword(request.getEnteredPassword())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access.");
        }

        bioService.createAndUpdateBio(request.getBioDTO());
        return ResponseEntity.ok("Bio created successfully.");
    }

    @DeleteMapping("/delete/projects/{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable("projectId") int projectId, @RequestParam("enteredPassword") String enteredPassword){
        ResponseEntity<String> UNAUTHORIZED = getStringResponseEntity(enteredPassword);
        if(UNAUTHORIZED != null) return UNAUTHORIZED;
        projectService.deleteProject(projectId);
        return ResponseEntity.ok("Project deleted successfully.");
    }

    private ResponseEntity<String> getStringResponseEntity(String enteredPassword){
        if(! checkService.checkAdminPassword(enteredPassword)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access.");
        }
        return null;
    }

    @DeleteMapping("/delete/image/{imageId}")
    public ResponseEntity<?> deleteImage(@PathVariable("imageId") int imageId, @RequestParam("enteredPassword") String enteredPassword){
        if(! checkService.checkAdminPassword(enteredPassword)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access.");
        }
        imageService.deleteImage(imageId);
        return ResponseEntity.ok("Image deleted successfully.");
    }
}

