package cn.comesaday.avt.example.proxy;

/**
 * <描述> JavaDeveloper
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-05-26 13:14
 */
public class JavaDeveloper implements Developer {

    @Override
    public void debug() {
        System.out.println("debug");
    }

    @Override
    public void bug() {
        System.out.println("bug");
    }
}
