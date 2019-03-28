package cn.luokaiii.user.provider.repository;

import cn.luokaiii.user.api.model.MovieRole;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MovieRoleRepository extends MongoRepository<MovieRole, String> {
}
