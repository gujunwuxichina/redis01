package com.gujun.knowledge;

import com.gujun.spring.config.SpringConfig01;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @ClassName gu
 * @Description TODO
 * @Author GuJun
 * @Date 2019/8/10 9:10
 * @Version 1.0
 **/
public class ListTest {

    //列表
    //相当于Java中的LinkedList;插入删除较快，但索引定位很慢；
    //列表中每个元素都使用双向指针顺序，同时支持向前向后遍历；

    //因为是双向列表，命令分为左操作和右操作：
    //r/lpush key v1,v2,..., llen key 求长度；
    //lindex key index 根据索引获取从0开始；index可以为负数，-1表示最后一个元素；
    //l/rpop key 删除左/右第一个节点，并返回；
    //lrange key start end,获取链表list从start到end的子链；
    //lset key index node; ltrim key start end,修剪链表，从保留start到end；

    //以上这些操纵命令，都是进程不安全的；
    //为此Redis提供了阻塞式命令，运行时会给链表加锁，以保证安全性；
    //blpop key timeout,移出并获取链表第一个元素，若没有元素会阻塞列表直到超时或发现可弹出元素为止；
    //brpop key timeout,移出并获取链表最后一个元素，若没有元素会阻塞列表直到超时或发现可弹出元素为止；
    //实际使用中阻塞式命令使用不多；

    //当列表弹出最后一个元首后，该数据结构自动删除，内存回收；
    //队列：
    //先进先出的数据结构，用于消息队列和异步处理逻辑，确保元素的访问顺序性；

    @Test
    public void test01(){
        ApplicationContext context=new AnnotationConfigApplicationContext(SpringConfig01.class);
        RedisTemplate<String, List<String>> redisTemplate=context.getBean(RedisTemplate.class);
        redisTemplate.execute(new SessionCallback<Object>() {
            @Override
            public  Object execute(RedisOperations redisOperations) throws DataAccessException {
                BoundListOperations boundListOps=redisOperations.boundListOps("liskey1");
                boundListOps.leftPushAll(new String[]{"java", "gj", "redis"});
                boundListOps.leftPush("javascript");
                System.out.println(boundListOps.index(-1));
                System.out.println(boundListOps.range(0,boundListOps.size()));
                System.out.println(boundListOps.leftPop());
                System.out.println(boundListOps.range(0,boundListOps.size()));
                boundListOps.trim(0,1);
                System.out.println(boundListOps.range(0,boundListOps.size()));
                return null;
            }
        });
    }

}
