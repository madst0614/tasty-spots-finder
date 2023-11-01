package wanted.n.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wanted.n.domain.Restaurant;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant,String> {

}
