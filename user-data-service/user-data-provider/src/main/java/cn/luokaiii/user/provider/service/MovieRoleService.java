package cn.luokaiii.user.provider.service;

import cn.luokaiii.common.service.AbstractService;
import cn.luokaiii.user.api.model.MovieRole;
import cn.luokaiii.user.provider.repository.MovieRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieRoleService extends AbstractService<MovieRole, String> {

    private final MovieRoleRepository movieRoleRepository;

    @Autowired
    public MovieRoleService(MovieRoleRepository movieRoleRepository) {
        this.movieRoleRepository = movieRoleRepository;
    }

    @Override
    protected MongoRepository<MovieRole, String> getRepository() {
        return movieRoleRepository;
    }

    public List<MovieRole> findByCodeIn(String[] codes) {
        return movieRoleRepository.findByRoleCodeIn(codes);
    }
}
