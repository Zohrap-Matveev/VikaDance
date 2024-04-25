package am.matveev.dance.mappers;

import am.matveev.dance.dto.ContactDTO;
import am.matveev.dance.entities.ContactEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ContactMapper{

    ContactEntity toEntity(ContactDTO contactDTO);
    ContactDTO toDTO(ContactEntity contactEntity);
}
