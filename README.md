# delay-queue
## Java延时队列
### what is delayQueue
延时队列，如字面意思，延时+队列。首先它是个队列，然后它带有延时功能。
### 应用场景
比如我的task需要一分钟后执行，那么这队列中的这个任务，到了一分钟之后可以取出来，否则是取不出来这个任务的。
> 能不能取出来首先它是队列，最上面的先取出来，最上面的没到时间，下面到了时间也是取不出来的。

### 使用
延时队列有三个主要部分
* 实现Delayed接口的消息体
* 消费消息的消费者
* 存放消息的延时队列




### exmaple
#### Message
```java
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

```
#### Consumer
```java
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

```

#### 测试类
```java
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

```
