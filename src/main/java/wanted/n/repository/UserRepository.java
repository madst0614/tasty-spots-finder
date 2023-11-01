package wanted.n.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wanted.n.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
