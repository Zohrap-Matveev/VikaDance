package am.matveev.dance.mappers;

import am.matveev.dance.dto.NewsDTO;
import am.matveev.dance.entities.NewsEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NewsMapper{

    NewsEntity toEntity(NewsDTO newsDTO);
    NewsDTO toDTO(NewsEntity newsEntity);
}
