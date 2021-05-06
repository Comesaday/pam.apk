package cn.comesaday.avt.business.user.service;

import cn.comesaday.avt.business.user.model.UserGroup;
import cn.comesaday.avt.business.user.model.UserGroupRelation;
import cn.comesaday.coe.core.basic.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <描述> UserGroupService
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-04-12 14:03
 */
@Service
@Transactional
public class UserGroupService extends BaseService<UserGroup, Long> {

    @Autowired
    private UserGroupRelationService userGroupRelationService;


    /**
     * <说明> 获取用户组用户
     * @param groupCode 组id
     * @author ChenWei
     * @date 2021/4/15 14:52
     * @return java.util.List<java.lang.Long>
     */
    public List<Long> getGroupUserIds(String groupCode) {
        List<UserGroupRelation> relations = userGroupRelationService.findAllByProperty("groupCode", groupCode);
        if (CollectionUtils.isEmpty(relations)) {
            return null;
        }
        return relations.stream().map(relation -> {return relation.getUserId();}).collect(Collectors.toList());
    }


    /**
     * <说明> 获取用户组用户
     * @param groupCodes 组ids
     * @author ChenWei
     * @date 2021/4/15 14:52
     * @return java.util.List<java.lang.Long>
     */
    public List<Long> getGroupsUserIds(List<String> groupCodes) {
        List<Long> userIds = new ArrayList<>();
        for (String groupCode : groupCodes) {
            List<Long> groupUserIds = getGroupUserIds(groupCode);
            userIds.addAll(groupUserIds);
        }
        return userIds;
    }
}
