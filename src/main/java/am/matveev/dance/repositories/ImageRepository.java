package am.matveev.dance.repositories;

import am.matveev.dance.entities.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Integer>{
}
