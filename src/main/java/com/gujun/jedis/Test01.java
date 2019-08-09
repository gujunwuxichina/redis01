package com.gujun.jedis;

import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @ClassName gu
 * @Description TODO
 * @Author GuJun
 * @Date 2019/8/8 21:59
 * @Version 1.0
 **/
public class Test01 {

    //在Java中简易使用Redis，可以通过jedis;
    @Test
    public void test01(){
        Jedis jedis=new Jedis("127.0.0.1",6379);
//        jedis.auth("gujun");    //密码
        long i=0;
        try{
            long start=System.currentTimeMillis();
            long end;
            while (true){
                end=System.currentTimeMillis();
                if(end-start>=1000){
                    break;
                }
                i++;
                jedis.set("name"+i,i+"");
            }
        }finally {
            jedis.close();
        }
        System.out.println("一秒内redis操作了"+i+"次");
        //此处只是一条条命令发送给Redis去执行，如果使用流水线技术会快很多；
        //且此处只是一个简单的连接，应该使用连接池去管理连接；
    }

    @Test
    public void test02(){
        JedisPoolConfig poolConfig=new JedisPoolConfig();
        poolConfig.setMaxIdle(50);  //最大空闲数
        poolConfig.setMaxTotal(100);    //最大连接数
        poolConfig.setMaxWaitMillis(20000);
        JedisPool pool=new JedisPool(poolConfig,"127.0.0.1",6379);     //通过配置创建连接池
        Jedis jedis=pool.getResource();
        jedis.auth("gujun");
        long i=0;
        try{
            long start=System.currentTimeMillis();
            long end;
            while (true){
                end=System.currentTimeMillis();
                if(end-start>=1000){
                    break;
                }
                i++;
                jedis.set("test"+i,i+"");
            }
        }finally {
            jedis.close();
        }
        System.out.println("一秒内redis操作了"+i+"次");
    }

}
