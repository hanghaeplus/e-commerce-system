package kr.hhplus.be.server.domain.user;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository {

    Optional<User> findByUserId(Long userId);

    Optional<User> findByUserName(String userName);

    User saveUser(User user);

}
