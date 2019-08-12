package com.gujun.knowledge;

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

}
