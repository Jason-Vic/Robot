package vic.bean;

import java.lang.reflect.Type;
import java.util.Date;

/**
 * Created by Vic on 2016/8/4.
 */
public class ChatMessage {
    private String name;
    private String msg;
    private Type type;
    private Date date;

    public ChatMessage() {
    }

    public ChatMessage(String msg, Type type, Date date) {
        this.msg = msg;
        this.type = type;
        this.date = date;
    }

    public enum Type {
        INCOMING, OUTCOMING
    }


    public Type getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getMsg() {
        return msg;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
