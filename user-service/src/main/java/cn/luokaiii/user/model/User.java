package cn.luokaiii.user.model;

import cn.luokaiii.common.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;

@Data
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {

    private String username;

    private String password;

    private String nickname;

    private String profile;

    private String vipId;

    @Id
    @Override
    public String getId() {
        return super.getId();
    }
}
