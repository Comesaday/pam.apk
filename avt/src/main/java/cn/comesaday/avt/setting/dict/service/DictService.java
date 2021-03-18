package cn.comesaday.avt.setting.dict.service;

import cn.comesaday.avt.setting.dict.model.Dict;

import java.util.List;

/**
 * <描述> ProcessElementService
 * <详细背景>
 *
 * @author: ChenWei
 * @CreateAt: 2021-03-09 13:34
 */
public interface DictService {

    Dict findOne(Dict element);

    Dict findOne(Long id);

    List<Dict> findAll(Dict element);

    Dict saveAndUpdate(Dict dict);

    void delete(Long dictId);

    List<Dict> listProcess(String pmCode);
}
