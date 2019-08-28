package com.gujun.knowledge;

import com.gujun.spring.config.SpringConfig01;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName gu
 * @Description TODO
 * @Author GuJun
 * @Date 2019/8/12 9:33
 * @Version 1.0
 **/
public class ZSetTest {

    //zset有序列表，类似于Java的SorterSet和HashMap的结合体；
    //首先它是一个set，保证了value的唯一性，其次它给每个value一个score（浮点数）,代表该value的排序权重；
    //内部实现是使用的一种叫着跳跃列表的数据结构；

    //也是基于hash的存储结构，所以添加删除查找性能还行；

    //同样最后一个成员被移除后，数据结构自动删除，内存释放；
    //每个元素都是唯一的，但是对于不同元素，它们的分数可以相同；
    //元素也是String类型，也是一种基于hash的存储结构；
    //不仅可以对分数进行排序，在满足一定条件下，也可以对值进行排序；

    //zadd key score1 value1 score2 value2 ....;
    //zcard key 获取成员数；
    //zcount key min max 根据分数返回成员列表数；
    //zincrby key amount value 跟有序集合值为value的成员增加amount
    //zinterstore deskey numkeys key1 key2... 求多个有序集合的交集，存入deskey中，numkeys是一个整数，表示多少个有序集合；
    //zlexcount key min max 求有序集合key成员值在min和max的范围，
    //zrange key start stop [withscores],按照分数范围返回成员，如果加入withscores选项则l连同分数一起返回；
    //HH

    //Spring对有序集合的操作：
    //Spring对Redis有序集合的元素的值和分数的范围进行了封装；
    //TypedTuple接口，是ZSetOperations接口的内部接口；定义了getValue(),getScore()两个方法；
    //Spring提供了默认的实现类DefaultTypedTuple，可以通过该类实例来获取对应的分数和值；
    //Spring不仅对有序集合元素进行了封装，还对范围进行了封装；

    @Test
    public void test01(){
        ApplicationContext context=new AnnotationConfigApplicationContext(SpringConfig01.class);
        RedisTemplate<String,String> redisTemplate=context.getBean(RedisTemplate.class);
        Set<ZSetOperations.TypedTuple<String>> set=new HashSet<>();
        set.add(new DefaultTypedTuple<String>("gujun",100.0));
        set.add(new DefaultTypedTuple<String>("li",80.5));
        set.add(new DefaultTypedTuple<String>("zhang",92.0));
        set.add(new DefaultTypedTuple<String>("wang",83.0));
        set.add(new DefaultTypedTuple<String>("zhao",91.5));
        set.add(new DefaultTypedTuple<String>("mao",82.0));
        set.add(new DefaultTypedTuple<String>("gao",79.0));
        System.out.println(redisTemplate.opsForZSet().zCard("z1")); //统计总数
        redisTemplate.opsForZSet().add("z1",set);
        System.out.println(redisTemplate.opsForZSet().count("z1",90.0,100));    //统计指定范围内的个数
        Set<String> values=redisTemplate.opsForZSet().range("z1",0,3);
        values.forEach(s -> System.out.println(s));
        Set<ZSetOperations.TypedTuple<String>> valuesAndScores=redisTemplate.opsForZSet().rangeWithScores("z1",0,3);
        valuesAndScores.forEach(t->{
            System.out.println(t.getValue()+"-"+t.getScore());
        });
        System.out.println("=======================================");
        RedisZSetCommands.Range range= RedisZSetCommands.Range.range();
        range.gte("gao");
        range.lt("zhang");
        Set<String> rangeSet=redisTemplate.opsForZSet().rangeByLex("z1",range); //?
        System.out.println(rangeSet.size());
        rangeSet.forEach(s -> System.out.println(s));
        //HH
    }

    @Test
    public void test02(){

    }

}
