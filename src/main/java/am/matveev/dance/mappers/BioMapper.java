package am.matveev.dance.mappers;

import am.matveev.dance.dto.BioDTO;
import am.matveev.dance.entities.BioEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BioMapper{

    BioEntity toEntity(BioDTO bioDTO);
    BioDTO toBioDTO(BioEntity bioEntity);
}
