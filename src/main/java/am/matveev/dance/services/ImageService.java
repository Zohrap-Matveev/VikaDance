package am.matveev.dance.services;

import am.matveev.dance.dto.ImageDTO;
import am.matveev.dance.entities.ImageEntity;
import am.matveev.dance.mappers.ImageMapper;
import am.matveev.dance.repositories.ImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService{

    private final ImageRepository imageRepository;
    private final ImageMapper imageMapper;


    @Transactional(readOnly = true)
    public ImageDTO findOneImage(int imageId){
        ImageEntity image = imageRepository.findById(imageId)
                .orElseThrow();
        return imageMapper.toDTO(image);
    }

    @Transactional(readOnly = true)
    public List<ImageDTO> findAllImages() {
        List<ImageEntity> images = imageRepository.findAll();
        return images.stream()
                .map(imageMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteImage(int imageId){
        imageRepository.deleteById(imageId);
    }
}
