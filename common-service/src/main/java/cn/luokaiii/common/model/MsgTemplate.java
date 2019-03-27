package cn.luokaiii.common.model;

import java.io.Serializable;

public class MsgTemplate implements Serializable {

    private String phone;

    private String text;

    private String type;

    public MsgTemplate(String phone, String text, String type) {
        this.phone = phone;
        this.text = text;
        this.type = type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
