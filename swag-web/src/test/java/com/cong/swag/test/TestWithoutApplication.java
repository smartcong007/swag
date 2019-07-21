package com.cong.swag.test;

import com.alibaba.fastjson.JSONObject;
import com.cong.swag.common.VO.UserVO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * @Description TODO
 * @Author zheng cong
 * @Date 2019-06-10
 */
public class TestWithoutApplication {

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
    public void test(){
        List<UserVO> userVOS = new ArrayList<>(2);
        UserVO userVO1 = new UserVO();
        userVO1.setId(1);

        UserVO userVO2 = new UserVO();
        userVO2.setId(2);

        userVOS.add(userVO1);
        userVOS.add(userVO2);

        UserVO[] users = userVOS.toArray(new UserVO[userVOS.size()]);
        for (UserVO u:users) {
            System.out.println(JSONObject.toJSONString(u));
        }
    }

}
