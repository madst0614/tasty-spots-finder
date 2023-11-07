package wanted.n.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import wanted.n.domain.User;
import java.util.Optional;

@Primary
public interface UserRepository extends JpaRepository<User, Long>  {
    Optional<User> findByEmail(String email);
}
