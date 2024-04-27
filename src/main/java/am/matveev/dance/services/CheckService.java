package am.matveev.dance.services;

import am.matveev.dance.entities.AdminEntity;
import am.matveev.dance.exceptions.WrongPasswordException;
import am.matveev.dance.repositories.AdminRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CheckService{

    private final AdminRepository adminRepository;

    @Transactional
    public boolean checkAdminPassword(String enteredPassword) {
        AdminEntity adminEntity = adminRepository.findById(1)
                .orElseThrow(WrongPasswordException ::new);

        return adminEntity != null && adminEntity.getPassword().equals(enteredPassword);
    }
}

