package am.matveev.dance.service;

import am.matveev.dance.dto.NewsDTO;
import am.matveev.dance.entities.NewsEntity;
import am.matveev.dance.exceptions.NewsNotFoundException;
import am.matveev.dance.mappers.NewsMapper;
import am.matveev.dance.repositories.NewsRepository;
import am.matveev.dance.services.NewsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NewsServiceTest {

    @Mock
    private NewsRepository newsRepository;

    @Mock
    private NewsMapper newsMapper;

    @InjectMocks
    private NewsService newsService;

//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }

    @Test
    public void testGetNewsWithMultipleNews() {
        // Arrange
        NewsEntity newsEntity1 = new NewsEntity();
        newsEntity1.setId(1);
        newsEntity1.setTitle("Test News 1");
        newsEntity1.setDate("2024-05-03");
        newsEntity1.setAddress("Test Address 1");
        newsEntity1.setDetails("Test Details 1");

        NewsEntity newsEntity2 = new NewsEntity();
        newsEntity2.setId(2);
        newsEntity2.setTitle("Test News 2");
        newsEntity2.setDate("2024-05-04");
        newsEntity2.setAddress("Test Address 2");
        newsEntity2.setDetails("Test Details 2");

        when(newsRepository.findAll()).thenReturn(Arrays.asList(newsEntity1, newsEntity2));

        NewsDTO newsDTO1 = new NewsDTO();
        newsDTO1.setId(newsEntity1.getId());
        newsDTO1.setTitle(newsEntity1.getTitle());
        newsDTO1.setDate(newsEntity1.getDate());
        newsDTO1.setAddress(newsEntity1.getAddress());
        newsDTO1.setDetails(newsEntity1.getDetails());

        NewsDTO newsDTO2 = new NewsDTO();
        newsDTO2.setId(newsEntity2.getId());
        newsDTO2.setTitle(newsEntity2.getTitle());
        newsDTO2.setDate(newsEntity2.getDate());
        newsDTO2.setAddress(newsEntity2.getAddress());
        newsDTO2.setDetails(newsEntity2.getDetails());

        when(newsMapper.toDTO(newsEntity1)).thenReturn(newsDTO1);
        when(newsMapper.toDTO(newsEntity2)).thenReturn(newsDTO2);

        // Act
        List<NewsDTO> result = newsService.getNews();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(newsDTO1, result.get(0));
        assertEquals(newsDTO2, result.get(1));
    }

    @Test
    public void testFindOneNews() {
        // Arrange
        int newsId = 1;
        NewsEntity newsEntity = new NewsEntity();
        newsEntity.setId(newsId);
        newsEntity.setTitle("Test News");
        newsEntity.setDate("2024-05-03");
        newsEntity.setAddress("Test Address");
        newsEntity.setDetails("Test Details");

        when(newsRepository.findById(newsId)).thenReturn(Optional.of(newsEntity));

        NewsDTO newsDTO = new NewsDTO();
        newsDTO.setId(newsEntity.getId());
        newsDTO.setTitle(newsEntity.getTitle());
        newsDTO.setDate(newsEntity.getDate());
        newsDTO.setAddress(newsEntity.getAddress());
        newsDTO.setDetails(newsEntity.getDetails());

        when(newsMapper.toDTO(newsEntity)).thenReturn(newsDTO);

        // Act
        NewsDTO result = newsService.findOneNews(newsId);

        // Assert
        assertNotNull(result);
        assertEquals(newsDTO, result);
    }

    @Test
    public void testFindOneNews_NotFound() {
        // Arrange
        int newsId = 1;
        when(newsRepository.findById(newsId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NewsNotFoundException.class, () -> newsService.findOneNews(newsId));
    }

}