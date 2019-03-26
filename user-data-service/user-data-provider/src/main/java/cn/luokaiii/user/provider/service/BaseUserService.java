package cn.luokaiii.user.provider.service;

import cn.luokaiii.common.service.AbstractService;
import cn.luokaiii.user.api.model.BaseUser;
import cn.luokaiii.user.provider.repository.BaseUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BaseUserService extends AbstractService<BaseUser, String> {

    private final BaseUserRepository userRepository;

    @Autowired
    public BaseUserService(BaseUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected MongoRepository<BaseUser, String> getRepository() {
        return this.userRepository;
    }

    public Optional<BaseUser> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<BaseUser> findByPhone(String phone) {
        return userRepository.findByPhone(phone);
    }
}
