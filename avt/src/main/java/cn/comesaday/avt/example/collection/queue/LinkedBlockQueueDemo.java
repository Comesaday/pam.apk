package cn.comesaday.avt.example.collection.queue;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <描述> LinkedBlockQueueDemo
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-05-28 10:32
 */
public class LinkedBlockQueueDemo {

    public static void main(String[] args) {
        Queue<Integer> queue = new LinkedBlockingQueue<>();
        queue.add(11);
        queue.add(22);
        queue.add(33);
        queue.add(44);
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());

        AtomicInteger integer = new AtomicInteger();

    }
}
