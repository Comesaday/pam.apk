package cn.comesaday;

import cn.comesaday.init.InitApplication;

// 启动类
public class SsoServerApplication extends InitApplication {

    public static void main(String[] args) {
        InitApplication.init(args, SsoServerApplication.class);
    }
}
