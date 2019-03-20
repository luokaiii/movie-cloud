package cn.luokaiii.movie.model;

import cn.luokaiii.common.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class Movie extends BaseEntity {

    private String name;
    private String cover;
    private List<String> poster;
    private String description;
    private List<String> actor;
    private String director;

    @Id
    @Override
    public String getId() {
        return super.getId();
    }
}
