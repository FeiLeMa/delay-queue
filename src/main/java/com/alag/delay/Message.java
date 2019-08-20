package com.alag.delay;

import lombok.ToString;

import java.util.Date;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @program: delay-queue
 * @description: 显现Delayed接口的消息体用于存放在延时队列
 * @author: Alag
 * @create: 2019-08-18 10:38
 * @email: alag256@aliyun.com
 **/
@ToString(exclude = "excuteTime")
public class Message implements Delayed {
    private Integer id;
    private String msgBody;
    // 重要属性，需要用它来定义这条消息的延时时间
    private long expireTime;
    private Date editTime;
    private long excuteTime;

    public Integer getId() {
        return id;
    }

    public String getMsgBody() {
        return msgBody;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public Date getEditTime() {
        return editTime;
    }

    public Message(Integer id, String msgBody, long expireTime, Date editTime) {
        this.id = id;
        this.msgBody = msgBody;
        this.expireTime = expireTime;
        this.editTime = editTime;
        excuteTime = this.expireTime * 1000 + System.currentTimeMillis();
    }

    //延时就是靠这个方法判断，返回负数，就可以取出。
    public long getDelay(TimeUnit unit) {
        return unit.convert(this.excuteTime - System.currentTimeMillis() , TimeUnit.MILLISECONDS);
    }

    public int compareTo(Delayed o) {
        Message msg = (Message)o;
        return this.editTime.compareTo(msg.editTime);
    }
}
