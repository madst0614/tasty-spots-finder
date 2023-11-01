package wanted.n.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wanted.n.domain.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
