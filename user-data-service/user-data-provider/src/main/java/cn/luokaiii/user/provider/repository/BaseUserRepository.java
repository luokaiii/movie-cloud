package cn.luokaiii.user.provider.repository;

import cn.luokaiii.user.api.model.BaseUser;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface BaseUserRepository extends MongoRepository<BaseUser, String> {

    Optional<BaseUser> findByUsername(String username);

    Optional<BaseUser> findByPhone(String phone);
}
