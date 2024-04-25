package am.matveev.dance.repositories;

import am.matveev.dance.entities.ProjectsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectsEntity,Integer>{
}
