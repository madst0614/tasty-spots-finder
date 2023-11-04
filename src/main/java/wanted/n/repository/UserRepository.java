package wanted.n.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wanted.n.domain.User;
import java.util.Optional;
import org.springframework.context.annotation.Primary;
import wanted.n.domain.User;

@Primary
public interface UserRepository extends JpaRepository<User, Long>, UserQRepository {
    Optional<User> findByEmail(String email);
}
