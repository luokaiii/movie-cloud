package cn.luokaiii.user.provider.repository;

import cn.luokaiii.user.api.model.MovieClientDetails;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MovieClientDetailsRepository extends MongoRepository<MovieClientDetails, String> {

}
