package am.matveev.dance.mappers;

import am.matveev.dance.dto.ProjectDTO;
import am.matveev.dance.entities.ImageEntity;
import am.matveev.dance.entities.ProjectsEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProjectMapper{

    ProjectsEntity toProjectEntity(ProjectDTO projectDTO);
    ProjectDTO toDTO(ProjectsEntity projectsEntity);

}
