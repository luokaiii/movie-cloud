package cn.luokaiii.common.service;

import cn.luokaiii.common.model.BaseEntity;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class BaseService<T extends BaseEntity, ID extends Serializable> {

    @Autowired
    protected MongoRepository<T, ID> repository;

    public Optional<T> findById(ID id) {
        return repository.findById(id);
    }

    public List<T> findByIds(Iterable<ID> ids) {
        return Lists.newArrayList(repository.findAllById(ids));
    }

    public List<T> getBySort(Sort sort) {
        return repository.findAll(sort);
    }

    public List<T> getByExample(T ex) {
        final Example<T> example = Example.of(ex);
        return repository.findAll(example);
    }

    public void deleteById(ID id) {
        repository.deleteById(id);
    }

    public Page<T> getByPage(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<T> getByPage(Example<T> example, Pageable pageable) {
        return repository.findAll(example, pageable);
    }

    public T save(T model) {
        LocalDateTime now = LocalDateTime.now();
        if (model.getId() == null) {
            model.setCreateTime(now);
        }
        model.setUpdateTime(now);
        return repository.save(model);
    }
}
