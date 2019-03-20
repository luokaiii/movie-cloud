package cn.luokaiii.movie.model;

import cn.luokaiii.common.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "movie")
@EqualsAndHashCode(callSuper = true)
@Data
public class Movie extends BaseEntity {

    private String name; // 名称
    private String alias; // 别名
    private String cover; // 封面
    private List<String> poster; // 海报
    private String description; // 剧情简介
    private List<String> actor; // 主演
    private List<String> director; // 导演
    private List<String> writer; // 编剧

    @Id
    @Override
    public String getId() {
        return super.getId();
    }
}
