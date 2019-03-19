package cn.luokaiii.common.service;

import cn.luokaiii.common.model.BaseEntity;
import com.google.common.collect.Lists;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public abstract class AbstractService<T extends BaseEntity, ID extends Serializable> {
    protected abstract MongoRepository<T,ID> getRepository();

    public Optional<T> findById(ID id){
        return getRepository().findById(id);
    }

    public List<T> findByIds(Iterable<ID> ids){
        return Lists.newArrayList(getRepository().findAllById(ids));
    }

    public List<T> getBySort(Sort sort) {
        return getRepository().findAll(sort);
    }

    public List<T> getByExample(T ex) {
        final Example<T> example = Example.of(ex);
        return getRepository().findAll(example);
    }

    public void deleteById(ID id) {
        getRepository().deleteById(id);
    }

    public Page<T> getByPage(Pageable pageable) {
        return getRepository().findAll(pageable);
    }

    public Page<T> getByPage(Example<T> example, Pageable pageable) {
        return getRepository().findAll(example, pageable);
    }

    public T save(T model) {
        LocalDateTime now = LocalDateTime.now();
        if (model.getId() == null) {
            model.setCreateTime(now);
        }
        model.setUpdateTime(now);
        return getRepository().save(model);
    }
}
