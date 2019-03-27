package cn.luokaiii.common.utils;

public class UUID {

    public static String uuid32() {
        return java.util.UUID.randomUUID().toString().replace("-", "");
    }

}
