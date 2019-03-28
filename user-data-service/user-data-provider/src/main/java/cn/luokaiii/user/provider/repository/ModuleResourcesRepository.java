package cn.luokaiii.user.provider.repository;

import cn.luokaiii.user.api.model.ModuleResources;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ModuleResourcesRepository extends MongoRepository<ModuleResources, String> {
}
