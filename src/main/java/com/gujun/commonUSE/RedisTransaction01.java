package com.gujun.commonUSE;

import com.gujun.spring.config.SpringConfig01;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;

import java.util.List;

public class RedisTransaction01 {

    //Redis事务：
    //与大多数NOSql技术不同，Redis是存在事务的；尽管没有数据库的事务强大，但是还是有它的用处；
    //Redis的事务使用MULTI-EXEC的命令组合；
    //tip:Redis的事务根本不能算原子性，仅仅满足了事务的隔离性；

    //命令：
    //multi,开启事务,之后的命令进入队列，而不会马上执行；
    //watch key1 key2...,监听某些键，当监听的键在事务执行前被修改，则事务回滚；（使用乐观锁？）
    //unwatch key1 key2...,取消监听某些键；
    //exec,执行事务，如果被监听的键没有被修改，则执行，否则回滚；
    //discard,回滚事务；

    //命令multi和exec之间的Redis命令将采取进入队列的形式，直至出现exec才会一次性将队列里的命令去执行，在执行这些命令时其它客户端不能再插入其它命令；
    @Test
    public void test01(){
        ApplicationContext context=new AnnotationConfigApplicationContext(SpringConfig01.class);
        RedisTemplate<String,String> redisTemplate=context.getBean(RedisTemplate.class);
        List<Object> results=redisTemplate.execute(new SessionCallback<List<Object>>() {
            @Override
            public <k,v> List<Object> execute(RedisOperations<k,v> ops) throws DataAccessException {
//                ops.watch("k1");  //监听指定key;
                ops.multi();    //开启事务
                ops.boundValueOps((k) "k1").set((v) "gj");
                String v1= (String) ops.boundValueOps((k) "k1").get();
                System.out.println(v1); //null,事务执行过程中，命令只是进入队列，还没有执行，所以v1为null;
                return ops.exec();
            }
        });
        System.out.println("==================");
        System.out.println(results.size());
        for(Object obj:results){
            System.out.println(obj.toString()); //true gj
        }
    }

    //Redis的事务回滚：
    //Redis的回滚和数据库的不大一样；
    //如果是数据类型造成的错误，命令照样进入队列，到执行事务时才会显示错误，而错误之前之后的命令都会执行；
    //如果是命令格式错误，在命令入列时就会检测出，这样之前之后的命令都会被回滚；
    //对此，必须通过程序检测数据类型的准确，以保证事务的准确执行；

}
