package com.cong.swag.service.util;

import com.cong.swag.common.cache.CacheItem;
import java.util.concurrent.DelayQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description TODO
 * @Author zheng cong
 * @Date 2019/8/25
 */
public class DekayedQueue {

    private static DelayQueue<CacheItem> queue = new DelayQueue<>();

    /** logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(DekayedQueue.class);

    static {
        new Thread(()->{
            while (true) {
                CacheItem item = null;
                try {
                    item = queue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                LOGGER.info("移除元素：{}", item.getData().toString());
            }
        }).start();
    }

    public static void put(Object data, long expire) {
        CacheItem item = new CacheItem(data, expire, System.currentTimeMillis());
        queue.add(item);
    }

    public static Object get() {
        if (queue.isEmpty()) {
            return null;
        }
        CacheItem item = queue.peek();
        if (item != null) {
            queue.remove(item);
            return item.getData();
        }else {
            return null;
        }
    }

    public static boolean isEmpty(){
        return queue.isEmpty();
    }

}
