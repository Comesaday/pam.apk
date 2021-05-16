package cn.comesaday.avt.example.lock.distriuted.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * <Description>
 *
 * @author ChenWei
 * @CreateAt 2021-05-15 16:48
 */
@Component
public class RedisLock  {

    private final static Logger logger = LoggerFactory.getLogger(RedisLock.class);

    @Autowired
    private RedisTemplate redisTemplate;


    public Boolean lock(String key, Long expireTime) {
        Boolean lock = false;
        try {
            lock = (Boolean) redisTemplate.execute(new RedisCallback() {
                @Override
                public Object doInRedis(RedisConnection connection) throws DataAccessException {
                    Long expireAt = System.currentTimeMillis() + expireTime;
                    // set if not exist
                    Boolean lock = connection.setNX(key.getBytes(), String.valueOf(expireAt).getBytes());
                    redisTemplate.expire(key, expireTime, TimeUnit.MILLISECONDS);
                    if (!lock) {
                        byte[] bytes = connection.get(key.getBytes());
                        if (Objects.nonNull(bytes) && bytes.length > 0) {
                            // 死锁解决方案-重新加锁，防止死锁
                            String string = new String(bytes);
                            if (Long.parseLong(string) < System.currentTimeMillis()) {
                                logger.info(Thread.currentThread().getName() + "死锁出现...");
                                expireAt = System.currentTimeMillis() + expireTime;
                                connection.getSet(key.getBytes(), String.valueOf(expireAt).getBytes());
                                redisTemplate.expire(key, expireTime, TimeUnit.MILLISECONDS);
                                lock = true;
                            }
                        }
                    }
                    connection.close();
                    return lock;
                }
            });
        } catch (Exception e) {
            logger.error("获取锁异常:{}", e.getMessage(), e);
        }
        return lock;
    }

    public Boolean unlock(String key) {
        Boolean unlock = false;
        try {
            unlock = (Boolean) redisTemplate.execute(new RedisCallback() {
                @Override
                public Object doInRedis(RedisConnection connection) throws DataAccessException {
                    Boolean unlock;
                    byte[] bytes = connection.get(key.getBytes());
                    if (Objects.nonNull(bytes) && bytes.length > 0) {
                        Long del = connection.del(key.getBytes());
                        unlock = del == 1 ? true : false;
                    } else {
                        unlock = true;
                    }
                    connection.close();
                    return unlock;
                }
            });
        } catch (Exception e) {
            logger.error("释放锁异常:{}", e.getMessage(), e);
        }
        return unlock;
    }
}
