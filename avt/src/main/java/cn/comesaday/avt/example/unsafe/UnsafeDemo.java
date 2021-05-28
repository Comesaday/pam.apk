package cn.comesaday.avt.example.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * <描述>
 * <详细背景>
 *
 * @author: ChenWei
 * @CreateAt: 2021-05-28 13:12
 */
public class UnsafeDemo {

    public static void main(String[] args) throws Exception {
        Field field = Unsafe.class.getDeclaredFields()[0];
        field.setAccessible(true);
        Unsafe unsafe = (Unsafe) field.get(null);
        Thread thread = Thread.currentThread();
        unsafe.park(true, 3000);
        unsafe.unpark(thread);
        System.out.println("线程阻塞");
    }
}
