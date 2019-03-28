package cn.luokaiii.user.provider.service;

import cn.luokaiii.common.service.AbstractService;
import cn.luokaiii.user.api.model.ModuleResources;
import cn.luokaiii.user.provider.repository.ModuleResourcesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

@Service
public class ModuleResourcesService extends AbstractService<ModuleResources, String> {

    private final ModuleResourcesRepository moduleResourcesRepository;

    @Autowired
    public ModuleResourcesService(ModuleResourcesRepository moduleResourcesRepository) {
        this.moduleResourcesRepository = moduleResourcesRepository;
    }

    @Override
    protected MongoRepository<ModuleResources, String> getRepository() {
        return moduleResourcesRepository;
    }

}
