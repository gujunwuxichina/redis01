package com.gujun.commonUSE;

import com.gujun.spring.config.SpringConfig01;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TimeOut {

    //超时命令：
    //persist key,持久化key,取消超时时间；
    //ttl key 查看超时时间，-1表示没有超时时间，-2表示不存在key或key已经超时；
    //enpire key seconds 设置超时时间，单位秒；
    //expireat key timestamp 设置超时s时间戳；
    //H

    @Test
    public void test01(){
        ApplicationContext context=new AnnotationConfigApplicationContext(SpringConfig01.class);
        RedisTemplate<String,String> redisTemplate=context.getBean(RedisTemplate.class);
        redisTemplate.execute(new SessionCallback<Object>() {
            @Override
            public  Object execute(RedisOperations ops) throws DataAccessException {
                ops.boundValueOps("k1").set("gj");
                System.out.println(ops.getExpire("k1"));    //-1
                ops.expire("k1",1000, TimeUnit.SECONDS);
                System.out.println(ops.getExpire("k1"));    //1000
                ops.persist("k1");
                System.out.println(ops.getExpire("k1"));    //-1
                ops.expireAt("k1",new Date(System.currentTimeMillis()+100000000000L));
                return null;
            }
        });
    }

}
