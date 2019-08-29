package com.gujun.commonUSE;

import com.gujun.spring.config.SpringConfig01;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;

public class Pipelined01 {

    //pipelined(流水线，管道)：
    //Redis在事务中添加了队列，来批量执行任务，但是这用到了multi...exec...命令，增加了开销；
    //有时希望在没有任何附加条件下实现批量执行命令，从而提高性能————这就用到了pipelined；
    //pipelined本质：
    //pipelined并不是服务器的什么特性，而是客户端(实际由服务端的程序实现)通过改变了读写顺序带来的性能提升；

    @Test
    public void test01(){
        ApplicationContext context=new AnnotationConfigApplicationContext(SpringConfig01.class);
        RedisTemplate<String,String> redisTemplate=context.getBean(RedisTemplate.class);
        long start=System.currentTimeMillis();
        redisTemplate.executePipelined(new SessionCallback<Object>() {
            @Override
            public <K, V> Object execute(RedisOperations<K, V> ops) throws DataAccessException {
                for(int i=0;i<10000;i++){
                    ops.boundValueOps((K) ("k"+i)).set((V) (""+i));
                }
                return null;
            }
        });
        long end=System.currentTimeMillis();
        System.out.println(end-start);
    }


}
