package cn.luokaiii.user.dao;

import cn.luokaiii.user.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserDao extends MongoRepository<User,String> {

    Optional<User> findByUsername(String username);
}
