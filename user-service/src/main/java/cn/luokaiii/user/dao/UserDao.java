package cn.luokaiii.user.dao;

import cn.luokaiii.user.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserDao extends MongoRepository<User,String> {

}
