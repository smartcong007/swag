package com.cong.swag.test;

import org.apache.commons.lang3.StringUtils;

/**
 * @Description TODO
 * @Author zheng cong
 * @Date 2019-06-10
 */
public class TestWithoutApplication {


    public static void main(String[] args) {

        String s = "ababcd";
        int i = kmp(s, "abc");
        System.out.println(i);
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

}
