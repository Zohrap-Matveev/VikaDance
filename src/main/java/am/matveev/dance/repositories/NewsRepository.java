package am.matveev.dance.repositories;

import am.matveev.dance.entities.NewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<NewsEntity,Integer>{
}
