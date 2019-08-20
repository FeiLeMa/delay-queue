package com.alag.delay;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @program: delay-queue
 * @description:
 * @author: Alag
 * @create: 2019-08-18 10:55
 * @email: alag256@aliyun.com
 **/
public class DelayTest {

    public static void main(String[] args) {
        DelayQueue<Message> messageDelayQueue = new DelayQueue<Message>();
        Message msg1 = new Message(1, "msg1", 3,DateTimeUtil.addTime(-10));
        Message msg2 = new Message(2, "msg2", 3,DateTimeUtil.addTime(-20));
        Message msg3 = new Message(3, "msg3", 3,DateTimeUtil.addTime(-100));
        Message msg4 = new Message(4, "msg4", 3,DateTimeUtil.addTime(-40));
        Message msg5 = new Message(5, "msg5", 3, DateTimeUtil.addTime(-50));
        messageDelayQueue.offer(msg1);
        messageDelayQueue.offer(msg2);
        messageDelayQueue.offer(msg3);
        messageDelayQueue.offer(msg4);
        messageDelayQueue.offer(msg5);

        new Thread(new Consumer(messageDelayQueue)).start();
    }
}
