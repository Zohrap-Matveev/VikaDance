package am.matveev.dance.mappers;

import am.matveev.dance.dto.ProjectDTO;
import am.matveev.dance.entities.ProjectsEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProjectMapper{

    ProjectsEntity toEntity(ProjectDTO projectDTO);
    ProjectDTO toDTO(ProjectsEntity projectsEntity);
}
