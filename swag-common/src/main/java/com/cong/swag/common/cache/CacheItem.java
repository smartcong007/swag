package com.cong.swag.common.cache;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @Description TODO
 * @Author zheng cong
 * @Date 2019/8/24
 */
public class CacheItem implements Delayed {

    private Object data;

    private long expire;

    public CacheItem(Object data, long expire, long createTime) {
        this.data = data;
        this.expire = TimeUnit.MILLISECONDS.convert(expire, TimeUnit.SECONDS) + createTime;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return expire - System.currentTimeMillis();
    }

    @Override
    public int compareTo(Delayed o) {
        return Long.valueOf(this.expire).compareTo(Long.valueOf(((CacheItem) o).expire));
    }

    public Object getData() {
        return data;
    }
}
