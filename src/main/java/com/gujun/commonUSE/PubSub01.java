package com.gujun.commonUSE;

import com.gujun.spring.config.SpringConfig01;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

public class PubSub01 {

    //发布订阅：
    //由于Redis5新增了Stream数据结构，给Redis带来了持久化消息队列；所以PubSub（无持久化能力）可以消失了；
    @Test
    public void test01() throws InterruptedException {
        ApplicationContext context=new AnnotationConfigApplicationContext(SpringConfig01.class);
        RedisTemplate<String,String> redisTemplate=context.getBean(RedisTemplate.class);
        Thread.sleep(1000); //测试时，这样确保发送前已有订阅者订阅成功；？
        redisTemplate.convertAndSend("msg01","hi gujun22");
    }

}
