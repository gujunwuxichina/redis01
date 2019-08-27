package com.gujun.knowledge;

import com.gujun.spring.config.SpringConfig01;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;

import javax.swing.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @ClassName gu
 * @Description TODO
 * @Author GuJun
 * @Date 2019/8/11 15:35
 * @Version 1.0
 **/
public class HashTest {

    //hash字典，相当于Java中的HashMap；无序字典；
    //值只能是字符串；
    //当hash移除最后一个元素后，该数据结构自动删除，内存释放；
    //hash可以用来存储用户信息，可以对用户的每个字段单独保存，获取时可以根据需要获取部分，避免不必要的流量浪费；
    //缺点：hash结构的存储消耗高于单个字符串；

    //hdel key field1,field2... 删除hash结构中的一些字段；
    //hexists key field;
    //hgetall key   获取该hash结构中的所有键值；
    //hincyby key field increment
    //hincybyfloat key field increment
    //hkeys key 获取所有key;
    //hlen key;
    //hmget key field1,field2... 返回指定键的值；
    //hmset key field1 value1 field2 value2;    设置多个键值对
    //hset key field value  设置一个
    //hsetnx key field value 当hash结构不存在时才设置键值对
    //hvals key 获取所有值
    //tip:如果hash结构很大，对于hgetall hkeys hvals等返回数据结构命令，
    //会造成大量数据读取，会影响性能；

    @Test
    public void test01(){
        ApplicationContext context=new AnnotationConfigApplicationContext(SpringConfig01.class);
        RedisTemplate<String, Map<String,String>> redisTemplate= (RedisTemplate<String,  Map<String,String>>) context.getBean("redisTemplate");
        Map<String,String> map=new HashMap<>();
        map.put("name","gujun");
        map.put("city","wuxi");
        redisTemplate.opsForHash().putAll("m1",map);
        System.out.println(redisTemplate.opsForHash().get("m1","name"));
        redisTemplate.opsForHash().put("m1","age","22");
        System.out.println(redisTemplate.opsForHash().get("m1","age"));
        System.out.println(redisTemplate.opsForHash().hasKey("m1","city"));
        Map<Object, Object> map1=redisTemplate.opsForHash().entries("m1");
        redisTemplate.opsForHash().increment("m1","age",10);
        System.out.println(redisTemplate.opsForHash().get("m1","age"));
        List<Object> values=redisTemplate.opsForHash().values("m1");
        for(Object obj:values){
            System.out.println(obj);
        }
        System.out.println(redisTemplate.opsForHash().putIfAbsent("m1","name","gj"));   //不存在才设置键值对,返回boolean;
        redisTemplate.delete("m1");
    }

    @Test
    public void test02(){
        ApplicationContext context=new AnnotationConfigApplicationContext(SpringConfig01.class);
        RedisTemplate<String, Map<String,String>> redisTemplate= (RedisTemplate<String,  Map<String,String>>) context.getBean("redisTemplate");
        redisTemplate.execute(new SessionCallback<Object>() {
            @Override
            public  Object execute(RedisOperations redisOperations) throws DataAccessException {
                Map<String,String> map=new HashMap<>();
                map.put("name","顾隽");
                map.put("age","22");
                BoundHashOperations<String,String,String> boundHashOps= redisOperations.boundHashOps("gujun");
                boundHashOps.putAll(map);
                boundHashOps.put("city","无锡");
                boundHashOps.increment("age",2);
                Map<String,String> map1=boundHashOps.entries();
                for(Map.Entry<String,String> entry:map1.entrySet()){
                    System.out.println(entry.getKey()+":"+entry.getValue());
                }
                return null;
            }
        });
    }

}
