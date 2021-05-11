package cn.comesaday.avt.example.mode.celue.sevice.impl;

import cn.comesaday.avt.example.mode.celue.sevice.CelueService;
import cn.comesaday.coe.common.constant.NumConstant;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <描述> ExampleServiceImpl1
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-04-13 19:50
 */
@Service
@Transactional
public class WorkServiceImpl implements CelueService {

    @Override
    public boolean matching(Integer factor) {
        return NumConstant.I1 == factor;
    }

    @Override
    public String apply() {
        return "WorkServiceImpl";
    }
}
