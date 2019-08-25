package com.gujun.knowledge;

import com.gujun.spring.config.SpringConfig01;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;

/**
 * @ClassName gu
 * @Description TODO
 * @Author GuJun
 * @Date 2019/8/10 7:46
 * @Version 1.0
 **/
public class StringTest {

    //Redis是一种基于内存的数据库，提供了一定的持久化能力；键值对数据库；
    //字符串数据类型：
    //内部结构是一个字符数组；是动态字符串，是可以修改的，内部结构类似Java的ArrayList；
    //采用预分配冗余空间的方式来减少内存的频繁分配；
    //当前字符串分配空间capacity一般高于实际字符串长度len;
    //基本操作：
    //set key value,get key,exists key,del key;
    //mget key1 key2 返回一个列表，mset key1 value1 key2 value2
    //可以对key设置过期时间，到时间会自动删除；(该自动删除非常复杂)？H
    //expire key  秒数，setex key 秒数 value;
    //setnx key value 若key不存在才执行创建；
    //若value是整数，还可以对其进行自增操作，自增范围在signed long之间（有符号long）,超出会报错；
    //incr key 增1，incrby key number(增加或减少多少，负数表示减少)；
    //strlen key 返回值字符串长度，getset key value 修改值，返回旧值；
    //getrange key start end 获取子串；append key value 将value添加到末尾；
    //decr key 减一，decrby num 减去多少；若一开始set key value 就设置浮点数，则incr/incrby/decr/decrby命令会失效；
    //incrbyfloat key num 加上浮点数

    //字符串由多个字节（字符）组成，每个字节8个bit;
    //可以将一个字符串看成很多bit的组合，便是bitmap(位图)数据结构；

    @Test
    public void test01(){
        ApplicationContext context=new AnnotationConfigApplicationContext(SpringConfig01.class);
        RedisTemplate<String,String> redisTemplate=context.getBean(RedisTemplate.class);
        redisTemplate.opsForValue().set("k1","gujun");
        System.out.println(redisTemplate.opsForValue().get("k1"));
        System.out.println(redisTemplate.opsForValue().size("k1"));
        System.out.println(redisTemplate.opsForValue().get("k1",1,2)); //求子串
        System.out.println(redisTemplate.opsForValue().getAndSet("k1","gj"));//设新值返回旧值
        redisTemplate.opsForValue().append("k1","java");    //添加到末尾
        System.out.println(redisTemplate.opsForValue().get(("k1")));
        redisTemplate.delete("k1");
    }

    @Test
    public void test02(){
        ApplicationContext context=new AnnotationConfigApplicationContext(SpringConfig01.class);
        RedisTemplate<String,String> redisTemplate=context.getBean(RedisTemplate.class);
        redisTemplate.execute(new SessionCallback<Object>() {
            @Override
            public <k,v> Object execute(RedisOperations<k,v> redisOperations) throws DataAccessException {
                redisOperations.boundValueOps((k) "name").set((v) "顾隽");
                System.out.println(redisOperations.boundValueOps((k) "name").get());
//                redisOperations.delete((k) "name");
                System.out.println(redisOperations.boundValueOps((k) "name").size());
                redisOperations.boundValueOps((k) "name").getAndSet((v) "gujun");
                System.out.println(redisOperations.boundValueOps((k) "name").size());
                System.out.println(redisOperations.boundValueOps((k) "name").get());
                System.out.println(redisOperations.boundValueOps((k) "name").get(2,4));
                int length=redisOperations.boundValueOps((k) "name").append("genius"); //返回新字符串长度；
                System.out.println(length);
                System.out.println(redisOperations.boundValueOps((k) "name").get());
                return null;
            }
        });
    }

}
