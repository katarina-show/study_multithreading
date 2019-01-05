package com.sjw.delayqueue;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 使用delayQueue的对象必须实现delay接口
 */
public class CacheBean<T> implements Delayed {

    //缓存ID
    private String id;
    //缓存名字
    private String name;
    //缓存数据实体
    private T data;
    //到期时间
    private long activeTime;

    //注意构造时传入的参数是---缓存的超时时间（ms），也可以选择在本类用2个成员变量来做
    public CacheBean(String id, String name, T data, long activeTime) {
        this.id = id;
        this.name = name;
        this.data = data;
        //当前时间+传入的过期时间=到期时间  并转换成纳秒级别
        this.activeTime = TimeUnit.NANOSECONDS.convert(activeTime,TimeUnit.MILLISECONDS) + System.nanoTime();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public long getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(long activeTime) {
        this.activeTime = activeTime;
    }

    //返回到过期时间的剩余时间，时间单位由单位参数指定
    //该方法会在取数据时（take、poll）调用，并且底层是通过判断该方法的返回是否小于0，来知道当前数据可否被取出
    //（小于0 = 可取出，大于0 = 不可取出）
    @Override
    public long getDelay(TimeUnit unit) {
        //当然就是用  到期时间 - 当前时间
        return unit.convert(this.activeTime - System.nanoTime(),TimeUnit.NANOSECONDS);
    }

    //队列里元素的排序依据
    //当队列里已经有至少1个元素且还未被取出时，下一个入队的元素（offer、add、put）就会执行此方法
    //方法里的参数o，是之前队列中的实例（通过 “上滤” 操作找到父辈元素）在做取出操作时，也会采用 “下滤”操作
    //该方法无论怎么返回，实际上只区分>=0  和 <0 的情况
    //如果小于0，会把当前对象 和 被比较对象o交换位置，也就说消费者会先取到 "当前对象"
    //值大的排在数组后面 ，取值时从数组头开始取

    //若要看懂具体的offer、poll方法源码流程，需要了解  堆排序算法 比如 父节点 = ( i - 1 )/2 ,子节点 = i * 2 + 1以及 i*2 + 2。
    @Override
    public int compareTo(Delayed o) {
        System.out.println("方法被调用");
        long d = getDelay(TimeUnit.NANOSECONDS) - o.getDelay(TimeUnit.NANOSECONDS);

        return (d==0)?0:(d<0)?-1:1;
    }
}
