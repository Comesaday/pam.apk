package cn.comesaday.avt.matter.service.impl;

import cn.comesaday.avt.matter.manager.MatterManager;
import cn.comesaday.avt.matter.model.Matter;
import cn.comesaday.avt.matter.service.MatterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <描述> MatterServiceImpl
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-03-18 16:23
 */
@Service
public class MatterServiceImpl implements MatterService {

    @Autowired
    private MatterManager matterManager;

    @Override
    public List<Matter> findAll(Matter matter) {
        Example<Matter> example = Example.of(matter);
        return matterManager.findAll(example);
    }

    @Override
    public Matter findOne(Long matterId) {
        return matterManager.findById(matterId).orElse(new Matter());
    }

    @Override
    public void saveOrUpdate(Matter matter) {
        matterManager.save(matter);
    }

    @Override
    public Matter findByModelId(String modelId) {
        Matter matter = new Matter();
        matter.setModelId(modelId);
        Example<Matter> example = Example.of(matter);
        return matterManager.findOne(example).orElse(new Matter());
    }
}
