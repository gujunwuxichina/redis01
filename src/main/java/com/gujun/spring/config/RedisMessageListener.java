package com.gujun.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

//消息监听类
@Component(value = "redisMessageListener")
public class RedisMessageListener implements MessageListener {

    @Autowired
    private StringRedisSerializer stringRedisSerializer;

    @Override
    public void onMessage(Message message, byte[] bytes) {
        String channel=stringRedisSerializer.deserialize(message.getChannel());
        String msg=stringRedisSerializer.deserialize(message.getBody());
        System.out.println("渠道:"+channel+",消息:"+msg);
    }

}
