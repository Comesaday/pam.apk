package cn.comesaday.avt.example.lock.thread;

import cn.comesaday.avt.example.lock.controller.LockController;

/**
 * <描述>
 * <详细背景>
 *
 * @author: ChenWei
 * @CreateAt: 2021-05-12 14:44
 */
public class LockThread implements Runnable {

    @Override
    public void run() {
        Integer number = ++ LockController.number;
        System.out.println("number=" + number);
    }
}
