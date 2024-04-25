package am.matveev.dance.mappers;

import am.matveev.dance.dto.ImageDTO;
import am.matveev.dance.entities.ImageEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ImageMapper{

    ImageEntity toImageEntity(ImageDTO imageDTO);
    ImageDTO toDTO(ImageEntity imageEntity);
}
