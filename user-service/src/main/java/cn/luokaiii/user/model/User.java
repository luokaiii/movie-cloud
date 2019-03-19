package cn.luokaiii.user.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity{

    private String username;

    private String password;

    private String nickname;

    private String profile;

    private String vipId;

}
