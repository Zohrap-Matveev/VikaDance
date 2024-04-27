package am.matveev.dance.controllers;

import am.matveev.dance.dto.NewsDTO;
import am.matveev.dance.dto.ProjectDTO;
import am.matveev.dance.entities.AdminEntity;
import am.matveev.dance.exceptions.WrongPasswordException;
import am.matveev.dance.repositories.AdminRepository;
import am.matveev.dance.request.*;
import am.matveev.dance.services.CheckService;
import am.matveev.dance.services.NewsService;
import am.matveev.dance.services.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Slf4j
@RequestMapping("/admin")
@RestController
@RequiredArgsConstructor
public class AdminController{

    private final NewsService newsService;
    private final ProjectService projectService;
    private final CheckService checkService;

    @PostMapping("/news")
    public ResponseEntity<?> postNews(@RequestBody NewsRequest request){
        if(! checkService.checkAdminPassword(request.getEnteredPassword())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access.");
        }
        return ResponseEntity.ok(newsService.createNews(request.getNewsDTO()));
    }

    @PutMapping("/update/news/{newsId}")
    public ResponseEntity<?> updateNews(@PathVariable("newsId") int id, @RequestBody UpdateNewsRequest request){
        if(! checkService.checkAdminPassword(request.getEnteredPassword())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access.");
        }
        return ResponseEntity.ok(newsService.updateNews(id, request.getNewsDTO()));
    }

    @DeleteMapping("/delete/news/{newsId}")
    public ResponseEntity<?> deleteNews(@PathVariable("newsId") int id, @RequestBody DeleteNewsRequest request){
        log.info("Received password: {}", request.getEnteredPassword());
        if(! checkService.checkAdminPassword(request.getEnteredPassword())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access.");
        }
        newsService.deleteNews(id);
        return ResponseEntity.ok("News deleted successfully.");
    }
    @DeleteMapping("/delete/projects/{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable("projectId") int projectId, @RequestBody DeleteProjectRequest request){
        if(! checkService.checkAdminPassword(request.getEnteredPassword())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access.");
        }
        projectService.deleteProject(projectId);
        return ResponseEntity.ok("Project deleted successfully.");
    }

    @PostMapping("/projects")
    public ResponseEntity<?> createProject(@RequestPart("file") MultipartFile file,
                                           @RequestPart("title") String title,
                                           @RequestPart("desc") String description,
                                           @RequestPart("enteredPassword") String enteredPassword) {
        if (!checkService.checkAdminPassword(enteredPassword)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access.");
        }

        try {
            ProjectDTO projectDTO = new ProjectDTO();
            projectDTO.setTitle(title);
            projectDTO.setDescription(description);
            byte[] imageBytes = file.getBytes();
            projectDTO.setBytes(imageBytes);
            return ResponseEntity.ok(projectService.createProject(projectDTO));
        } catch (IOException e) {
            log.error("Failed to read image file: {}", e.getMessage());
            throw new RuntimeException("Failed to read image file", e);
        }
    }



    @PutMapping("/update/projects/{projectId}/add-photo")
    public ResponseEntity<?> addPhotoToProject(@PathVariable("projectId") int projectId,
                                        @RequestParam("file") MultipartFile file,
                                        @RequestPart("enteredPassword") String enteredPassword){
        if (!checkService.checkAdminPassword(enteredPassword)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access.");
        }
        try{
            if(file == null || file.isEmpty()){
                throw new IllegalArgumentException("File is empty or not provided");
            }
            ProjectDTO projectDTO = projectService.findOneProject(projectId);

            byte[] imageBytes = file.getBytes();
            projectDTO.setBytes(imageBytes);

            return ResponseEntity.ok(projectService.updateProject(projectId, projectDTO));
        }catch(Exception e){
            log.error("Failed to add photo to project: {}", e.getMessage());
            throw new RuntimeException("Failed to add photo to project", e);
        }
    }



    @PostMapping("/check-admin")
    public ResponseEntity<String> checkAdmin(@RequestBody Map<String, String> requestBody){
        String enteredPassword = requestBody.get("password");
        if(checkService.checkAdminPassword(enteredPassword)){
            return ResponseEntity.ok("Access granted to post methods.");
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access.");
        }
    }
}

