package com.gujun.spring;

import com.gujun.entity.Person;
import com.gujun.spring.config.SpringConfig01;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @ClassName gu
 * @Description TODO
 * @Author GuJun
 * @Date 2019/8/8 22:25
 * @Version 1.0
 **/
public class Test01 {

    //Redis只能提供基于字符串的操作，而Java中主要以对象为主；
    //Spring对此提供了支持，提供了序列化的设计和一些序列化类，
    //通过它们可以将对象序列化为redis能操作的数据，读取时反序列化为Java对象；
    //所以一般使用Spring的RedisTemplate来操作redis;

    //需要配置Spring所提供的连接工厂；
    //有了RedisConnectionFactory，就可以使用RedisTemplate;
    //RedisTemplate的两个属性：
    //1.keySerializer，键序列号器；(通常情况下key采用StringRedisSerializer)
    //2.valueSerializer，值序列号器；

    @Test
    public void test01(){
        ApplicationContext context=new AnnotationConfigApplicationContext(SpringConfig01.class);
        RedisTemplate<String,Object> redisTemplate=context.getBean(RedisTemplate.class);
        Person person=new Person("小李",22);
        redisTemplate.opsForValue().set("p1",person);
        Person p= (Person) redisTemplate.opsForValue().get("p1");
        System.out.println(p);
    }

}
