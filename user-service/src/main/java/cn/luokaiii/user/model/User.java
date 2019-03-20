package cn.luokaiii.user.model;

import cn.luokaiii.common.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;

import java.util.Collections;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {

    private String username; // 用户名

    private String password; // 密码

    private String nickname; // 昵称

    private String profile; // 头像

    private List<String> roles = Collections.singletonList("bronze"); // 权限,默认为青铜

    private Boolean isEnabled = true; // 账号是否可用,默认可用

    @Id
    @Override
    public String getId() {
        return super.getId();
    }
}
