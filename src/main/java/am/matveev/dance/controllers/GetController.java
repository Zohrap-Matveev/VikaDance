package am.matveev.dance.controllers;

import am.matveev.dance.dto.NewsDTO;
import am.matveev.dance.dto.ProjectDTO;
import am.matveev.dance.services.EmailService;
import am.matveev.dance.services.NewsService;
import am.matveev.dance.services.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class GetController{

    private final NewsService newsService;
    private final ProjectService projectService;
    private final EmailService emailService;

    @GetMapping("/news")
    public List<NewsDTO> getAllNews() {
        return newsService.getNews();
    }

    @GetMapping("/news/{newsId}")
    public NewsDTO getNewsById(@PathVariable("newsId") int id) {
        return newsService.findOneNews(id);
    }

    @GetMapping("/projects")
    public List<ProjectDTO> getAllProjects() {
        return projectService.getProjects();
    }

    @GetMapping("/projects/{projectId}")
    public ProjectDTO getProjectById(@PathVariable("projectId") int id) {
        ProjectDTO projectDTO = projectService.findOneProject(id);
        return projectDTO;
    }

    @GetMapping("/projects/images/{projectId}")
    public ResponseEntity<byte[]> getProjectImagesById(@PathVariable("projectId") int id) {
        ProjectDTO projectDTO = projectService.findOneProject(id);
        return ResponseEntity.status(HttpStatus.OK)
                        .contentType(MediaType.valueOf(MediaType.IMAGE_PNG_VALUE))
                        .body(projectDTO.getBytes());
    }

    @PostMapping("/contact/sendMessage")
    public ResponseEntity<String> sendMessage(@RequestParam("email") String email, @RequestParam("message") String message){
        boolean messageSent = emailService.sendEmailToAdmin(email, message);

        if(messageSent){
            return ResponseEntity.ok("Your message has been sent successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send message.");
        }
    }
}

