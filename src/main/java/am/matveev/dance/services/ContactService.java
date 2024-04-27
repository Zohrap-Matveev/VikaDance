package am.matveev.dance.services;

import am.matveev.dance.dto.ContactDTO;
import am.matveev.dance.entities.ContactEntity;
import am.matveev.dance.mappers.ContactMapper;
import am.matveev.dance.repositories.ContactRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContactService{

    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;

    @Transactional
    public ContactDTO create(ContactDTO contactDTO){
        log.info("Creating contact {}", contactDTO);
        CompletableFuture.runAsync(() -> {
            ContactEntity contactEntity = contactMapper.toEntity(contactDTO);
            contactEntity = contactRepository.save(contactEntity);
            contactMapper.toDTO(contactEntity);
        });

        return null;
    }
}
