package cn.comesaday.avt.matter.service;

import cn.comesaday.avt.matter.model.Matter;

import java.util.List;

/**
 * <描述> MatterService
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-03-18 16:23
 */
public interface MatterService {

    List<Matter> findAll(Matter matter);

    Matter findOne(Long matterId);

    void saveOrUpdate(Matter matter);

    Matter findByModelId(String modelId);
}
