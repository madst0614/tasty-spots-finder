package wanted.n.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wanted.n.domain.SggLatLon;

@Repository
public interface SggLatLonRepository extends JpaRepository<SggLatLon, Long> {
}