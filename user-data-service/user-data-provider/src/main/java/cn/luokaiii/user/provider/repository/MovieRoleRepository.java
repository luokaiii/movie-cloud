package cn.luokaiii.user.provider.repository;

import cn.luokaiii.user.api.model.MovieRole;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MovieRoleRepository extends MongoRepository<MovieRole, String> {

    List<MovieRole> findByRoleCodeIn(String[] codes);
}
