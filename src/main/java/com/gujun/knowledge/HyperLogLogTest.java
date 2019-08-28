package com.gujun.knowledge;

import com.gujun.spring.config.SpringConfig01;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

public class HyperLogLogTest {

    //基数：
    //是Redis的高级数据结构，提供不精确（也不是非常不精确，标准误差不超1%）的去重功能；
    //指令：pfadd,pfcount,即增加计数，获取计数；如下：
    //pfadd key element 将指定元素添加到HyperLogLog中；
    //pfcount key 返回HyperLogLog的基数值；
    //pfmerge desKey key1 key2 ..   合并多个HyperLogLog，并保存到desKey中；

    @Test
    public void test01(){
        ApplicationContext context=new AnnotationConfigApplicationContext(SpringConfig01.class);
        RedisTemplate<String,String> redisTemplate=context.getBean(RedisTemplate.class);
        redisTemplate.opsForHyperLogLog().add("h1","gj","gujun","java");
        System.out.println(redisTemplate.opsForHyperLogLog().size("h1"));
        redisTemplate.opsForHyperLogLog().add("h2","gujun","gj","java","python");
        redisTemplate.opsForHyperLogLog().union("h3","h1","h2");
        System.out.println(redisTemplate.opsForHyperLogLog().size("h3"));
    }

}
