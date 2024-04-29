package am.matveev.dance.repositories;

import am.matveev.dance.entities.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<AdminEntity,Integer>{
    AdminEntity findByEmail(String email);
}
