package wanted.n.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.n.domain.User;
import wanted.n.exception.CustomException;
import wanted.n.exception.ErrorCode;
import wanted.n.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final RedisService redisService;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // redis에 캐싱된 데이터가 있는지 확인
        User user = redisService.findUserByEmail(email);

        // 없다면 DB에서 가져와서 redis에 저장 후 사용
        if(user == null){
            user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

            redisService.saveUser(user);
        }


        List<GrantedAuthority> authorities = user.getUserRole().getAuthorities();

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getNickname())
                .password(user.getPassword())
                .authorities(authorities)
                .build();
    }
}
