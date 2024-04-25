package am.matveev.dance.repositories;

import am.matveev.dance.entities.BioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BioRepository extends JpaRepository<BioEntity,Integer>{
}
