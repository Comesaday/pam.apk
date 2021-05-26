package cn.comesaday.avt.example.proxy;

/**
 * <描述> StsticJavaDeveloperProxy
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-05-26 14:03
 */
public class StaticJavaDeveloperProxy extends JavaDeveloper {

    @Override
    public void debug() {
        System.out.println("start...");
        super.debug();
        System.out.println("end...");
    }

    @Override
    public void bug() {
        System.out.println("start...");
        super.bug();
        System.out.println("end...");
    }
}
