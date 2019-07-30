package com.cong.swag.test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description TODO
 * @Author zheng cong
 * @Date 2019-06-10
 */
public class TestWithoutApplication {

    /** logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(TestWithoutApplication.class);

    private static Object obj = new Object();

    public static void main(String[] args) throws IOException {

        new Thread(() -> {
            synchronized (obj) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("释放锁啦");
        }).start();
        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (obj) {
                System.out.println("获取到锁啦");
            }
        }).start();
        System.in.read();
    }

    static void getNext(String pattern, int next[]) {
        int j = 0;
        int k = -1;
        int len = pattern.length();
        next[0] = -1;

        while (j < len - 1) {
            if (k == -1 || pattern.charAt(k) == pattern.charAt(j)) {

                j++;
                k++;
                next[j] = k;
            } else {

                // 比较到第K个字符，说明p[0——k-1]字符串和p[j-k——j-1]字符串相等，而next[k]表示
                // p[0——k-1]的前缀和后缀的最长共有长度，所接下来可以直接比较p[next[k]]和p[j]
                k = next[k];
            }
        }

    }

    static int kmp(String source, String patrern) {
        if (StringUtils.isEmpty(source) || StringUtils.isEmpty(patrern)) {
            throw new IllegalArgumentException();
        }
        int slen = source.length();
        int plen = patrern.length();
        int[] next = new int[plen];
        getNext(patrern, next);
        int i=0;
        int j=0;
        while (i<slen && j<plen) {
            if (source.charAt(i) == patrern.charAt(j)) {
                i++;
                j++;
            }else {
                if (next[j]==-1) {
                    i++;
                    j=0;
                }else {
                    j=next[j];
                }
            }
            if (j==plen) {
                return i-j;
            }
        }
        return -1;
    }

    @Test
    public int[] sds(){
       Long i = 123l;
        BigDecimal a = BigDecimal.valueOf(i);
        BigDecimal b = BigDecimal.valueOf(10000);
        System.out.println(a.divide(b));
        Map<Integer, Integer> map = new HashMap<>();
        int []ii = new int[]{};
        return new int[]{};
    }

}
