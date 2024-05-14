package am.matveev.dance.services;

import am.matveev.dance.dto.BioDTO;
import am.matveev.dance.entities.BioEntity;
import am.matveev.dance.exceptions.BioNotFoundException;
import am.matveev.dance.mappers.BioMapper;
import am.matveev.dance.repositories.BioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BioService{

    private final BioRepository bioRepository;
    private final BioMapper bioMapper;

    @Transactional
    public void createAndUpdateBio(BioDTO newBioDTO){
        Optional<BioEntity> oldBioEntityOpt = bioRepository.findFirstByOrderById();

        BioEntity newBioEntity = bioMapper.toEntity(newBioDTO);
        bioRepository.save(newBioEntity);

        oldBioEntityOpt.ifPresent(bioRepository::delete);
    }

    @Transactional(readOnly = true)
    public List<BioDTO> findAllBios(){
        List<BioEntity> bioEntities = bioRepository.findAll();
        List<BioDTO> bioDTOS = bioEntities.stream()
                .map(bioMapper::toBioDTO)
                .findAny()
                .map(Collections::singletonList)
                .orElse(Collections.emptyList());
        return bioDTOS;
    }
}
