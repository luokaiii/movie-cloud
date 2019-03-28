package cn.luokaiii.user.api.model;

import cn.luokaiii.common.model.BaseEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

@Document(collection = "module_resources")
public class ModuleResources extends BaseEntity implements Serializable {

    private String moduleName; // 名称

    private String moduleCode; // 编码

    private String modulePath; // 路径

    private String parentId; // 父模块

    private String moduleIcon; // 图标

    private String httpMethod; // 请求方式

    private Boolean isOperating; // 是否可操作

    private Integer sort; // 排序

    private Integer active; // 资源是否可用

    private String systemId; // 所属系统的ID

    private String projectName; // 所属系统的名称

    private List<ModuleResources> subModules; // 子模块，即所有parentId为当前ID的模块

    @Id
    @Override
    public String getId() {
        return super.getId();
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getModulePath() {
        return modulePath;
    }

    public void setModulePath(String modulePath) {
        this.modulePath = modulePath;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getModuleIcon() {
        return moduleIcon;
    }

    public void setModuleIcon(String moduleIcon) {
        this.moduleIcon = moduleIcon;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public Boolean getOperating() {
        return isOperating;
    }

    public void setOperating(Boolean operating) {
        isOperating = operating;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public List<ModuleResources> getSubModules() {
        return subModules;
    }

    public void setSubModules(List<ModuleResources> subModules) {
        this.subModules = subModules;
    }
}
