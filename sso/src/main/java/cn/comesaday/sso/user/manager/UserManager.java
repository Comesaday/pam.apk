package cn.comesaday.sso.user.manager;

import cn.comesaday.coe.core.jpa.bean.repository.MyRepository;
import cn.comesaday.sso.user.model.UserInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * <Description>
 *
 * @author ChenWei
 * @CreateAt 2020-08-29 19:37
 */
@Repository
public interface UserManager extends MyRepository<UserInfo, Long> {

    @Query("select u from UserInfo u where u.username = :username and u.isDeleted = 0 and u.isDisabled = 0")
    UserInfo loadUserByUsername(@Param("username") String username);

}
