package cn.comesaday.avt.example.affair.manager;

import cn.comesaday.avt.example.affair.model.Affair;
import cn.comesaday.coe.core.jpa.bean.repository.MyRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;

/**
 * <描述>
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-05-11 14:07
 */
@Repository
public interface AffairManager extends MyRepository<Affair, Long> {

    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query(value = "select a from Affair a where a.Id = 1")
    Affair findForTest();
}
