package cn.comesaday.avt.business.dict.manager;

import cn.comesaday.avt.business.dict.model.Dict;
import cn.comesaday.coe.core.jpa.bean.repository.MyRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * <描述> ProcessElementManager
 * <详细背景>
 *
 * @author: ChenWei
 * @CreateAt: 2021-03-09 10:33
 */
public interface DictManager extends MyRepository<Dict, Long> {

    @Query("select c from Dict c left join Dict p on c.parentId = p.id where p.code = :pmCode")
    List<Dict> listProcess(@Param(value = "pmCode") String pmCode);
}
