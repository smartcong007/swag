package com.cong.swag.common.cache;

import java.util.Collections;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;

/**
 * @Description Redis单节点版分布式锁
 * @Author zheng cong
 * @Date 2019-06-24
 */
public class RedisLock {

    private RedisTemplate<String, String> redisTemplate;

    private static final Long UNLOCK_SUCCESS = 1L;

    private static final String SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

    private RedisScript<Long> redisScript = new DefaultRedisScript<>(SCRIPT, Long.class);

    public void setRedisTemplate(
        RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 加锁
     *
     * @param lockKey 锁的唯一标示
     * @param requestId 加锁线程的唯一标示
     * @param exepire 锁的超时自动释放时间
     */
    public boolean lock(String lockKey, String requestId, long exepire) {
        if (StringUtils.isEmpty(lockKey) || StringUtils.isEmpty(requestId)) {
            throw new IllegalArgumentException();
        }
        return redisTemplate.opsForValue().setIfAbsent(lockKey, requestId, exepire, TimeUnit.SECONDS);
    }

    /**
     * 解锁
     *
     * @param lockKey 锁的唯一标示
     * @param requestId 获得锁的线程的唯一标示
     */
    public boolean unlock(String lockKey, String requestId) {
        if (StringUtils.isEmpty(lockKey)) {
            throw new IllegalArgumentException();
        }
        Object result = redisTemplate.execute(redisScript, Collections.singletonList(lockKey), requestId);
        if (UNLOCK_SUCCESS.equals(result)) {
            return true;
        } else {
            return false;
        }
    }
}
