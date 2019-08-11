package com.gujun.knowledge;

import com.gujun.spring.config.SpringConfig01;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName gu
 * @Description TODO
 * @Author GuJun
 * @Date 2019/8/11 21:19
 * @Version 1.0
 **/
public class SetTest {

    //set集合：
    //相当于Java中的HashSet，内部键值对（内部实现是个特殊的字典，value是一个null）是无序的，唯一的；
    //集合中最后一个元素被移除后，该数据结构也被字段删除，内存释放；

    //sadd key v1 v2 ... ;
    //scard key 返回指定set的成员数；
    //sdiff key1 key2 找出两个集合差值，若是只有一个key则返回该key的所有值；
    //sdiffstore des key1 key2  先找出key1和key2的差值，最后将其保存到des集合中；
    //sinter key1 key2 求交集，若是只有一个key则返回该key的所有值；
    //sinterstore des key1 key2 先找出交集，将其保存到des集合中；
    //sismember key member 判断是否是key集合的成员；
    //smember key 返回集合所有成员
    //smove src des member 将成员member从集合src移到集合des
    //spop key 随机弹出一个成员；
    //srandmember key [count] 随机返回集合中一个或多个成员，count为负数求绝对值，不写默认为1，超过集合总数返回全部；
    //srem key m1 m2 移除集合的成员
    //sunion key1 key2 求并集，若是只有一个key则返回该key的所有值；
    //sunion des key1 key2 先求并集，保存到集合des中；

    @Test
    public void test01(){
        ApplicationContext context=new AnnotationConfigApplicationContext(SpringConfig01.class);
        RedisTemplate<String, Set<String>> redisTemplate=context.getBean(RedisTemplate.class);
        redisTemplate.execute(new SessionCallback<Object>() {
            @Override
            public  Object execute(RedisOperations redisOperations) throws DataAccessException {
                BoundSetOperations<String,String> boundSetOps1=redisOperations.boundSetOps("k1");
                BoundSetOperations<String,String> boundSetOps2=redisOperations.boundSetOps("k2");
                boundSetOps1.add(new String[]{"gj","gujun","java"});
                boundSetOps2.add(new String[]{"gujun","javascript","java"});
                System.out.println(boundSetOps1.size());
                Set<String> union=boundSetOps1.union("k2"); //求并集
                System.out.println(union);
                boundSetOps1.intersectAndStore("k2","k3");  //求交集并存入集合k3
                System.out.println(boundSetOps1.randomMember());    //随机获取一个
                return null;
            }
        });
    }

}
