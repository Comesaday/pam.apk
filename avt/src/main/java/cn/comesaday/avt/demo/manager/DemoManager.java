package cn.comesaday.avt.demo.manager;

import cn.comesaday.avt.demo.model.Demo;
import cn.comesaday.coe.core.jpa.bean.repository.MyRepository;
import org.springframework.stereotype.Repository;

/**
 * <Descripe>
 *
 * @author: ChenWei
 * @CreateAt: 2020-08-10 15:20
 */
@Repository
public interface DemoManager extends MyRepository<Demo, Long> {

}
