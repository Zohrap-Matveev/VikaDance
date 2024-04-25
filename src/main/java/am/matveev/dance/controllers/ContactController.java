package am.matveev.dance.controllers;

import am.matveev.dance.dto.ContactDTO;
import am.matveev.dance.services.ContactService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/contacts")
@RestController
@RequiredArgsConstructor
public class ContactController{

    private final ContactService contactService;

    @PostMapping
    public ContactDTO createContact(@RequestBody ContactDTO contactDTO) {
        log.info("Creating contact {}", contactDTO);
        return contactService.create(contactDTO);
    }
}
