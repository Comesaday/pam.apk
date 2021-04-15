package cn.comesaday.avt.dict.service;

import cn.comesaday.avt.dict.model.Dict;
import cn.comesaday.coe.core.basic.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <描述> DictService
 * <详细背景>
 *
 * @author: ChenWei
 * @CreateAt: 2021-03-09 13:34
 */
@Transactional
@Service
public class DictService extends BaseService<Dict, Long> {

}
