package am.matveev.dance.controllers;

import am.matveev.dance.dto.*;
import am.matveev.dance.entities.ContactEntity;
import am.matveev.dance.exceptions.ImageNotFoundException;
import am.matveev.dance.exceptions.ProjectNotFoundException;
import am.matveev.dance.mappers.ContactMapper;
import am.matveev.dance.services.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class GetController{

    private final NewsService newsService;
    private final ProjectService projectService;
    private final EmailService emailService;
    private final ContactService contactService;
    private final ContactMapper contactMapper;
    private final ImageService imageService;
    private final BioService bioService;


    @GetMapping("/news")
    public List<NewsDTO> getAllNews(){
        return newsService.getNews();
    }

    @GetMapping("/news/{newsId}")
    public NewsDTO getNewsById(@PathVariable("newsId") int id){
        return newsService.findOneNews(id);
    }

    @GetMapping("/projects")
    public List<ProjectDtoWithoutImage> getAllProjects(){
        return projectService.getProjects();
    }

    @GetMapping("/projects/{projectId}")
    public ProjectDTO getProjectById(@PathVariable("projectId") int id){
        ProjectDTO projectDTO = projectService.findOneProject(id);
        return projectDTO;
    }

    @GetMapping("/projects/images/{projectId}")
    public ResponseEntity<byte[]> getProjectImagesById(@PathVariable("projectId") int id){
        ProjectDTO projectDTO = projectService.findOneProject(id);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf(MediaType.IMAGE_PNG_VALUE))
                .body(projectDTO.getImage());
    }

    @PostMapping("/contact/sendMessage")
    public ResponseEntity<String> sendMessage(@Valid @RequestBody ContactDTO contactDTO){
        ContactEntity contactEntity = contactMapper.toEntity(contactDTO);
        contactService.create(contactDTO);

        boolean messageSent = emailService.sendEmailToAdmin(contactDTO.getEmail(), contactDTO.getMessage());

        if(messageSent){
            return ResponseEntity.ok("Your message has been sent successfully!");
        }else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send message.");
        }
    }

    @GetMapping("/images/{imageId}")
    public ResponseEntity<byte[]> getImages(@PathVariable int imageId){
        ImageDTO imageDTO = imageService.findOneImage(imageId);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf(MediaType.IMAGE_PNG_VALUE))
                .body(imageDTO.getImages());
    }

    @GetMapping("/images")
    public ResponseEntity<List<ImageDTO>> getAllImages(){
        List<ImageDTO> imageDTOs = imageService.findAllImages();
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(imageDTOs);
    }

    @GetMapping("/bio")
    public List<BioDTO> getAllBios(){
        return bioService.findAllBios();
    }

    @GetMapping("/{projectId}/images")
    public ResponseEntity<byte[]> getImagesByProjectId(@PathVariable int projectId){
        try{
            List<byte[]> images = projectService.getImagesByProjectId(projectId);
            byte[] concatenatedImages = concatenateImages(images);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(concatenatedImages);
        }catch(ProjectNotFoundException | IOException e){
            return ResponseEntity.notFound().build();
        }
    }

    private byte[] concatenateImages(List<byte[]> images) throws IOException{
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        for(byte[] image : images){
            baos.write(image);
        }
        return baos.toByteArray();
    }

    @GetMapping("/{projectId}/images/{imageId}")
    public ResponseEntity<byte[]> getImageByProjectIdAndImageId(@PathVariable int projectId, @PathVariable int imageId){
        try{
            byte[] imageData = projectService.getImageByProjectIdAndImageId(projectId, imageId);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(imageData);
        }catch(ImageNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

}

