package cn.comesaday.example.thread.helper;

import cn.comesaday.coe.common.util.JsonUtil;
import cn.comesaday.coe.enhance.redis.RedisEnhance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <描述> 任务工具
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-04-07 14:36
 */
@Service
public class JobHelper {

    @Autowired
    public RedisEnhance redisEnhance;

    /**
     * <说明> 获取分布式锁
     * @param key String
     * @author ChenWei
     * @date 2021/4/7 14:11
     * @return java.lang.Boolean
     */
    public Boolean getJobLock(String key) {
        Object lock = redisEnhance.get(key);
        if (null != lock) {
            return true;
        }
        return false;
    }

    /**
     * <说明> 设置分布式锁
     * @param key String
     * @param value Object
     * @author ChenWei
     * @date 2021/4/7 14:40
     * @return java.lang.Boolean
     */
    public Boolean setJobLock(String key, Object value) {
        return redisEnhance.set(key, JsonUtil.toJson(value));
    }

    /**
     * <说明> 设置分布式锁
     * @param key String
     * @param value Object
     * @param time long
     * @author ChenWei
     * @date 2021/4/7 14:40
     * @return java.lang.Boolean
     */
    public Boolean setJobLock(String key, Object value, long time) {
        return redisEnhance.set(key, JsonUtil.toJson(value), time);
    }

    /**
     * <说明> 移除分布式锁
     * @param key String
     * @author ChenWei
     * @date 2021/4/7 14:42
     * @return void
     */
    public void removeJobLock(String key) {
        redisEnhance.del(key);
    }
}
