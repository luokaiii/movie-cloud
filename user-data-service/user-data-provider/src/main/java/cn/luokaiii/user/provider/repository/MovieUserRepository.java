package cn.luokaiii.user.provider.repository;

import cn.luokaiii.user.api.model.MovieUser;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MovieUserRepository extends MongoRepository<MovieUser, String> {

    Optional<MovieUser> findByUsername(String username);

    Optional<MovieUser> findByPhone(String phone);
}
