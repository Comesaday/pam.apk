package cn.comesaday.avt.example.counter.atomicinteger;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * <描述> AutomicCounter
 * <详细背景> AtomicInteger原子操作数字另类
 * @author: ChenWei
 * @CreateAt: 2021-05-28 13:08
 */
public class AtomicCounter {

    public static void main(String[] args) {
        AtomicInteger integer = new AtomicInteger();
        System.out.println(integer.getAndIncrement());
        System.out.println(integer.getAndDecrement());
    }
}
