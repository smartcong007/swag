package com.cong.swag.common.cache;

import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @Description TODO
 * @Author zheng cong
 * @Date 2019-06-21
 */
public class RedisRepository {

    private RedisTemplate<String, Object> redisTemplate;
    private String keyPrefix;

    public void setRedisTemplate(
        RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

    private String buildKey(String key){
        return StringUtils.isNotEmpty(key)?keyPrefix+":"+key:null;
    }

    public void set(String key, Object val, long exepire) {
        if (StringUtils.isEmpty(key) || val == null) {
            throw new IllegalArgumentException();
        }
        redisTemplate.opsForValue().set(buildKey(key), val, exepire, TimeUnit.SECONDS);
    }

    public Object get(String key) {
        if (StringUtils.isEmpty(key)) {
            throw new IllegalArgumentException();
        }
        return redisTemplate.opsForValue().get(buildKey(key));
    }

    public Boolean del(String key) {
        if (StringUtils.isEmpty(key)) {
            throw new IllegalArgumentException();
        }
        return redisTemplate.delete(key);
    }

    public void zsetPush(String key, Object val, double score) {
        if (StringUtils.isEmpty(key)) {
            throw new IllegalArgumentException();
        }
        redisTemplate.opsForZSet().add(key, val, score);
    }

    public Set getZset(String key, boolean reverse, long page, long pageSize) {
        if (StringUtils.isEmpty(key)) {
            throw new IllegalArgumentException();
        }
        if (reverse) {
            return redisTemplate.opsForZSet().reverseRange(key, (page-1)*pageSize, page*pageSize);
        }else {
            return redisTemplate.opsForZSet().range(key, (page - 1) * pageSize, page * pageSize);
        }
    }

}
