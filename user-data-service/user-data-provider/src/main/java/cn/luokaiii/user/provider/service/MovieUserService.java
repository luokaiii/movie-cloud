package cn.luokaiii.user.provider.service;

import cn.luokaiii.common.service.AbstractService;
import cn.luokaiii.user.api.model.MovieUser;
import cn.luokaiii.user.provider.repository.MovieUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MovieUserService extends AbstractService<MovieUser, String> {

    private final MovieUserRepository userRepository;

    @Autowired
    public MovieUserService(MovieUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected MongoRepository<MovieUser, String> getRepository() {
        return this.userRepository;
    }

    public Optional<MovieUser> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<MovieUser> findByPhone(String phone) {
        return userRepository.findByPhone(phone);
    }
}
