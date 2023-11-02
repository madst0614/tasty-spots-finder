package wanted.n.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import wanted.n.domain.QUser;
import wanted.n.domain.User;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserQRepositoryImpl implements UserQRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<User> getAllUsersAgreed() {
        QUser user = QUser.user;
        return queryFactory
                .selectFrom(user)
                .where(user.lunch_served.eq(true))
                .fetch();
    }
}
