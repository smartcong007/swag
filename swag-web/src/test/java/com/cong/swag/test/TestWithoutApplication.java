package com.cong.swag.test;

import com.cong.swag.common.VO.UserVO;
import com.cong.swag.service.util.DekayedQueue;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

    /**
     * logger
     */
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
        int i = 0;
        int j = 0;
        while (i < slen && j < plen) {
            if (source.charAt(i) == patrern.charAt(j)) {
                i++;
                j++;
            } else {
                if (next[j] == -1) {
                    i++;
                    j = 0;
                } else {
                    j = next[j];
                }
            }
            if (j == plen) {
                return i - j;
            }
        }
        return -1;
    }

    boolean binarySearch(List<Integer> list, int target) {
        if (list.isEmpty()) {
            return false;
        }
        int startOff = 0,endOff = list.size()-1;
        while (startOff<=endOff) {
            int midOff = startOff+endOff>>>1;
            int midVal =  list.get(midOff);
            if (midVal == target) {
                return true;
            }else if (midVal>target) {
                endOff = midOff-1;
            }else {
                startOff = midOff+1;
            }
        }
        return false;
    }

    List comBineList(List<Integer> list1, List<Integer> list2) {
        if (list1==null||list1.isEmpty()) {
            return list2;
        }
        if (list2==null||list2.isEmpty()) {
            return list1;
        }
        List<Integer> list = new ArrayList<>(list1.size()+list2.size());
        int i=0,j=0;
        while (i<list1.size()&&j<list2.size()) {
            int val1 = list1.get(i);
            int val2 = list2.get(j);
            if (val1<val2) {
                list.add(val1);
                i++;
            }else if (val1>val2) {
                list.add(val2);
                j++;
            }else {
                list.add(val1);
                i++;
                j++;
            }
        }
        while (i<list1.size()){
            list.add(list1.get(i++));
        }
        while (j<list2.size()) {
            list.add(list2.get(j++));
        }
        return list;
    }

    @Test
    public void test(){
        List<Integer> list1 = new ArrayList<>();
        list1.add(1);
        list1.add(4);
        list1.add(6);
        List<Integer> list2 = new ArrayList<>();
        list2.add(2);
        list2.add(5);
        List<Integer> list = comBineList(list1, list2);
        for (int i:list) {
            System.out.println(i);
        }
    }

    @Test
    public void testCa() throws InterruptedException {
        UserVO user = new UserVO();
        user.setId(1);
        user.setName("张三");
        DekayedQueue.put(user, 3);
        Thread.sleep(5000);
        UserVO vo = (UserVO) DekayedQueue.get();
        if (vo == null) {
            return;
        }
        System.out.println(vo.toString());
        System.out.println(DekayedQueue.isEmpty());
    }

    @Test
    public void tests(){
        TreeNode root = new TreeNode(10);
        TreeNode rootL = new TreeNode(5);
        TreeNode rootR = new TreeNode(15);
        TreeNode rootRL = new TreeNode(6);
        TreeNode rootRR = new TreeNode(20);
        rootR.left = rootRL;
        rootR.right = rootRR;
        root.left = rootL;
        root.right = rootR;
        System.out.println(isValidBST(root));
    }

    public boolean isValidBST(TreeNode root) {
        if (root == null) {
            return true;
        }
        boolean leftValid = true;
        boolean rightValid = true;

        if (root.left != null) {
            if (root.left.val<root.val) {
                leftValid = isValidBST(root.left);
            }else {
                return false;
            }
        }

        if(root.right != null) {
            if(root.right.val>root.val) {
                rightValid = isValidBST(root.right);
            }else {
                return false;
            }
        }
        return leftValid && rightValid;
    }

    public class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode(int x) { val = x; }
   }

}
