package am.matveev.dance.repositories;

import am.matveev.dance.entities.ContactEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<ContactEntity,Integer>{

}
