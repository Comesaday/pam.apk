package cn.comesaday.sso.api.manager;

import cn.comesaday.coe.core.jpa.bean.repository.MyRepository;
import cn.comesaday.sso.api.model.Api;
import org.springframework.stereotype.Repository;

/**
 * <描述> api
 * <详细背景>
 *
 * @author: ChenWei
 * @CreateAt: 2020-12-31 15:49
 */
@Repository
public interface ApiManager extends MyRepository<Api, Long> {

}
