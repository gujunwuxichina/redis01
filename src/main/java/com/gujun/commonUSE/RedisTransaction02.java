package com.gujun.commonUSE;

public class RedisTransaction02 {

    //使用watch命令监控事务：
    //在Redis中可以通过watch命令来决定事务是执行还是回滚；
    //tip:Redis禁止在multi和exec之间执行watch指令，必须在multi之前就做好watch指令；
    //当时有exec命令执行事务时，首先会去对比被watch命令监控的键值对，若无变化则执行队列中的事务命令提交事务；
    //若有变化，则不执行事务中的任何命令，而是回滚事务；
    //无论事务是否回滚，都取消之前watch命令；
    //tip:以上采用了CAS原理，会引发ABA问题；redis不会产生ABA问题，因为在事务过程中，其它客户端把监听的值修改的值和之前的旧值相同也会被认为值被修改过；
    //在Java中当exec()返回null时，说明事务执行失败；

}
