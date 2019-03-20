package cn.luokaiii.movie.service;

import cn.luokaiii.common.service.AbstractService;
import cn.luokaiii.movie.dao.MovieRepository;
import cn.luokaiii.movie.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

@Service
public class MovieService extends AbstractService<Movie, String> {

    private final MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    protected MongoRepository<Movie, String> getRepository() {
        return movieRepository;
    }
}
