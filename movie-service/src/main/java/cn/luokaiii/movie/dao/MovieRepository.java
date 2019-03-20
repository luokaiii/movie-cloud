package cn.luokaiii.movie.dao;

import cn.luokaiii.movie.model.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MovieRepository extends MongoRepository<Movie, String> {
}
