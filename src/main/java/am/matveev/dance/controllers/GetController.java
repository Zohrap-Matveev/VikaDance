package am.matveev.dance.controllers;

import am.matveev.dance.dto.*;
import am.matveev.dance.entities.ContactEntity;
import am.matveev.dance.entities.ImageEntity;
import am.matveev.dance.mappers.ContactMapper;
import am.matveev.dance.repositories.ImageRepository;
import am.matveev.dance.services.*;
import jakarta.validation.Valid;
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
    private final ContactService contactService;
    private final ContactMapper contactMapper;
    private final ImageService imageService;


    @GetMapping("/news")
    public List<NewsDTO> getAllNews() {
        return newsService.getNews();
    }

    @GetMapping("/news/{newsId}")
    public NewsDTO getNewsById(@PathVariable("newsId") int id) {
        return newsService.findOneNews(id);
    }

    @GetMapping("/projects")
    public List<ProjectDtoWithoutImage> getAllProjects() {
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
                        .body(projectDTO.getImage());
    }

    @PostMapping("/contact/sendMessage")
    public ResponseEntity<String> sendMessage(@RequestParam("email") String email, @RequestParam("message") String message,
                                               ContactDTO contactDTO) {
        ContactEntity contactEntity = contactMapper.toEntity(contactDTO);
        contactEntity.setEmail(email);
        contactEntity.setMessage(message);

        contactService.create(contactDTO);

        boolean messageSent = emailService.sendEmailToAdmin(email, message);

        if (messageSent) {
            return ResponseEntity.ok("Your message has been sent successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send message.");
        }
    }

    @GetMapping("/images/{imageId}")
    public ResponseEntity<byte[]> getImages(@PathVariable int imageId) {
       ImageDTO imageDTO = imageService.findOneImage(imageId);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf(MediaType.IMAGE_PNG_VALUE))
                .body(imageDTO.getImages());
    }

    @GetMapping("/images")
    public ResponseEntity<List<ImageDTO>> getAllImages() {
        List<ImageDTO> imageDTOs = imageService.findAllImages();
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(imageDTOs);
    }

}

