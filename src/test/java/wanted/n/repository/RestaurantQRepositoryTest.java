package wanted.n.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import wanted.n.domain.Restaurant;
import wanted.n.dto.RestaurantSearchRequestDTO;
import wanted.n.dto.RestaurantSearchResponseDTO;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.*;
@TestConfiguration
class config {
    @PersistenceContext
    private EntityManager entityManager;
    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }
}
@DataJpaTest
@Import(config.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RestaurantQRepositoryTest {

    @Autowired private RestaurantRepository restaurantRepository;
    @Autowired private RestaurantQRepositoryImpl restaurantQRepositoryImpl;

    private RestaurantSearchRequestDTO restaurantSearchRequestDTO;
    @BeforeEach
    void before(){
        restaurantRepository.saveAll(LongStream
                .range(1, 15)
                .mapToObj(l -> Restaurant.builder()
                        .id("test" + l)
                        .refinedLatitude((double)l)
                        .refinedLongitude((double)l)
                        .rate((double) (l%5))
                        .build())
                .collect(Collectors.toList())
        );
        restaurantSearchRequestDTO = RestaurantSearchRequestDTO.builder()
                .lat(1.0)
                .lon(1.0)
                .range(1000.0)
                .build();
    }

    @Test
    void 맛집_리스트_조회_페이징(){
        Pageable pageable = PageRequest.of(0,3);
        Page<RestaurantSearchResponseDTO> resultList = restaurantRepository.findBySearch(restaurantSearchRequestDTO, pageable);

        assertThat(resultList.getTotalPages()).isEqualTo(3);
        assertThat(resultList.getTotalElements()).isEqualTo(7);
        assertThat(resultList.getSize()).isEqualTo(3);
        assertThat(resultList.getNumber()).isEqualTo(0);

        pageable = PageRequest.of(1,3);
        resultList = restaurantRepository.findBySearch(restaurantSearchRequestDTO, pageable);

        assertThat(resultList.getTotalPages()).isEqualTo(3);
        assertThat(resultList.getTotalElements()).isEqualTo(7);
        assertThat(resultList.getSize()).isEqualTo(3);
        assertThat(resultList.getNumber()).isEqualTo(1);
    }

    @Test
    void 맛집_리스트_조회_평점순(){
        restaurantSearchRequestDTO = RestaurantSearchRequestDTO.builder()
                .lat(1.0)
                .lon(1.0)
                .range(1000.0)
                .orderBy("rate")
                .build();
        Pageable pageable = PageRequest.of(0,7);
        Page<RestaurantSearchResponseDTO> resultList = restaurantRepository.findBySearch(restaurantSearchRequestDTO, pageable);

        IntStream.range(0,6).forEach(i -> assertThat(resultList.getContent().get(i).getRate()).isGreaterThanOrEqualTo(resultList.getContent().get(i+1).getRate()));
    }

    @Test
    void 맛집_리스트_조회_거리순(){
        Pageable pageable = PageRequest.of(0,7);
        Page<RestaurantSearchResponseDTO> resultList = restaurantRepository.findBySearch(restaurantSearchRequestDTO, pageable);

        IntStream.range(0,6).forEach(i -> assertThat(resultList.getContent().get(i).getDistance()).isLessThanOrEqualTo(resultList.getContent().get(i+1).getDistance()));
    }
}