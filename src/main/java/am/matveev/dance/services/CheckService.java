package am.matveev.dance.services;

import am.matveev.dance.entities.AdminEntity;
import am.matveev.dance.repositories.AdminRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CheckService{

    private final AdminRepository adminRepository;

    @Value("${admin.email}")
    private String adminEmail;

    @Transactional
    public boolean checkAdminPassword(String enteredPassword) {
        AdminEntity adminEntity = adminRepository.findByEmail(adminEmail);

        return adminEntity != null && adminEntity.getPassword().equals(enteredPassword);
    }
}

