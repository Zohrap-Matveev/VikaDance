package am.matveev.dance.services;

import am.matveev.dance.dto.BioDTO;
import am.matveev.dance.entities.BioEntity;
import am.matveev.dance.mappers.BioMapper;
import am.matveev.dance.repositories.BioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BioService{

    private final BioRepository bioRepository;
    private final BioMapper bioMapper;

    @Transactional
    public BioDTO createBio(BioDTO bioDTO){
        BioEntity bioEntity = bioMapper.toEntity(bioDTO);
        bioRepository.save(bioEntity);
        return bioDTO;
    }
}
