package am.matveev.dance.services;

import am.matveev.dance.dto.ContactDTO;
import am.matveev.dance.entities.ContactEntity;
import am.matveev.dance.mappers.ContactMapper;
import am.matveev.dance.repositories.ContactRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContactService{

    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;

    @Transactional
    public ContactDTO create(@Valid ContactDTO contactDTO){
        log.info("Creating contact {}", contactDTO);
            ContactEntity contactEntity = contactMapper.toEntity(contactDTO);
            contactEntity = contactRepository.save(contactEntity);
            contactMapper.toDTO(contactEntity);
        return contactDTO;
    }
}
