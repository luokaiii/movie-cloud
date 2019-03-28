package cn.luokaiii.user.api.model;

import cn.luokaiii.common.model.BaseEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * 系统一级菜单
 */
@Document(collection = "movie_system")
public class MovieSystem extends BaseEntity implements Serializable {

    private String systemName; // 系统名称(中文)

    private String projectName; // 项目名称(别名)

    private Boolean active; // 是否可用

    private Integer sort; // 顺序

    @Id
    @Override
    public String getId() {
        return super.getId();
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
