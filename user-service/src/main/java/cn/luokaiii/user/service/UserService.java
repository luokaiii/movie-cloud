package cn.luokaiii.user.service;

import cn.luokaiii.user.dao.UserDao;
import cn.luokaiii.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService extends AbstractService<User,String>{
    private final UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    protected MongoRepository<User, String> getRepository() {
        return userDao;
    }
}
