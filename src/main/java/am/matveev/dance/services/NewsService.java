package am.matveev.dance.services;

import am.matveev.dance.dto.NewsDTO;
import am.matveev.dance.entities.NewsEntity;
import am.matveev.dance.exceptions.NewsNotFoundException;
import am.matveev.dance.mappers.NewsMapper;
import am.matveev.dance.repositories.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewsService{

    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;

    @Transactional(readOnly = true)
    public List<NewsDTO> getNews(){
        List<NewsEntity> newsEntities = newsRepository.findAll();
        List<NewsDTO> newsDTOS = newsEntities.stream()
                .map(newsMapper::toDTO)
                .collect(Collectors.toList());
                return newsDTOS;
    }

    @Transactional(readOnly = true)
    public NewsDTO findOneNews(int id){
        NewsEntity newsEntity = newsRepository.findById(id)
                .orElseThrow(NewsNotFoundException ::new);
        return newsMapper.toDTO(newsEntity);
    }

    @Transactional
    public NewsDTO createNews(NewsDTO newsDTO){
        NewsEntity newsEntity = newsMapper.toEntity(newsDTO);
        newsEntity = newsRepository.save(newsEntity);
        return newsMapper.toDTO(newsEntity);
    }

    @Transactional
    public NewsDTO updateNews(int id, NewsDTO newsDTO){
        NewsEntity newsEntity = newsMapper.toEntity(newsDTO);
        newsEntity.setId(id);
        newsEntity = newsRepository.save(newsEntity);
        return newsMapper.toDTO(newsEntity);
    }

    @Transactional
    public void deleteNews(int id){
        newsRepository.deleteById(id);
    }
}
