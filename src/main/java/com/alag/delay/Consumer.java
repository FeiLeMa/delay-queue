package com.alag.delay;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.DelayQueue;

/**
 * @program: delay-queue
 * @description:
 * @author: Alag
 * @create: 2019-08-18 10:51
 * @email: alag256@aliyun.com
 **/
public class Consumer implements Runnable {
    private DelayQueue<Message> queue;

    public Consumer(DelayQueue<Message> queue) {
        this.queue = queue;
    }

    public void run() {
        while (true) {
            try {
                Message take = queue.take();
                System.out.println("消息取出：-------->"+take);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
