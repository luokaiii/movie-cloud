package cn.luokaiii.user.provider.service;

import cn.luokaiii.common.service.AbstractService;
import cn.luokaiii.user.api.model.MovieClientDetails;
import cn.luokaiii.user.provider.repository.MovieClientDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

@Service
public class MovieClientDetailsService extends AbstractService<MovieClientDetails, String> {

    private final MovieClientDetailsRepository movieClientDetailsRepository;

    @Autowired
    public MovieClientDetailsService(MovieClientDetailsRepository movieClientDetailsRepository) {
        this.movieClientDetailsRepository = movieClientDetailsRepository;
    }

    @Override
    protected MongoRepository<MovieClientDetails, String> getRepository() {
        return movieClientDetailsRepository;
    }

}
